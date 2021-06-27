package com.github.irvinglink.AmethystLib.gui.manager;

import com.github.irvinglink.AmethystLib.MClass;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class Menu {

    private static final MClass plugin = MClass.getPlugin(MClass.class);

    public static void openMenu(Player player, IMenu menu) {

        UUID uuid = player.getUniqueId();

        player.closeInventory();

        if (!plugin.getPlayerMenus().containsKey(player.getUniqueId())) plugin.getPlayerMenus().put(uuid, menu);
        else plugin.getPlayerMenus().replace(uuid, menu);

        player.openInventory(menu.getInventory());

    }

    public static void closeMenu(Player player) {

        player.closeInventory();
        plugin.getPlayerMenus().remove(player.getUniqueId());

    }
}
