package me.wait_for_meee.titlesPlugin.command.test;

import me.wait_for_meee.titlesPlugin.util.ComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SerializationTestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player player)) {
            return true;
        }

        Component test1 = Component.text("hello").color(TextColor.fromHexString("#FFFC01"));
        Component test2 = Component.text("No hex title");
        Component test3 = Component.text("My").color(TextColor.fromHexString("#FFFC01"))
                .append(Component.text("title").color(TextColor.fromHexString("#11FC01")));

        player.sendMessage(test1);
        player.sendMessage(test2);
        player.sendMessage(test3);

        String serialized1 = ComponentSerializer.serialize(test1);
        String serialized2 = ComponentSerializer.serialize(test2);
        String serialized3 = ComponentSerializer.serialize(test3);

        player.sendMessage("Serialized:\n");

        player.sendMessage(serialized1);
        player.sendMessage(serialized2);
        player.sendMessage(serialized3);

        player.sendMessage("Deserialized:\n");

        player.sendMessage(ComponentSerializer.deserialize(serialized1));
        player.sendMessage(ComponentSerializer.deserialize(serialized2));
        player.sendMessage(ComponentSerializer.deserialize(serialized3));

        return true;
    }
}
