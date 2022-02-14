package com.github.irvinglink.Totems.editor.chat;

import com.github.irvinglink.Totems.MClass;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Map;

public abstract class ChatEditor implements Listener {


    private final MClass plugin = MClass.getPlugin(MClass.class);
    private final Class<?> type;

    public ChatEditor(Class<?> type) {
        this.type = type;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        Map.Entry<Enum<?>, Object> editor = EditorManager.getEditor(player);
        if (editor == null) return;

        Enum<?> type = editor.getKey();

        event.getRecipients().clear();
        event.setCancelled(true);

        String msg = event.getMessage();
        if (plugin.getChat().removeColor(msg).equalsIgnoreCase("exit")) {
            EditorManager.quitEditor(player);
            return;
        }

        this.plugin.getServer().getScheduler().runTask(plugin, () ->
        {
            if (onType(player, editor.getValue(), type, msg)) EditorManager.quitEditor(player);
        });
    }


    public Class<?> getType() {
        return type;
    }

    protected abstract boolean onType(Player player, Object object, Enum<?> paramEnum, String paramString);

}
