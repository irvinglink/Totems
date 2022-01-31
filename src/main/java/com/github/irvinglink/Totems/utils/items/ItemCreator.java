package com.github.irvinglink.Totems.utils.items;

import com.github.irvinglink.Totems.MClass;
import com.github.irvinglink.Totems.utils.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class ItemCreator {

    protected static class Builder<T extends ItemCreator, U extends Builder<T, U>> {

        private final MClass plugin = MClass.getPlugin(MClass.class);
        private final Chat chat = plugin.getChat();

        private final Material material;
        private final int amount;
        private final ItemStack itemStack;
        private final ItemMeta itemMeta;

        public Builder(ItemStack itemStack) {
            this.itemStack = itemStack;
            this.itemMeta = itemStack.getItemMeta();

            this.material = itemStack.getType();
            this.amount = itemStack.getAmount();
        }

        public Builder(Material material) {
            this.material = material;
            this.amount = 1;

            this.itemStack = new ItemStack(material, amount);
            this.itemMeta = itemStack.getItemMeta();
        }

        public Builder(Material material, int amount) {
            this.material = material;
            this.amount = amount;

            this.itemStack = new ItemStack(material, amount);
            this.itemMeta = itemStack.getItemMeta();
        }

        public U setLore(List<String> lore) {

            if (lore != null && !(lore.isEmpty()))
                itemMeta.setLore(lore.stream().map(x -> chat.replace(x, true)).collect(Collectors.toList()));

            update();
            return (U) this;

        }

        public U setLore(String... lore) {
            List<String> loreList = Arrays.stream(lore).collect(Collectors.toList());
            return setLore(loreList);
        }

        public U setDisplayName(String displayName) {

            this.itemMeta.setDisplayName(chat.replace(displayName, true));

            update();
            return (U) this;

        }

        public U changeColorName(ChatColor color) {
            return setDisplayName(("&" + color.getChar()) + ChatColor.stripColor(Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName()));
        }

        private void update() {
            this.itemStack.setItemMeta(itemMeta);
        }

        public ItemStack build() {
            return this.itemStack;
        }

    }

}
