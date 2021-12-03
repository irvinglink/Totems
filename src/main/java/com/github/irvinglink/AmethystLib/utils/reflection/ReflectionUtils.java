package com.github.irvinglink.AmethystLib.utils.reflection;

import com.github.irvinglink.AmethystLib.MClass;
import com.github.irvinglink.AmethystLib.utils.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReflectionUtils {

    protected static Chat chat = MClass.getPlugin(MClass.class).getChat();

    protected static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);


        } catch (Exception ex) {
        }
    }

    protected static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server."
                    + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
        } catch (ClassNotFoundException ignored) {
            ignored.printStackTrace();
        }
        return null;
    }

}
