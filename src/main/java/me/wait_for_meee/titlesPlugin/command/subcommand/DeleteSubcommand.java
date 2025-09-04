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

public class DeleteSubcommand implements Subcommand {

    @Override
    public void execute(@NotNull Player executor, String[] args, @Nullable OfflinePlayer target) {

        if (args.length == 0) {

            executor.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("You need to provide the "))
                    .append(Component.text("index ").color(NamedTextColor.YELLOW))
                    .append(Component.text("the title to delete")));

            return;
        }

        int index;

        try {
            index = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {

            executor.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("You provided an "))
                    .append(Component.text("invalid ").color(NamedTextColor.RED))
                    .append(Component.text("index").color(NamedTextColor.WHITE)));

            return;
        }

        List<?> titles;

        if (target != null) {
            titles = TitlesPlugin.getTitleManager().getTitles(target.getUniqueId());
        } else {
            titles = TitlesPlugin.getTitleManager().getTitles(executor.getUniqueId());
        }

        if (titles == null) {

            if (target != null) {

                executor.sendMessage(Common.yoshiTitlesMessagePrefix
                        .append(Component.text("The player has no titles (nothing to delete)")));

            } else {

                executor.sendMessage(Common.yoshiTitlesMessagePrefix
                        .append(Component.text("You have no titles no titles (nothing to delete)")));
            }

            return;
        }

        if (titles.size() <= index) {

            executor.sendMessage(Common.yoshiTitlesMessagePrefix
                    .append(Component.text("The index is out of range")));

            return;
        }

        if (target != null) {
            TitlesPlugin.getTitleManager().deleteTitle(target.getUniqueId(), index);
        } else {
            TitlesPlugin.getTitleManager().deleteTitle(executor.getUniqueId(), index);
        }

        executor.sendMessage(Common.yoshiTitlesMessagePrefix
                .append(Component.text("You have successfully "))
                .append(Component.text("deleted ").color(NamedTextColor.RED))
                .append(Component.text("the title with an index of ").color(NamedTextColor.WHITE))
                .append(Component.text(index).color(NamedTextColor.YELLOW)));
    }

    @Override
    public String getName() {
        return "delete";
    }
}
