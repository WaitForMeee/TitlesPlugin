package me.wait_for_meee.titlesPlugin.command.subcommand;

import me.wait_for_meee.titlesPlugin.TitlesPlugin;
import me.wait_for_meee.titlesPlugin.util.Common;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ListSubcommand implements Subcommand {


    @Override
    public void execute(@NotNull Player executor, String[] args, @Nullable OfflinePlayer target) {

        UUID targetUUID = (target == null) ? executor.getUniqueId() : target.getUniqueId();

        List<Component> titles = TitlesPlugin.getTitleManager().getTitles(targetUUID);

        if (titles == null || titles.isEmpty()) {

            if (target == null) {

                executor.sendMessage(Common.yoshiTitlesMessagePrefix
                        .append(Component.text("You don't have any titles yet")));

            } else {

                executor.sendMessage(Common.yoshiTitlesMessagePrefix
                        .append(Component.text("The player doesn't have any titles yet")));

            }

            return;
        }

        Component beginning = (target != null) ?
                Component.text(Bukkit.getOfflinePlayer(targetUUID).getName() + "'s ").color(NamedTextColor.GREEN) :
                Component.text("Your ").color(NamedTextColor.GREEN);

        Component listMessage = beginning.append(Component.text("titles:\n").color(NamedTextColor.WHITE));

        for (int i = 0; i < titles.size(); ++i) {
            Component title = titles.get(i);

            Component line = Component.text(i + " ").color(NamedTextColor.RED)
                    .append(Component.text("- ").color(NamedTextColor.WHITE))
                    .append(title)
                    .clickEvent(ClickEvent.runCommand("/yoshititles set " + i))
                    .hoverEvent(HoverEvent.showText(
                            Component.text("Click to set!").color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD)
                    ))
                    .append(Component.text("\n"));

            listMessage = listMessage.append(line);
        }

        executor.sendMessage(listMessage);
    }

    @Override
    public String getName() {
        return "list";
    }
}
