package com.github.irvinglink.AmethystLib.editor;

import com.github.irvinglink.AmethystLib.MClass;
import com.github.irvinglink.AmethystLib.utils.chat.Chat;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.AbstractMap;
import java.util.Map;
import java.util.WeakHashMap;

public class EditorManager implements Listener {

    private static final MClass plugin = MClass.getPlugin(MClass.class);
    private static final Chat chat = plugin.getChat();

    private static final Map<Player, Map.Entry<Enum<?>, Object>> editor_cache = new WeakHashMap<>();

    public static boolean isEditor(Player player) {
        return getEditor(player) != null;
    }

    public static Map.Entry<Enum<?>, Object> getEditor(Player player) {
        return editor_cache.getOrDefault(player, null);
    }


    public static void setEditor(Player player, Enum<?> type, Object object) {
        editor_cache.put(player, new AbstractMap.SimpleEntry<>(type, object));
        player.closeInventory();
        player.sendTitle(chat.replace(plugin.getLang().getString("Title.Editing_Title"), true), chat.replace(plugin.getLang().getString("Title.Editing_SubTitle"), true), 10, 40, 10);

        player.spigot().sendMessage(chat.clickedMessage(plugin.getLang().getString("Editor.Suggest_Message"), "exit"));

    }

    public static void quitEditor(Player player) {
        editor_cache.remove(player);
        player.sendTitle(chat.replace(plugin.getLang().getString("Title.Done_Title"), true), "", 10, 40, 10);
    }

}
