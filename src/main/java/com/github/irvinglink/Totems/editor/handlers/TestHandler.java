package com.github.irvinglink.Totems.editor.handlers;

import com.github.irvinglink.Totems.editor.EditorType;
import com.github.irvinglink.Totems.editor.IChatEditor;
import org.bukkit.entity.Player;

public class TestHandler implements IChatEditor<Object> {

    @Override
    public boolean onType(Player player, Object paramT, EditorType type, String paramString) {

        return false;
    }

}
