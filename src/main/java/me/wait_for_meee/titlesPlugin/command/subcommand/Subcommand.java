package me.wait_for_meee.titlesPlugin.command.subcommand;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Subcommand {

    void execute(@NotNull Player executor, String[] args, @Nullable OfflinePlayer target);

    String getName();
}
