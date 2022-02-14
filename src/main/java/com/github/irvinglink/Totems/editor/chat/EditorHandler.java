package com.github.irvinglink.Totems.editor.chat;

import com.github.irvinglink.Totems.editor.chat.handlers.TestHandler;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EditorHandler extends ChatEditor {

                    // id     handler
    private final Map<String, IChatEditor> handlers = new ConcurrentHashMap<>();

    public EditorHandler() {
        super(EditorType.class);
        handlers.put("test", new TestHandler());
    }

    @Override
    protected boolean onType(Player player, Object object, Enum<?> paramEnum, String paramString) {
        EditorType type = (EditorType) paramEnum;

        if (object instanceof Object || type == EditorType.EDITING_AN_OBJECT) {
            return handlers.get("test").onType(player, object, type, paramString);
        }

        return false;
    }


}
