package me.wait_for_meee.titlesPlugin;

import me.wait_for_meee.titlesPlugin.command.TitleCommand;
import me.wait_for_meee.titlesPlugin.command.TokenCommand;
import me.wait_for_meee.titlesPlugin.core.TitleManager;
import me.wait_for_meee.titlesPlugin.core.TokenManager;
import me.wait_for_meee.titlesPlugin.listener.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;


public final class TitlesPlugin extends JavaPlugin {

    private static TitleManager titleManager;
    private static TokenManager tokenManager;

    @Override
    public void onEnable() {

        titleManager = new TitleManager(this);
        titleManager.load();

        tokenManager = new TokenManager(this);
        tokenManager.load();

        getCommand("yoshititles").setExecutor(new TitleCommand());
        getCommand("tokens").setExecutor(new TokenCommand());

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(),this);
    }


    @Override
    public void onDisable() {
    }

    public static @NotNull TitleManager getTitleManager() {
        return titleManager;
    }

    public static @NotNull TokenManager getTokenManager() {
        return tokenManager;
    }
}
