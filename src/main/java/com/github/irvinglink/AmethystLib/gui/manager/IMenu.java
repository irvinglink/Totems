package com.github.irvinglink.AmethystLib.gui.manager;

import com.github.irvinglink.AmethystLib.MClass;
import com.github.irvinglink.AmethystLib.utils.chat.Chat;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Objects;

public interface IMenu extends InventoryHolder {

    MClass plugin = MClass.getPlugin(MClass.class);
    Chat chat = plugin.getChat();

    void onOpen(InventoryOpenEvent event);

    void onDrag(InventoryDragEvent event);

    void onClose(InventoryCloseEvent event);

    void onClick(InventoryClickEvent event);

    boolean enableSlots();

    default void preClick(InventoryClickEvent event) {


        if (!(enableSlots())) {

            if (event.isCancelled()) return;

            if ((event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) || (event.getAction().name().contains("PLACE") && !isPlayerInventory(Objects.requireNonNull(event.getClickedInventory())))
                    || event.getAction().equals(InventoryAction.HOTBAR_SWAP)))
                event.setCancelled(true);

            if (event.getCurrentItem() == null || Objects.requireNonNull(event.getCurrentItem()).getType().equals(Material.AIR))
                return;

            if (!(event.getWhoClicked() instanceof Player)) return;

            if (!isPlayerInventory(Objects.requireNonNull(event.getClickedInventory()))) event.setCancelled(true);


        }

        onClick(event);

    }

    default boolean isPlayerInventory(Inventory inventory) {
        return inventory.getType().equals(InventoryType.PLAYER);
    }

}