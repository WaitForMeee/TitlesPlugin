package me.wait_for_meee.titlesPlugin.util;

import me.wait_for_meee.titlesPlugin.TitlesPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

public final class ScoreboardUtil {

    private ScoreboardUtil() {
    }

    public static void updateTag(@NotNull Player player) {

        Component title = TitlesPlugin.getTitleManager().getSelectedTitle(player.getUniqueId());

        if (title == null) {
            return;
        }

        player.displayName(Component.text(player.getName() + " ").append(title));
        player.playerListName(Component.text(player.getName() + " ").append(title));

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        String teamName = "name_" + player.getUniqueId().toString().substring(0, 12); // must be <= 16 chars
        Team team = scoreboard.getTeam(teamName);
        if (team == null) team = scoreboard.registerNewTeam(teamName);
        else team.getEntries().forEach(team::removeEntry);

        team.suffix(Component.text(" ").append(title));

        team.addEntry(player.getName());

        player.setScoreboard(scoreboard);
    }
}
