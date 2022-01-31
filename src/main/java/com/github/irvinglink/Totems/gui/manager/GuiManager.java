package com.github.irvinglink.Totems.gui.manager;

import com.github.irvinglink.Totems.MClass;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class GuiManager implements Listener {

    private final MClass plugin = MClass.getPlugin(MClass.class);

    public void onOpen(InventoryOpenEvent event) {

        if (event.getPlayer() instanceof Player) {

            Player player = (Player) event.getPlayer();

            IMenu menu = plugin.getPlayerMenus().get(player.getUniqueId());

            if (menu != null) menu.onOpen(event);

        }
    }

    public void onClick(InventoryClickEvent event) {

        if (event.getWhoClicked() instanceof Player) {

            Player player = ((Player) event.getWhoClicked()).getPlayer();
            assert player != null;

            IMenu menu = plugin.getPlayerMenus().get(player.getUniqueId());


            if (menu != null) menu.preClick(event);

        }

    }

    public void onClose(InventoryCloseEvent event) {

        if (event.getPlayer() instanceof Player) {

            Player player = (Player) event.getPlayer();

            IMenu menu = plugin.getPlayerMenus().get(player.getUniqueId());

            if (menu != null) menu.onClose(event);

        }
    }

    public void onDrag(InventoryDragEvent event) {

        if (event.isCancelled()) return;

        if (event.getWhoClicked() instanceof Player) {

            Player player = (Player) event.getWhoClicked();

            IMenu menu = plugin.getPlayerMenus().get(player.getUniqueId());

            if (menu != null) menu.onDrag(event);

        }
    }

}
