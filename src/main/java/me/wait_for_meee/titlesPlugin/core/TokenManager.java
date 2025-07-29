package me.wait_for_meee.titlesPlugin.core;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class TokenManager {

    private final Map<UUID, Integer> tokensMap = new HashMap<>();

    private final JavaPlugin plugin;
    private File file;
    private YamlConfiguration config;

    public TokenManager(@NotNull JavaPlugin plugin) {

        this.plugin = plugin;
    }

    public void load() {

        file = new File(plugin.getDataFolder(),"tokens.yml");

        if (!file.exists()) {
            plugin.saveResource("tokens.yml",false);
        }

        config = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection section = config.getConfigurationSection("tokens_map");

        if (section == null) {

            config.createSection("tokens_map");
            save();
            section = config.getConfigurationSection("tokens_map");
        }

        for (Map.Entry<String,Object> entry : section.getValues(false).entrySet()) {

            UUID uuid = UUID.fromString(entry.getKey());

            int tokens = section.getInt(entry.getKey());

            tokensMap.put(uuid,tokens);
        }
    }

    public void save() {

        try {
            config.save(file);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void setTokens(@NotNull UUID uuid,int value) {

        if (value < 0) {
            value = 0;
        }

        tokensMap.put(uuid,value);

        ConfigurationSection section = config.getConfigurationSection("tokens_map");

        if (section == null) {
            config.createSection("tokens_map");
        }

        section.set(uuid.toString(),value);

        save();
    }

    public void addTokens(@NotNull UUID uuid, int value) {

        if (!tokensMap.containsKey(uuid)) {
            tokensMap.put(uuid,0);
        }

        int tokens = tokensMap.get(uuid);

        tokens += value;

        if (tokens < 0) {
            tokens = 0;
        }

        tokensMap.put(uuid,tokens);

        ConfigurationSection section = config.getConfigurationSection("tokens_map");

        if (section == null) {
            config.createSection("tokens_map");
        }

        section.set(uuid.toString(),value);

        save();
    }

    public void subtractTokens(@NotNull UUID uuid, int value) {

        if (value < 0) {
            value = 0;
        }

        int tokens = tokensMap.get(uuid);

        tokens -= value;

        if (tokens < 0) {
            tokens = 0;
        }

        tokensMap.put(uuid,tokens);

        ConfigurationSection section = config.getConfigurationSection("tokens_map");

        if (section == null) {
            config.createSection("tokens_map");
        }

        section.set(uuid.toString(),value);

        save();
    }

    public int getTokens(@NotNull UUID uuid) {

        if (!tokensMap.containsKey(uuid)) {
            return 0;
        }

        return tokensMap.get(uuid);
    }
}
