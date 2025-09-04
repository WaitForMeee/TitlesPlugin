package me.wait_for_meee.titlesPlugin.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ComponentParser {

    private static final Pattern pattern = Pattern.compile("(#[\\dA-Fa-f]{6})\\s*([^#]+)");

    private ComponentParser() {
    }

    public static @NotNull Component parseFromString(@NotNull String string) throws TitleTooLongException {

        Matcher matcher = pattern.matcher(string);

        Component result = Component.empty();

        boolean hasHex = false;
        int totalLength = 0;

        while (matcher.find()) {

            hasHex = true;

            String hexString = matcher.group(1);
            String text = matcher.group(2);

            totalLength += text.length();

            result = result.append(Component.text(text).color(TextColor.fromHexString(hexString)));
        }

        if (!hasHex) {
            totalLength += string.length();
            result = Component.text(string).color(NamedTextColor.WHITE);
        }

        if (totalLength > 40) {
            throw new TitleTooLongException();
        }

        return result;
    }
}
