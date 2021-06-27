package com.github.irvinglink.AmethystLib.utils.items;

import com.github.irvinglink.AmethystLib.MClass;
import com.github.irvinglink.AmethystLib.utils.chat.Chat;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.UUID;

public final class CustomHead extends ItemCreator {

    private static final MClass plugin = MClass.getPlugin(MClass.class);
    private static final Chat chat = plugin.getChat();

    private static Field field;

    public static Builder builder(ItemStack itemStack) {
        return new Builder(itemStack);
    }

    public static Builder builder(Material material, int amount) {
        return new Builder(material, amount);
    }


    public static ItemStack getPlayerHead(String playerName) {

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        assert skullMeta != null;

        skullMeta.setOwner(playerName);

        skullMeta.setDisplayName(chat.str("&e" + playerName));

        skull.setItemMeta(skullMeta);
        return skull;

    }

    public static ItemStack getCustomHead(String name, String url) {
        return getCustomHead(name, 1, url);
    }

    public static ItemStack getCustomHead(String name, int amount, String url) {

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, amount);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        assert skullMeta != null;

        if (url.length() < 16) {

            skullMeta.setOwner(url);

            skullMeta.setDisplayName(chat.str(name));

            skull.setItemMeta(skullMeta);
            return skull;

        }

        StringBuilder s_url = new StringBuilder();
        s_url.append("https://textures.minecraft.net/texture/").append(url);

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        byte[] data = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", s_url.toString()).getBytes());

        gameProfile.getProperties().put("textures", new Property("textures", new String(data)));

        try {

            if (field == null) field = skullMeta.getClass().getDeclaredField("profile");

            field.setAccessible(true);
            field.set(skullMeta, gameProfile);

        } catch (Exception e) {
            e.printStackTrace();
        }


        skullMeta.setDisplayName(chat.replace(name, true));
        skull.setItemMeta(skullMeta);

        return skull;

    }

    // DEFAULT HEADS
    public enum Default {


        EXAMPLE_ITEM("example_item", "&6Example Item", "c74df1c52d48b81f46ad07dfd54f7cba13412abf6df5a8ebccbdf746a26a75ab");

        private final String id;
        private final String displayName;
        private final String url;

        Default(String id, String displayName, String url) {
            this.id = id;
            this.displayName = displayName;
            this.url = url;
        }

        public ItemStack getItemStack() {
            return getCustomHead(getDisplayName(), getUrl());
        }

        public String getUrl() {
            return url;
        }

        public String getId() {
            return id;
        }

        public String getDisplayName() {
            return displayName;
        }

    }

    public static class Builder extends ItemCreator.Builder<CustomHead, Builder> {

        public Builder(Material material, int amount) {
            super(material, amount);
        }

        public Builder(ItemStack itemStack) {
            super(itemStack);
        }

    }

}