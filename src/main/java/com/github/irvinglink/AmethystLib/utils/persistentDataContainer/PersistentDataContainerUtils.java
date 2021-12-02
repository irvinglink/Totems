package com.github.irvinglink.AmethystLib.utils.persistentDataContainer;

import com.github.irvinglink.AmethystLib.MClass;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 * Only will work for versions 1.14+
 */
public class PersistentDataContainerUtils {

    private final MClass plugin = MClass.getPlugin(MClass.class);

    public void setItemKey(ItemStack itemStack, String key, String value) {
        if (itemStack == null) return;

        ItemMeta itemMeta = itemStack.getItemMeta();

        assert itemMeta != null;

        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);

        dataContainer.set(namespacedKey, PersistentDataType.STRING, value);
    }


    public void setItemKey(ItemStack itemStack, String key, int value) {
        if (itemStack == null) return;

        ItemMeta itemMeta = itemStack.getItemMeta();

        assert itemMeta != null;

        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);

        dataContainer.set(namespacedKey, PersistentDataType.INTEGER, value);

    }

    public void setBlockKey(Block block, String key, String value) {
        if (block == null) return;

        TileState blockState = (TileState) block.getState();

        PersistentDataContainer dataContainer = blockState.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);

        dataContainer.set(namespacedKey, PersistentDataType.STRING, value);
        blockState.update();
    }


    public void setBlockKey(Block block, String key, int value) {
        if (block == null) return;

        TileState blockState = (TileState) block.getState();

        PersistentDataContainer dataContainer = blockState.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);

        dataContainer.set(namespacedKey, PersistentDataType.INTEGER, value);
        blockState.update();
    }

    public String getItemString(ItemStack itemStack, String key) {
        if (itemStack == null) return null;

        ItemMeta itemMeta = itemStack.getItemMeta();

        assert itemMeta != null;

        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);

        return dataContainer.getOrDefault(namespacedKey, PersistentDataType.STRING, "");
    }

    public int getItemInt(ItemStack itemStack, String key) {
        if (itemStack == null) return 0;

        ItemMeta itemMeta = itemStack.getItemMeta();

        assert itemMeta != null;

        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);

        return dataContainer.getOrDefault(namespacedKey, PersistentDataType.INTEGER, 0);
    }


    public String getBlockString(Block block, String key) {
        if (block == null) return null;

        TileState blockState = (TileState) block.getState();

        PersistentDataContainer dataContainer = blockState.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);

        return dataContainer.getOrDefault(namespacedKey, PersistentDataType.STRING, "");
    }

    public int getBlockInt(Block block, String key) {
        if (block == null) return 0;

        TileState blockState = (TileState) block.getState();

        PersistentDataContainer dataContainer = blockState.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);

        return dataContainer.getOrDefault(namespacedKey, PersistentDataType.INTEGER, 0);
    }


}
