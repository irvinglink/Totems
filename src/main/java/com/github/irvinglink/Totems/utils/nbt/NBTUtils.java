package com.github.irvinglink.Totems.utils.nbt;

import com.github.irvinglink.Totems.utils.nbt.enums.DEFAULT_NBT;
import com.github.irvinglink.Totems.utils.reflection.ReflectionUtils;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;

public class NBTUtils extends ReflectionUtils {

    // 1.17 uses a different NBT package net.minecraft.nbt
    public static void setKey(ItemStack itemStack, String key, String value) {
        if (itemStack == null || key == null) return;

        try {

            Object nmsItem = Objects.requireNonNull(getBukkitClass("inventory.CraftItemStack")).getMethod("asNMSCopy", ItemStack.class).invoke(null, itemStack);
            boolean hasTag = (boolean) nmsItem.getClass().getMethod("hasTag").invoke(nmsItem);
            Object nbtTagCompound = (hasTag) ? nmsItem.getClass().getMethod("getTag").invoke(nmsItem) : Objects.requireNonNull(getNMSClass("NBTTagCompound")).newInstance();


            Class<?> nbtTagString = Objects.requireNonNull(getNBTClass("NBTTagString"));
            nbtTagCompound.getClass().getMethod("set", String.class, getNBTClass("NBTBase")).invoke(nbtTagCompound, key, (versionInt >= 17) ? nbtTagString.getMethod("a", String.class).invoke(null, value) : nbtTagString.getConstructor(String.class).newInstance(value));
            nmsItem.getClass().getMethod("setTag", getNBTClass("NBTTagCompound")).invoke(nmsItem, nbtTagCompound);

            /*nbtTagCompound.getClass().getMethod("set", String.class, getNMSClass("NBTBase")).invoke(nbtTagCompound, key, getNMSClass("NBTTagString").getConstructor(String.class).newInstance(value));
            nmsItem.getClass().getMethod("setTag", getNMSClass("NBTTagCompound")).invoke(nmsItem, nbtTagCompound);*/

            getBukkitClass("inventory.CraftItemStack").getMethod("asBukkitCopy", getWorldItemClass("ItemStack")).invoke(null, nmsItem);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
        }

    }


    public static void setKey(ItemStack itemStack, String key, int value) {
        if (itemStack == null || key == null) return;

        try {

            Object nmsItem = Objects.requireNonNull(getBukkitClass("inventory.CraftItemStack")).getMethod("asNMSCopy", ItemStack.class).invoke(null, itemStack);
            boolean hasTag = (boolean) nmsItem.getClass().getMethod("hasTag").invoke(nmsItem);
            Object nbtTagCompound = (hasTag) ? nmsItem.getClass().getMethod("getTag").invoke(nmsItem) : Objects.requireNonNull(getNMSClass("NBTTagCompound")).newInstance();

            Class<?> nbtTagINT = Objects.requireNonNull(getNBTClass("NBTTagInt"));
            nbtTagCompound.getClass().getMethod("set", String.class, getNBTClass("NBTBase")).invoke(nbtTagCompound, key, (versionInt >= 17) ? nbtTagINT.getMethod("a", int.class).invoke(null, value) : nbtTagINT.getConstructor(int.class).newInstance(value));
            nmsItem.getClass().getMethod("setTag", getNBTClass("NBTTagCompound")).invoke(nmsItem, nbtTagCompound);

            Objects.requireNonNull(getBukkitClass("inventory.CraftItemStack")).getMethod("asBukkitCopy", getWorldItemClass("ItemStack")).invoke(null, nmsItem);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
        }

    }

    public static boolean hasKey(ItemStack itemStack, String key) {
        if (itemStack == null || key == null) return false;

        try {

            Object nmsItem = getBukkitClass("inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(null, itemStack);

            if (nmsItem.getClass().getMethod("getTag").invoke(nmsItem) == null) return false;
            Object getTag = nmsItem.getClass().getMethod("getTag").invoke(nmsItem);

            return (boolean) getTag.getClass().getMethod("hasKey", String.class).invoke(getTag, key);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static int getInt(ItemStack itemStack, String key) {
        if (itemStack == null || key == null) return 0;

        try {

            Object nmsItem = getBukkitClass("inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(null, itemStack);
            boolean hasTag = (boolean) nmsItem.getClass().getMethod("hasTag").invoke(nmsItem);

            Object nbtTagCompound = (hasTag) ? nmsItem.getClass().getMethod("getTag").invoke(nmsItem) : null;

            if (nbtTagCompound == null) return 0;

            return (int) nbtTagCompound.getClass().getMethod("getInt", String.class).invoke(nbtTagCompound, key);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static String getString(ItemStack itemStack, String key) {
        if (itemStack == null || key == null) return null;

        try {

            Object nmsItem = getBukkitClass("inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(null, itemStack);
            boolean hasTag = (boolean) nmsItem.getClass().getMethod("hasTag").invoke(nmsItem);

            Object nbtTagCompound = (hasTag) ? nmsItem.getClass().getMethod("getTag").invoke(nmsItem) : null;

            if (nbtTagCompound == null) return null;

            // TODO: 2/13/2022
            System.out.println(nbtTagCompound.getClass().getMethod("getString", String.class).invoke(nbtTagCompound, key));
            return (String) nbtTagCompound.getClass().getMethod("getString", String.class).invoke(nbtTagCompound, key);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean isPluginNBT(ItemStack itemStack) {
        if (itemStack == null) return false;
        return Arrays.stream(DEFAULT_NBT.values()).distinct().anyMatch(x -> hasKey(itemStack, x.getNbtkey()));
    }

}
