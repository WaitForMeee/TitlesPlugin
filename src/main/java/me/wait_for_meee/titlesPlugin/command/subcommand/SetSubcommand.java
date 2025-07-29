package me.wait_for_meee.titlesPlugin.command.subcommand;

import me.wait_for_meee.titlesPlugin.TitlesPlugin;
import me.wait_for_meee.titlesPlugin.util.Common;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SetSubcommand implements Subcommand {


    @Override
    public void execute(@NotNull Player executor, String[] args, @Nullable OfflinePlayer target) {

        if (args.length == 0) {

            executor.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("You need to provide an index of the title")));

            return;
        }

        int index;

        try {
            index = Integer.parseInt(args[0]);

        } catch (NumberFormatException e) {

            executor.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("You provided an "))
                    .append(Component.text("invalid ").color(NamedTextColor.RED))
                    .append(Component.text(" number")));

            return;
        }

        List<?> list = TitlesPlugin.getTitleManager().getTitles(target == null ? executor.getUniqueId() : target.getUniqueId());

        if (list == null) {

            executor.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("You don't have any titles yet (nothing to choose from)")));

            return;
        }

        if (index >= list.size()) {

            executor.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("The index you provided is out of range")));

            return;
        }

        if (target == null) {

            TitlesPlugin.getTitleManager().setSelectedTitle(executor.getUniqueId(),index);

            executor.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("You have successfully "))
                    .append(Component.text("set ").color(NamedTextColor.GREEN))
                    .append(Component.text("your title to be "))
                    .append(TitlesPlugin.getTitleManager().getSelectedTitle(executor.getUniqueId())));

            TitlesPlugin.getTitleManager().updateTitle(executor);

        } else {

            TitlesPlugin.getTitleManager().setSelectedTitle(target.getUniqueId(),index);

            executor.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("You have successfully "))
                    .append(Component.text("set ").color(NamedTextColor.GREEN))
                    .append(Component.text("the player's title to be "))
                    .append(TitlesPlugin.getTitleManager().getSelectedTitle(target.getUniqueId())));

            TitlesPlugin.getTitleManager().updateTitle(target);
        }
    }

    @Override
    public String getName() {
        return "set";
    }
}
