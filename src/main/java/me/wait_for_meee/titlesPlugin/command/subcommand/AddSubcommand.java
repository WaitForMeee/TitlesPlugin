package me.wait_for_meee.titlesPlugin.command.subcommand;

import me.wait_for_meee.titlesPlugin.TitlesPlugin;
import me.wait_for_meee.titlesPlugin.util.Common;
import me.wait_for_meee.titlesPlugin.util.ComponentParser;
import me.wait_for_meee.titlesPlugin.util.TitleTooLongException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AddSubcommand implements Subcommand {

    /*
     * indicates whether the add subcommand is intended to be executed from admin panel
     * if executed from admin panel, the target must not be null
     */
    private final boolean isAdmin;

    public AddSubcommand(boolean admin) {
        this.isAdmin = admin;
    }

    @Override
    public void execute(@NotNull Player executor, String[] args, @Nullable OfflinePlayer target) {


        if (!executor.hasPermission("yoshititles.add") && !isAdmin &&
                TitlesPlugin.getTokenManager().getTokens(executor.getUniqueId()) <= 0)
        {

            executor.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("You can't add the title. You have 0 tokens").color(NamedTextColor.WHITE)));

            return;
        }

        if (args.length == 0) {

            executor.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("You need to provide the title you want. It can contain hex colors! For example, "))
                    .append(Component.text("#FFC0F1").color(TextColor.fromHexString("#FFC011"))));

            return;
        }

        String rawTitle = String.join(" ",args);

        Component component;

        try {
            component = ComponentParser.parseFromString(rawTitle);
        } catch (final TitleTooLongException e) {

            executor.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("The title you provided is too long").color(NamedTextColor.RED)));
            return;
        }

        //in case something weird happens
        if (isAdmin && target == null) {

            executor.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("The player you provided does not exist")));
            return;
        }

        if (target == null) {

            TitlesPlugin.getTitleManager().addTitle(executor.getUniqueId(), component);

            executor.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("You have successfully added ").append(component))
                    .append(Component.text(" to the your list")));

        } else {

            TitlesPlugin.getTitleManager().addTitle(target.getUniqueId(), component);

            executor.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("You have successfully added ").append(component))
                    .append(Component.text(" to the player's title list")));
        }

        if (!isAdmin) {
            TitlesPlugin.getTokenManager().subtractTokens(executor.getUniqueId(),1);
        }
    }

    @Override
    public String getName() {
        return "add";
    }
}
