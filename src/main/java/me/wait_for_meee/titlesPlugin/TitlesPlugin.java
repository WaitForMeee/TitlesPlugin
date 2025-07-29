package me.wait_for_meee.titlesPlugin;

import me.wait_for_meee.titlesPlugin.command.TitleCommand;
import me.wait_for_meee.titlesPlugin.command.TokenCommand;
import me.wait_for_meee.titlesPlugin.command.test.SerializationTestCommand;
import me.wait_for_meee.titlesPlugin.core.TitleManager;
import me.wait_for_meee.titlesPlugin.core.TokenManager;
import me.wait_for_meee.titlesPlugin.listener.PlayerJoinListener;
//import net.luckperms.api.LuckPerms;
//import net.luckperms.api.LuckPermsProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;


public final class TitlesPlugin extends JavaPlugin {

    //private static LuckPerms api;
    private static TitleManager titleManager;
    private static TokenManager tokenManager;

    @Override
    public void onEnable() {

        //api = LuckPermsProvider.get();

        titleManager = new TitleManager(this);
        titleManager.load();

        tokenManager = new TokenManager(this);
        tokenManager.load();

        getCommand("yoshititles").setExecutor(new TitleCommand());
        /*temp*/
        getCommand("serializationtest").setExecutor(new SerializationTestCommand());
        /*temp*/
        getCommand("tokens").setExecutor(new TokenCommand());
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(),this);
    }


    @Override
    public void onDisable() {
        
    }

    /*public static @NotNull LuckPerms getLuckPermsApi() {
        return api;
    }*/

    public static @NotNull TitleManager getTitleManager() {
        return titleManager;
    }

    public static @NotNull TokenManager getTokenManager() {
        return tokenManager;
    }
}
