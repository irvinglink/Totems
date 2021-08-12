package com.github.irvinglink.AmethystLib.editor;

import com.github.irvinglink.AmethystLib.MClass;
import com.github.irvinglink.AmethystLib.utils.chat.Chat;
import org.bukkit.entity.Player;

public interface IChatEditor<T> {

    MClass plugin = MClass.getPlugin(MClass.class);
    Chat chat = plugin.getChat();

    boolean onType(Player player, T paramT, EditorType type, String paramString);

}
