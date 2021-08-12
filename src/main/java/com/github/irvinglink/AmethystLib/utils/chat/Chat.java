package com.github.irvinglink.AmethystLib.utils.chat;


import com.github.irvinglink.AmethystLib.MClass;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Chat extends HexManager {

    private final MClass plugin = MClass.getPlugin(MClass.class);

    private final String serverVersion = plugin.getServerVersion();

    private final Pattern pattern = Pattern.compile("[%]([^%]+)[%]");

    private static final Map<String, IReplacementHook> hookMap = new HashMap<>();

    public String str(String textToTranslate) {

        int index = serverVersion.indexOf("_");
        int finalIndex = serverVersion.indexOf("_", index + 1);

        int minorVersion = Integer.parseInt(serverVersion.substring(index + 1, finalIndex));

        if (minorVersion >= 16)
            return hexMessage(textToTranslate);

        return ChatColor.translateAlternateColorCodes('&', textToTranslate);
    }

    public String removeColor(String str) {
        return ChatColor.stripColor(str);
    }

    public String firstCharUpper(String value) {
        if (value == null)
            return null;
        if (value.length() < 2)
            return value.toUpperCase();
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    // MODIFY
    public String getPluginPrefix() {
        return "&8[&6SlashCodePlugin&8] ";
    }

    public String getPrefix() {
        return this.plugin.getLang().getString("Chat.Prefix");
    }

    public TextComponent clickedMessage(String text, String value) {

        TextComponent textComponent = new TextComponent(str(text));
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, value));

        return textComponent;
    }

    public TextComponent clickedMessage_Hover(String text, String hover_msg, String value) {

        TextComponent textComponent = new TextComponent(str(text));
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, value));
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover_msg).create()));
        return textComponent;
    }

    public TextComponent message(String text) {
        return new TextComponent(str(text));
    }

    public TextComponent clickMessageCmd(String text, String cmd) {

        TextComponent textComponent = new TextComponent(text);
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd));

        return textComponent;
    }

    // EDIT HOOK NAME!
    public void registerHooks() {
        hookMap.put("yourhook", new ReplacementHook());
    }

    public void registerHook(String key, IReplacementHook hook) {
        hookMap.put(key, hook);
    }

    public void unRegisterHook(String key) {
        hookMap.remove(key);
    }

    public Map<String, IReplacementHook> getHooks() {
        return hookMap;
    }

    public String replace(String text, boolean color) {
        return replace(null, null, null, text, color);
    }

    public String replace(OfflinePlayer player, String text, boolean color) {
        return replace(player, null, null, text, color);
    }

    public String replace(OfflinePlayer player, String str, String text, boolean color) {
        return replace(player, null, str, text, color);
    }

    public String replace(OfflinePlayer player, OfflinePlayer target, String str, String text, boolean color) {

        if (text == null) return null;

        Matcher matcher = this.pattern.matcher(text);

        while (matcher.find()) {

            String var = matcher.group(1);
            int index = var.indexOf("_");

            if (index <= 0 || index >= var.length())
                continue;

            String beginning = var.substring(0, index);
            String next = var.substring(index + 1);

            if (hookMap.containsKey(beginning)) {
                String value = (hookMap.get(beginning)).replace(player, target, str, next);

                if (value != null)
                    text = text.replaceAll(Pattern.quote(matcher.group()), Matcher.quoteReplacement(value));

            }


        }

        /* REMOVE COMMENT IF PLACEHOLDER API IS GOING TO BE USED
            text = PlaceholderAPI.setPlaceholders(player, text);
         */

        return color ? str(text) : text;
    }

}