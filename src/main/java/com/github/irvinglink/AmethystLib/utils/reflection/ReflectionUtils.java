package com.github.irvinglink.AmethystLib.utils.reflection;

import com.github.irvinglink.AmethystLib.MClass;
import com.github.irvinglink.AmethystLib.utils.chat.Chat;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReflectionUtils {

    protected static MClass plugin = MClass.getPlugin(MClass.class);
    protected static Chat chat = plugin.getChat();

    protected static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);


        } catch (Exception ex) {
        }
    }

    public static Class<?> getNMSClass(String name) {

        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        int versionInt = Integer.parseInt((Bukkit.getBukkitVersion().split("-")[0]).split("\\.")[1]);

        String minecraftPacketVersion = (versionInt >= 17) ? "net.minecraft.network.protocol.game." :"net.minecraft.server." + version + ".";

        try {
            return Class.forName(minecraftPacketVersion + name);
        } catch (ClassNotFoundException ignored) {
            ignored.printStackTrace();
        }

        return null;
    }

}
