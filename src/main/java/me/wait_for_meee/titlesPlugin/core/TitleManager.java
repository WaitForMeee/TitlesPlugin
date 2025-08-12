package me.wait_for_meee.titlesPlugin.core;

import me.wait_for_meee.titlesPlugin.util.ComponentSerializer;
import me.wait_for_meee.titlesPlugin.util.ScoreboardUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class TitleManager {

    private File file;
    private YamlConfiguration config;
    private final @NotNull JavaPlugin plugin;

    public TitleManager(@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private final Map<UUID, List<Component>> titlesMap = new HashMap<>();
    private final Map<UUID,Component> selectedTitlesMap = new HashMap<>();


    public void load() {

        file = new File(plugin.getDataFolder(),"titles.yml");

        if (!file.exists()) {
            plugin.saveResource("titles.yml",false);
        }

        config = YamlConfiguration.loadConfiguration(file);
        config.options().parseComments(true);

        //titles_map (all titles)
        ConfigurationSection section = config.getConfigurationSection("titles_map");

        if (section == null) {
            config.createSection("titles_map");
            section = config.getConfigurationSection("titles_map");
        }

        for (Map.Entry<String,Object> entry : section.getValues(false).entrySet()) {

            UUID uuid = UUID.fromString(entry.getKey());

            List<String> stringList = section.getStringList(entry.getKey());

            List<Component> mapValueList = titlesMap.get(uuid);

            if (mapValueList == null) {
                mapValueList = new ArrayList<>();
            }

            for (String string : stringList) {
                mapValueList.add(ComponentSerializer.deserialize(string));
            }

            titlesMap.put(uuid,mapValueList);
        }

        //selected_titles_map (selected titles)
        ConfigurationSection section1 = config.getConfigurationSection("selected_titles_map");

        if (section1 == null) {
            config.createSection("selected_titles_map");
            section1 = config.getConfigurationSection("selected_titles_map");
        }

        for (Map.Entry<String, Object> entry : section1.getValues(false).entrySet()) {

            UUID uuid = UUID.fromString(entry.getKey());

            String serialized = section1.getString(entry.getKey());

            Component selectedTitle = ComponentSerializer.deserialize(serialized);

            selectedTitlesMap.put(uuid,selectedTitle);
        }
    }

    public void save() {

        try {
            config.save(file);
        } catch (final IOException exception) {
            exception.printStackTrace();
        }

    }

    public void addTitle(@NotNull UUID uuid,@NotNull Component title) {

        List<Component> list = titlesMap.get(uuid);

        if (list == null) {
            list = new ArrayList<>();
        }

        list.add(title);

        titlesMap.put(uuid,list);

        ConfigurationSection section = config.getConfigurationSection("titles_map");

        List<String> stringList = section.getStringList(uuid.toString());

        stringList.add(ComponentSerializer.serialize(title));

        section.set(uuid.toString(),stringList);

        save();
    }

    public @Nullable List<Component> getTitles(@NotNull UUID uuid) {
        return titlesMap.get(uuid);
    }

    public @Nullable Component getSelectedTitle(@NotNull UUID uuid) {
        return selectedTitlesMap.get(uuid);
    }

    public void setSelectedTitle(@NotNull UUID uuid, int index) {

        List<Component> list = titlesMap.get(uuid);

        if (list == null) {
            return;
        }

        if (index >= list.size()) {
            return;
        }

        selectedTitlesMap.put(uuid,list.get(index));

        ConfigurationSection section = config.getConfigurationSection("selected_titles_map");

        if (section == null) {
            config.createSection("selected_titles_map");
        }

        section.set(uuid.toString(), ComponentSerializer.serialize(list.get(index)));
        save();
    }

    public void setSelectedTitle(@NotNull UUID uuid, @NotNull Component component) {

        selectedTitlesMap.put(uuid,component);

        ConfigurationSection section = config.getConfigurationSection("selected_titles_map");

        if (section == null) {
            config.createSection("selected_titles_map");
        }

        section.set(uuid.toString(), ComponentSerializer.serialize(component));
        save();
    }

    public void unsetSelectedTitle(@NotNull UUID uuid) {

        selectedTitlesMap.put(uuid,Component.empty());

        ConfigurationSection section = config.getConfigurationSection("selected_titles_map");

        if (section == null) {
            config.createSection("selected_titles_map");
        }

        section.set(uuid.toString(),null);
        save();
    }

    public void deleteTitle(@NotNull UUID uuid,@NotNull Component component) {

        if (!titlesMap.containsKey(uuid)) {
            return;
        }

        titlesMap.get(uuid).remove(component);

        ConfigurationSection section = config.getConfigurationSection("titles_map");

        List<String> stringList = section.getStringList(uuid.toString());

        stringList.remove(ComponentSerializer.serialize(component));

        section.set(uuid.toString(),stringList);

        save();
    }

    public void deleteTitle(@NotNull UUID uuid, int index) {

        if (!titlesMap.containsKey(uuid)) {
            return;
        }

        List<Component> list = titlesMap.get(uuid);

        if (index >= list.size()) {
            return;
        }

        list.remove(index);

        ConfigurationSection section = config.getConfigurationSection("titles_map");

        List<String> stringList = section.getStringList(uuid.toString());

        stringList.remove(index);

        section.set(uuid.toString(),stringList);

        save();
    }

    public void clearTitles(@NotNull UUID uuid) {

        if (!titlesMap.containsKey(uuid)) {
            return;
        }

        titlesMap.get(uuid).clear();

        ConfigurationSection section = config.getConfigurationSection("titles_map");

        section.set(uuid.toString(),null);

        save();
    }

    public void updateTitle(@NotNull OfflinePlayer player) {

        String selectedTitle = config.getConfigurationSection("selected_titles_map")
                .getString(player.getUniqueId().toString());

        Component title;

        if (selectedTitle == null) {

            selectedTitlesMap.put(player.getUniqueId(),Component.empty());
        } else {

            title = ComponentSerializer.deserialize(selectedTitle);
            selectedTitlesMap.put(player.getUniqueId(),title);
        }

        if (!player.isOnline()) {
            return;
        }

        ScoreboardUtil.updateTag((Player) player);
    }
}
