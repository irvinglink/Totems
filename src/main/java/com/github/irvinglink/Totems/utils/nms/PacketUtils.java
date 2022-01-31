package com.github.irvinglink.Totems.utils.nms;

import com.github.irvinglink.Totems.utils.reflection.ReflectionUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public final class PacketUtils extends ReflectionUtils {


    public static void sendTitle(Player player, String title, String subtitle, int fadeInTime, int showTime, int fadeOutTime) {

        try {

            ///////////
            // TITLE
            Object enumTitleActionClazz = Objects.requireNonNull(Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0]).getField("TITLE").get(null);
            Object chatSerializer = Objects.requireNonNull(getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]).getMethod("a", String.class).invoke(null, "{\"text\":\"" + title + "\"}");

            Constructor<?> packetTitleConstructor = Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
            Object packetTitle = packetTitleConstructor.newInstance(enumTitleActionClazz, chatSerializer, fadeInTime, showTime, fadeOutTime);

            /////////////
            // SUBTITLE
            Object enumSubTitleActionClazz = Objects.requireNonNull(Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getDeclaredClasses()[0]).getField("SUBTITLE").get(null);
            Object chatSubSerializer = Objects.requireNonNull(getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]).getMethod("a", String.class).invoke(null, "{\"text\":\"" + subtitle + "\"}");

            Constructor<?> packetSubTitleConstructor = Objects.requireNonNull(getNMSClass("PacketPlayOutTitle")).getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
            Object packetSubTitle = packetSubTitleConstructor.newInstance(enumSubTitleActionClazz, chatSubSerializer, fadeInTime, showTime, fadeOutTime);

            sendPacket(player, packetTitle);
            sendPacket(player, packetSubTitle);

        } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

    }

    public static void sendActionBar(Player player, String message) {

        try {
            
            Object chatSerializer = Objects.requireNonNull(getNMSClass("IChatBaseComponent")).getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + chat.replace(message, true) + "\"}");

            Constructor<?> packetConstructor = Objects.requireNonNull(getNMSClass("PacketPlayOutChat")).getConstructor(getNMSClass("IChatBaseComponent"), byte.class);
            Object packetPlayOutChat = packetConstructor.newInstance(chatSerializer, (byte) 2);

            sendPacket(player, packetPlayOutChat);

        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }


    public static void sendActionBar(final Player player, final String message, long duration) {
        sendActionBar(player, message);

        if (duration >= 0) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendActionBar(player, "");
                }
            }.runTaskLater(plugin, 20* (duration + 1));
        }

        while (duration > 40) {
            duration -= 40;
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendActionBar(player, message);
                }
            }.runTaskLater(plugin, 20 * duration);

        }

    }

}
