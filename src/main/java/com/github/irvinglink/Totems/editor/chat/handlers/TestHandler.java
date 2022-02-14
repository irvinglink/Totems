package com.github.irvinglink.Totems.editor.chat.handlers;

import com.github.irvinglink.Totems.editor.chat.EditorType;
import com.github.irvinglink.Totems.editor.chat.IChatEditor;
import org.bukkit.entity.Player;

public class TestHandler implements IChatEditor<Object> {

    @Override
    public boolean onType(Player player, Object paramT, EditorType type, String paramString) {

        return false;
    }

}
