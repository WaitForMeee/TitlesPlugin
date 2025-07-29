package me.wait_for_meee.titlesPlugin.command;

import me.wait_for_meee.titlesPlugin.TitlesPlugin;
import me.wait_for_meee.titlesPlugin.util.Common;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TokenCommand implements CommandExecutor, TabExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        CommandSender player = commandSender;

        /* /tokens get | set | add | subtract */

        if (strings.length == 0) {

            player.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("The subcommands are "))
                    .append(Component.text("get | set | add | subtract").color(NamedTextColor.GREEN)));

            return true;
        }

        if (strings[0].equals("get")) {

            if (strings.length == 1) {

                player.sendMessage(Common.yoshiTitlesMessagePrefix
                        .append(Component.text("Provide the player name for whom to perform the lookup").color(NamedTextColor.WHITE)));

                return true;
            }

            OfflinePlayer target = Bukkit.getOfflinePlayer(strings[1]);

            int tokens = TitlesPlugin.getTokenManager().getTokens(target.getUniqueId());

            player.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("The player ").color(NamedTextColor.WHITE))
                    .append(Component.text(strings[1] + " ").color(NamedTextColor.GREEN))
                    .append(Component.text("has ").color(NamedTextColor.WHITE))
                    .append(Component.text(tokens + " ").color(NamedTextColor.YELLOW))
                    .append(Component.text("tokens")));
            return true;
        }

        if (strings[0].equals("add")) {

            if (strings.length == 1) {

                player.sendMessage(Common.yoshiTitlesMessagePrefix
                        .append(Component.text("Please provide the player name to add the tokens to").color(NamedTextColor.WHITE)));

                return true;
            }

            if (strings.length == 2) {

                player.sendMessage(Common.yoshiTitlesMessagePrefix
                        .append(Component.text("Please provide the amount").color(NamedTextColor.WHITE)));
                return true;
            }

            int amount = Integer.parseInt(strings[2]);

            OfflinePlayer target = Bukkit.getOfflinePlayer(strings[1]);

            TitlesPlugin.getTokenManager().addTokens(target.getUniqueId(),amount);

            player.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text(amount + " ").color(NamedTextColor.YELLOW))
                    .append(Component.text("tokens were ").color(NamedTextColor.WHITE))
                    .append(Component.text("added ").color(NamedTextColor.GREEN))
                    .append(Component.text("to ").color(NamedTextColor.WHITE))
                    .append(Component.text(strings[1] + " ").color(NamedTextColor.GREEN))
                    .append(Component.text("'s balance ").color(NamedTextColor.WHITE)));
        }

        if (strings[0].equals("subtract")) {

            if (strings.length == 1) {

                player.sendMessage(Common.yoshiTitlesMessagePrefix
                        .append(Component.text("Please provide the player name to subtract the tokens from").color(NamedTextColor.WHITE)));

                return true;
            }

            if (strings.length == 2) {

                player.sendMessage(Common.yoshiTitlesMessagePrefix
                        .append(Component.text("Please provide the amount").color(NamedTextColor.WHITE)));
                return true;
            }

            int amount = Integer.parseInt(strings[2]);

            OfflinePlayer target = Bukkit.getOfflinePlayer(strings[1]);

            TitlesPlugin.getTokenManager().subtractTokens(target.getUniqueId(),amount);

            player.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text(amount + " ").color(NamedTextColor.YELLOW))
                    .append(Component.text("tokens were ").color(NamedTextColor.WHITE))
                    .append(Component.text("subtracted ").color(NamedTextColor.RED))
                    .append(Component.text("from ").color(NamedTextColor.WHITE))
                    .append(Component.text(strings[1] + " ").color(NamedTextColor.GREEN))
                    .append(Component.text("'s balance").color(NamedTextColor.WHITE)));
        }

        if (strings[0].equals("set")) {

            if (strings.length == 1) {

                player.sendMessage(Common.yoshiTitlesMessagePrefix
                        .append(Component.text("Please provide the player for whom to set the tokens").color(NamedTextColor.WHITE)));

                return true;
            }

            if (strings.length == 2) {

                player.sendMessage(Common.yoshiTitlesMessagePrefix
                        .append(Component.text("Please provide the value").color(NamedTextColor.WHITE)));
                return true;
            }

            int amount = Integer.parseInt(strings[2]);

            OfflinePlayer target = Bukkit.getOfflinePlayer(strings[1]);

            TitlesPlugin.getTokenManager().setTokens(target.getUniqueId(),amount);

            player.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text(strings[1]).color(NamedTextColor.GREEN))
                    .append(Component.text("'s balance was ").color(NamedTextColor.WHITE))
                    .append(Component.text("set ").color(NamedTextColor.GREEN))
                    .append(Component.text("to ").color(NamedTextColor.WHITE))
                    .append(Component.text(amount + " ").color(NamedTextColor.YELLOW))
                    .append(Component.text("tokens").color(NamedTextColor.WHITE)));
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (strings.length == 1) {

            return Stream.of("add","set","subtract","get")
                    .filter(string -> string.startsWith(strings[0]))
                    .collect(Collectors.toList());
        }

        if (strings.length == 2 && (strings[0].equals("add") ||
                strings[0].equals("set") ||
                strings[0].equals("subtract") ||
                strings[0].equals("get")))
        {
            return Bukkit.getOnlinePlayers()
                    .stream()
                    .map(Player::getName)
                    .filter(string -> string.startsWith(strings[1]))
                    .collect(Collectors.toList());
        }

        if (strings.length == 3 && (strings[0].equals("add") ||
                strings[0].equals("set") ||
                strings[0].equals("subtract")))
        {
            return List.of("<value>");
        }

        return List.of();
    }
}
