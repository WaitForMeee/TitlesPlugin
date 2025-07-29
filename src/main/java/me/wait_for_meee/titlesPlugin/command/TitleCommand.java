package me.wait_for_meee.titlesPlugin.command;

import me.wait_for_meee.titlesPlugin.TitlesPlugin;
import me.wait_for_meee.titlesPlugin.command.subcommand.AddSubcommand;
import me.wait_for_meee.titlesPlugin.command.subcommand.DeleteSubcommand;
import me.wait_for_meee.titlesPlugin.command.subcommand.ListSubcommand;
import me.wait_for_meee.titlesPlugin.command.subcommand.SetSubcommand;
import me.wait_for_meee.titlesPlugin.util.Common;
import me.wait_for_meee.titlesPlugin.util.ComponentParser;
import me.wait_for_meee.titlesPlugin.util.ComponentSerializer;
import me.wait_for_meee.titlesPlugin.util.TitleTooLongException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TitleCommand implements CommandExecutor, TabExecutor {

    //yoshititles add|set|disable|list|admin
    //yoshititles admin <player> add|set|disable|list
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player)) {

            commandSender.sendMessage("You cannot use this command unless you're a player.");
            return true;
        }

        Player player = (Player) commandSender;

        if (strings.length == 0) {

            player.sendMessage(Common.yoshiTitlesMessagePrefix);

            player.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("Hi! This is a quick guide to YoshiTitles plugin!\n\n").color(NamedTextColor.WHITE))
                    .append(Component.text("This plugin lets you create custom titles (displayed after your name)\n\n").color(NamedTextColor.WHITE))
                    .append(Component.text("To create a custom title, you need a token\n\n").color(NamedTextColor.WHITE))
                    .append(Component.text("The tokens can be obtained in various ways, more about this later\n\n").color(NamedTextColor.WHITE))
                    .append(Component.text(" The subcommands are ").color(NamedTextColor.WHITE))
                    .append(Component.text("add | set | disable | list | preview\n\n").color(NamedTextColor.GREEN))
                    .append(Component.text("preview -> ").color(NamedTextColor.GREEN))
                    .append(Component.text("you can preview what the title will look like using this subcommand\n\n").color(NamedTextColor.WHITE))
                    .append(Component.text("add -> ")).color(NamedTextColor.GREEN)
                    .append(Component.text("this subcommand lets you add a custom title " +
                            "(before adding a new title, use preview feature. you can't undo it)\n\n").color(NamedTextColor.WHITE))
                    .append(Component.text("list -> ").color(NamedTextColor.GREEN))
                    .append(Component.text("lists all of your available titles with their indices (needed for selection)\n\n").color(NamedTextColor.WHITE))
                    .append(Component.text("set -> ").color(NamedTextColor.GREEN))
                    .append(Component.text("lets you choose a title by index (use list feature to see the titles and their indices)\n\n").color(NamedTextColor.WHITE))
                    .append(Component.text("disable -> ").color(NamedTextColor.GREEN))
                    .append(Component.text("disables your title\n").color(NamedTextColor.WHITE))
                    .append(Component.text("tokens -> ").color(NamedTextColor.GREEN))
                    .append(Component.text("see how many tokens you have\n").color(NamedTextColor.WHITE)));

            return true;
        }

        String[] args = Arrays.copyOfRange(strings,1,strings.length);

        //adding
        if (strings[0].equals("add")) {

            AddSubcommand addSubcommand = new AddSubcommand(false);

            addSubcommand.execute(player,args,null);
            return true;
        }

        //listing
        if (strings[0].equals("list")) {

            ListSubcommand listSubcommand = new ListSubcommand();

            listSubcommand.execute(player,args,null);

            return true;
        }

        if (strings[0].equals("tokens")) {

            int tokens = TitlesPlugin.getTokenManager().getTokens(player.getUniqueId());

            player.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("You have ").color(NamedTextColor.WHITE))
                    .append(Component.text(tokens + " ").color(NamedTextColor.YELLOW))
                    .append(Component.text("tokens").color(NamedTextColor.WHITE)));

            return true;
        }

        if (strings[0].equals("preview")) {

            if (strings.length == 1) {

                player.sendMessage(Common.yoshiTitlesMessagePrefix
                        .append(Component.text("You need to provide the title")));

                return true;
            }

            String rawTitle = String.join(" ",args);

            Component title;

            try {
                title = ComponentParser.parseFromString(rawTitle);
            } catch (TitleTooLongException e) {

                player.sendMessage(Common.yoshiTitlesMessagePrefix
                        .append(Component.text("The title is too long. Your title has to be below 40 characters")));

                return true;
            }

            player.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("The title "))
                    .append(Component.text(rawTitle))
                    .append(Component.text(" evaluated to be "))
                    .append(title));

            return true;
        }

        //disabling
        if (strings[0].equals("disable")) {

            TitlesPlugin.getTitleManager().unsetSelectedTitle(player.getUniqueId());

            player.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("You have successfully disabled the title!")));

            TitlesPlugin.getTitleManager().updateTitle(player);

            return true;
        }

        //setting
        if (strings[0].equals("set")) {

            SetSubcommand setSubcommand = new SetSubcommand();

            setSubcommand.execute(player,args,null);

            return true;
        }

        //admin panel
        if (strings[0].equals("admin")) {

            if (!player.hasPermission("yoshititles.admin")) {
                return true;
            }

            if (strings.length < 3) {
                return true;
            }

            String[] adminArgs = Arrays.copyOfRange(strings,3,strings.length);

            //yoshititles admin Yoshi24s add COOL TITLE

            OfflinePlayer target = Bukkit.getOfflinePlayer(strings[1]);

            if (strings[2].equals("add")) {

                AddSubcommand addSubcommand = new AddSubcommand(true);

                addSubcommand.execute(player,adminArgs,target);
            }

            //listing
            if (strings[2].equals("list")) {

                ListSubcommand listSubcommand = new ListSubcommand();

                listSubcommand.execute(player,adminArgs,target);

                return true;
            }

            //disabling
            if (strings[2].equals("disable")) {

                TitlesPlugin.getTitleManager().unsetSelectedTitle(target.getUniqueId());

                player.sendMessage(Common.yoshiTitlesMessagePrefix
                        .append(Component.text("You have successfully disabled the title!")));

                TitlesPlugin.getTitleManager().updateTitle(target);

                return true;
            }

            //setting
            if (strings[2].equals("set")) {

                SetSubcommand setSubcommand = new SetSubcommand();

                setSubcommand.execute(player,adminArgs,target);

                return true;
            }

            //deletion
            if (strings[2].equals("delete")) {

                DeleteSubcommand deleteSubcommand = new DeleteSubcommand();

                deleteSubcommand.execute(player,adminArgs,target);

                return true;
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player)) {
            return List.of();
        }

        if (strings.length == 1) {

            Player player = (Player) commandSender;

            Stream<String> stream = player.hasPermission("yoshititles.admin") ?
                    Stream.of("add","set","list","disable","admin","preview","tokens") : Stream.of("add","set","list","disable","preview","tokens");

            return stream.filter(arg -> arg.startsWith(strings[0])).collect(Collectors.toList());
        }

        if (strings.length == 2 && strings[0].equals("admin")) {

            if (!commandSender.hasPermission("yoshititles.admin")) {
                return List.of();
            }

            return Bukkit.getOnlinePlayers()
                    .stream()
                    .map(Player::getName)
                    .filter(arg -> arg.startsWith(strings[1]))
                    .collect(Collectors.toList());
        }

        if (strings.length == 3 && strings[0].equals("admin")) {

            if (!commandSender.hasPermission("yoshititles.admin")) {
                return List.of();
            }

            return Stream.of("add","set","list","disable","delete")
                    .filter(arg -> arg.startsWith(strings[2]))
                    .collect(Collectors.toList());
        }

        return List.of();
    }
}
