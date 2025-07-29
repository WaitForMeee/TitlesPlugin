package me.wait_for_meee.titlesPlugin.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import org.jetbrains.annotations.NotNull;

public final class ComponentSerializer {

    private ComponentSerializer() {
    }

    @NotNull
    public static Component deserialize(@NotNull String string) {
        return JSONComponentSerializer.json().deserialize(string);
    }

    @NotNull
    public static String serialize(@NotNull Component component) {
        return JSONComponentSerializer.json().serialize(component)  ;
    }
}
