package me.wait_for_meee.titlesPlugin.listener;

import me.wait_for_meee.titlesPlugin.TitlesPlugin;
import me.wait_for_meee.titlesPlugin.util.ScoreboardUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        /*
        Player player = event.getPlayer();

        String name = player.getName();

        Component title = TitlesPlugin.getTitleManager().getSelectedTitle(player.getUniqueId());

        if (title == null) {
            return;
        }

        player.displayName(Component.text(name + " ").color(NamedTextColor.WHITE).append(title));
         */

        ScoreboardUtil.updateTag(event.getPlayer());
    }
}
