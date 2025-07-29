package me.wait_for_meee.titlesPlugin.util;

import me.wait_for_meee.titlesPlugin.TitlesPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public final class Common {

    private Common() {
    }

    public static final Component yoshiTitlesMessagePrefix;

    public static final NamespacedKey pdckey = new NamespacedKey(TitlesPlugin.getPlugin(TitlesPlugin.class),"titlesplugin");

    public static final ItemStack confirmButton = new ItemStack(Material.LIME_DYE);

    static {

        yoshiTitlesMessagePrefix = Component.text("『").color(NamedTextColor.WHITE)
                .append(Component.text("YoshiTitles").color(NamedTextColor.GREEN))
                .append(Component.text("』 ").color(NamedTextColor.WHITE));

        ItemMeta meta1 = confirmButton.getItemMeta();
        meta1.displayName(Component.text("Yes!").color(NamedTextColor.GREEN));
        meta1.getPersistentDataContainer().set(pdckey, PersistentDataType.BOOLEAN, true);

    }
}
