package me.wait_for_meee.titlesPlugin.listener;

import me.wait_for_meee.titlesPlugin.util.ScoreboardUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        ScoreboardUtil.updateTag(event.getPlayer());
    }
}
