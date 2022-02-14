package com.github.irvinglink.Totems.editor.inventory;

import com.github.irvinglink.Totems.MClass;
import com.github.irvinglink.Totems.enums.config.MESSAGES;
import com.github.irvinglink.Totems.utils.chat.Chat;
import com.github.irvinglink.Totems.utils.nbt.NBTUtils;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class EditorManager {

    private final MClass plugin;
    private final Chat chat;

    private final Map<UUID, ItemStack[]> editorCache = new ConcurrentHashMap<>();

    public EditorManager(MClass plugin, Chat chat) {
        this.plugin = plugin;
        this.chat = chat;
    }

    public void toggleEditor(UUID uuid) {
        if (!(Bukkit.getPlayer(uuid) != null && Objects.requireNonNull(Bukkit.getPlayer(uuid)).isOnline())) return;

        Player player = Bukkit.getPlayer(uuid);
        assert player != null;

        if (!editorCache.containsKey(uuid)) { // EDITOR ENABLED
            saveInventory(uuid, player.getInventory().getContents());

            player.getInventory().clear();
            player.getInventory().setContents(EditorInventory.TOTEM_EDITOR.getItems());


            player.updateInventory();

            player.sendMessage(chat.replace(player, MESSAGES.COMMAND_TOTEMADMIN_EDITOR_ENABLE.getMessage(), true));

            Arrays.stream(player.getInventory().getContents()).filter(Objects::nonNull).forEach(x -> {
                plugin.getLogger().info(NBTUtils.getString(x, "item"));
            });

        } else { // EDITOR DISABLED
            player.getInventory().clear();
            loadInventory(uuid);
            player.sendMessage(chat.replace(player, MESSAGES.COMMAND_TOTEMADMIN_EDITOR_DISABLE.getMessage(), true));

            editorCache.remove(uuid);
        }

    }

    public boolean isEditing(UUID uuid) {
        if (!(Bukkit.getPlayer(uuid) != null && Objects.requireNonNull(Bukkit.getPlayer(uuid)).isOnline()))
            return false;

        return editorCache.containsKey(uuid);
    }

    private void saveInventory(UUID uuid, ItemStack[] inventory) {
        editorCache.put(uuid, inventory);
    }

    private void loadInventory(UUID uuid) {
        Objects.requireNonNull(Bukkit.getPlayer(uuid)).getInventory().setContents(editorCache.get(uuid));
    }

}
