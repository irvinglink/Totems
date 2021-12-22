package com.github.irvinglink.AmethystLib.utils.chat;


import com.github.irvinglink.AmethystLib.MClass;
import com.github.irvinglink.AmethystLib.utils.chat.hex.HexManager;
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

/**
 * Chat class is used for the easy development of message such as
 * placeholders, colored strings, clicked messages, and more!
 *
 * <p>Common methods: </p>
 * <ul>
 *   <li><p>{@link #str(String)} Colorize a string.</p></li>
 *   <li><p>{@link #replace(String, boolean)} Replace placeholders from a string.</p></li>
 *   <li><p>{@link #getPrefix()} Returns the prefix from lang.yml</p></li>
 * </ul>
 */
public final class Chat {

    private final MClass plugin = MClass.getPlugin(MClass.class);

    private final String serverVersion = plugin.getServerVersion();

    private final Pattern pattern = Pattern.compile("[%]([^%]+)[%]");

    private static final Map<String, IReplacementHook> hookMap = new HashMap<>();

    /**
     * Gives color to a String, if you are using newer versions
     * from 1.16 the plugin will automatically accept the hex format.
     */
    public String str(String textToTranslate) {

        int index = serverVersion.indexOf("_");
        int finalIndex = serverVersion.indexOf("_", index + 1);

        int minorVersion = Integer.parseInt(serverVersion.substring(index + 1, finalIndex));

        if (minorVersion >= 16)
            textToTranslate = HexManager.getInstance().toChatColorString((textToTranslate));

        return ChatColor.translateAlternateColorCodes('&', textToTranslate);
    }

    /**
     * Remove the color from a String
     */
    public String removeColor(String str) {
        return ChatColor.stripColor(str);
    }

    /**
     * @return Replace the first char from a String to UpperCase.
     */
    public String firstCharUpper(String value) {
        if (value == null)
            return null;
        if (value.length() < 2)
            return value.toUpperCase();
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    /**
     * Remember to customize your placeholder at the begging of
     * the development.
     */
    public String getPluginPrefix() {
        return "&8[&6SlashCodePlugin&8] ";
    }

    /**
     * @return Prefix from the lang.yml
     */
    public String getPrefix() {
        return this.plugin.getLang().getString("Chat.Prefix");
    }

    /**
     * Create a clicked message. This can be used to suggest
     * a command to a player in chat.
     *
     * @param text  Your String.
     * @param value Suggest a command.
     */
    public TextComponent clickedMessage(String text, String value) {

        TextComponent textComponent = new TextComponent(str(text));
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, value));

        return textComponent;
    }

    /**
     * Create a clicked message. This can be used to suggest
     * a command to a player in chat with a hover message.
     *
     * @param text      Your String.
     * @param hover_msg Hover text.
     * @param value     Suggest a command.
     */
    public TextComponent clickedMessage_Hover(String text, String hover_msg, String value) {

        TextComponent textComponent = new TextComponent(str(text));
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, value));
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover_msg).create()));
        return textComponent;
    }

    /**
     * Create a {@link TextComponent} message, this automatically accepts colors from {@link #replace(String, boolean)}.
     */
    public TextComponent message(String text) {
        return new TextComponent(replace(text, true));
    }

    /**
     * Create a clicked message that runs a command. This can be used to run
     * a command to a player in chat.
     *
     * @param text Your String.
     * @param cmd  Command to run.
     */
    public TextComponent clickMessageCmd(String text, String cmd) {

        TextComponent textComponent = new TextComponent(text);
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd));

        return textComponent;
    }

    /**
     * Call this method when you already called Chat class in your
     * main class to register main placeholders. Without calling this method
     * your text that uses {@link #replace(String, boolean)} will not replace your
     * placeholders.
     */
    public void registerHooks() {
        hookMap.put("lorem", new ReplacementHook());
    }

    /**
     * This method is used to third-party plugins create hooks
     * for your plugin.
     */
    public void registerHook(String key, IReplacementHook hook) {
        hookMap.put(key, hook);
    }

    /**
     * Unregister any kind of placeholder of your plugin or third-party hook.
     */
    public void unRegisterHook(String key) {
        hookMap.remove(key);
    }

    /**
     * Return the registered placeholders.
     *
     * @return Map with name of the placeholder and hooker class.
     */
    public Map<String, IReplacementHook> getHooks() {
        return hookMap;
    }

    /**
     * Replace the text with placeholders from PlaceholderAPI or
     * registered by the plugin (for example: {@link ReplacementHook}).
     * <p>
     * This method inherits from {@link #replace(OfflinePlayer, OfflinePlayer, String, String, boolean)}
     */
    public String replace(String text, boolean color) {
        return replace(null, null, null, text, color);
    }

    /**
     * Replace the text with placeholders from PlaceholderAPI or
     * registered by the plugin (for example: {@link ReplacementHook}).
     * <p>
     * This method inherits from {@link #replace(OfflinePlayer, OfflinePlayer, String, String, boolean)}
     */
    public String replace(OfflinePlayer player, String text, boolean color) {
        return replace(player, null, null, text, color);
    }

    /**
     * Replace the text with placeholders from PlaceholderAPI or
     * registered by the plugin (for example: {@link ReplacementHook}).
     * <p>
     * This method inherits from {@link #replace(OfflinePlayer, OfflinePlayer, String, String, boolean)}
     */
    public String replace(OfflinePlayer player, String str, String text, boolean color) {
        return replace(player, null, str, text, color);
    }

    /**
     * Replace the text with placeholders from PlaceholderAPI or
     * registered by the plugin (for example: {@link ReplacementHook}).
     * <p>
     * Note: Remove the PlaceholderAPI comment if you already hook it.
     */
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