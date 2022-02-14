package com.github.irvinglink.Totems.editor.chat;

import com.github.irvinglink.Totems.MClass;
import com.github.irvinglink.Totems.utils.chat.Chat;
import org.bukkit.entity.Player;

public interface IChatEditor<T> {

    MClass plugin = MClass.getPlugin(MClass.class);
    Chat chat = plugin.getChat();

    boolean onType(Player player, T paramT, EditorType type, String paramString);

}
