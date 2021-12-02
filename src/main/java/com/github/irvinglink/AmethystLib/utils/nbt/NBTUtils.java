package com.github.irvinglink.AmethystLib.utils.nbt;

import com.github.irvinglink.AmethystLib.utils.nbt.enums.DEFAULT_NBT;
import net.minecraft.server.v1_16_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class NBTUtils {

    public static ItemStack setKey(ItemStack itemStack, String key, String value) {
        if (itemStack == null || key == null) return null;

        net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound nbtTagCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();

        assert nbtTagCompound != null;

        nbtTagCompound.setString(key, value);
        nmsItem.setTag(nbtTagCompound);

        return CraftItemStack.asBukkitCopy(nmsItem);
    }


    public static ItemStack setKey(ItemStack itemStack, String key, int value) {
        if (itemStack == null || key == null) return null;

        net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound nbtTagCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();

        assert nbtTagCompound != null;
        nbtTagCompound.setInt(key, value);
        nmsItem.setTag(nbtTagCompound);

        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    public static boolean hasKey(ItemStack itemStack, String key) {
        if (itemStack == null || key == null) return false;

        net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
        if (nmsItem.getTag() == null) return false;
        return (nmsItem.getTag().hasKey(key));
    }

    public static int getInt(ItemStack itemStack, String key) {
        if (itemStack == null || key == null) return 0;

        net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound nbtTagCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : null;

        if (nbtTagCompound == null) return 0;

        return nbtTagCompound.getInt(key);
    }

    public static String getString(ItemStack itemStack, String key) {
        if (itemStack == null || key == null) return null;

        net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound nbtTagCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : null;

        if (nbtTagCompound == null) return null;

        return nbtTagCompound.getString(key);
    }

    public static boolean isPluginNBT(ItemStack itemStack) {
        if (itemStack == null) return false;
        return Arrays.stream(DEFAULT_NBT.values()).distinct().anyMatch(x -> hasKey(itemStack, x.getNbtId()));
    }

}
