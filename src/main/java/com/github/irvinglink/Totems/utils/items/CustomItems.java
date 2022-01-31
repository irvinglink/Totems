package com.github.irvinglink.Totems.utils.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public final class CustomItems extends ItemCreator {

    public static Builder builder(ItemStack itemStack) {
        return new Builder(itemStack);
    }

    public static Builder builder(Material material, int amount) {
        return new Builder(material, amount);
    }

    public static Builder builder(Material material) {
        return new Builder(material);
    }


    // DEFAULT ITEMS
    public enum Default {


        MAIN_MENU("main_menu", Material.OAK_DOOR, "&aMain IMenu"),
        SUPER_SWORD("super_sword", Material.DIAMOND_SWORD, "&aSuper Sword"),
        ;

        private final String id;
        private final Material material;
        private final String displayName;

        Default(String id, Material material, String displayName) {
            this.id = id;
            this.material = material;
            this.displayName = displayName;
        }

        public String getId() {
            return id;
        }

        public Material getMaterial() {
            return material;
        }

        public String getDisplayName() {
            return displayName;
        }

        public List<String> getLore() {

            List<String> loreOutput = new ArrayList<>();

            switch (id) {
                case "clan_chest":
                    loreOutput.add("&bOpen the chest content of your clan&7.");
                    break;

                case "toggle_pvp":
                    loreOutput.add("");
                    break;

            }

            return loreOutput;
        }

        public ItemStack getItemStack() {
            return builder(getMaterial(), 1).setDisplayName(getDisplayName()).setLore(getLore()).build();
        }

    }

    public static class Builder extends ItemCreator.Builder<CustomItems, Builder> {

        public Builder(Material material, int amount) {
            super(material, amount);
        }

        public Builder(Material material) {
            super(material);
        }

        public Builder(ItemStack itemStack) {
            super(itemStack);
        }

    }

}
