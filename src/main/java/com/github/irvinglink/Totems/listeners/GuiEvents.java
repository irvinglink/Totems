package com.github.irvinglink.Totems.listeners;

import com.github.irvinglink.Totems.gui.manager.GuiManager;
import com.github.irvinglink.Totems.gui.manager.IMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class GuiEvents implements Listener {

    private final GuiManager guiManager = new GuiManager();

    @EventHandler
    public void onOpen(InventoryOpenEvent event) {

        if (event.isCancelled()) return;

        if (event.getInventory().getHolder() instanceof IMenu) {

            if (event.getPlayer().isSleeping()) return;

            guiManager.onOpen(event);

        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (event.isCancelled()) return;

        if (event.getInventory().getHolder() instanceof IMenu) guiManager.onClick(event);

    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {

        if (event.isCancelled()) return;

        if (event.getInventory().getHolder() instanceof IMenu) guiManager.onDrag(event);

    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {

        if (!(event.getPlayer() instanceof Player)) return;

        if (event.getInventory().getHolder() instanceof IMenu)
            guiManager.onClose(event);

    }

}