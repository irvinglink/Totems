package com.github.irvinglink.Totems.editor.inventory;

import com.github.irvinglink.Totems.utils.items.CustomItems;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public enum EditorInventory {

    TOTEM_EDITOR("totem_editor", new ItemStack[]{

            CustomItems.builder(CustomItems.Default.TOTEM_STICK).setNBT("item", "totem_stick").build(),

    }),

    ;

    @Getter private final String id;
    @Getter private final ItemStack[] items;

    EditorInventory(String id, ItemStack[] items) {
        this.id = id;
        this.items = items;
    }


}

