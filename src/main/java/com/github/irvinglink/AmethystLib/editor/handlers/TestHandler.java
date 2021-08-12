package com.github.irvinglink.AmethystLib.editor.handlers;

import com.github.irvinglink.AmethystLib.editor.EditorType;
import com.github.irvinglink.AmethystLib.editor.IChatEditor;
import org.bukkit.entity.Player;

public class TestHandler implements IChatEditor<Object> {

    @Override
    public boolean onType(Player player, Object paramT, EditorType type, String paramString) {

        return false;
    }

}
