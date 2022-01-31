package com.github.irvinglink.Totems.utils.chat;


import com.github.irvinglink.Totems.MClass;
import org.bukkit.OfflinePlayer;

import java.util.Objects;

public class ReplacementHook implements IReplacementHook {

    private final MClass plugin = MClass.getPlugin(MClass.class);

    private final Chat chat = this.plugin.getChat();

    public String replace(OfflinePlayer player, OfflinePlayer target, String str, String var) {

        switch (var.toLowerCase()) {
            case "prefix":
                return this.chat.getPrefix();

            case "player_name":
                if (player == null) return "null";
                return player.getName();

            case "target_name":
                if (target == null) return "null";
                return target.getName();

            case "player_displayname":
                if (player == null) return "null";
                return Objects.requireNonNull(player.getPlayer()).getDisplayName();

            case "target_displayname":
                if (target == null) return "null";
                return Objects.requireNonNull(target.getPlayer()).getDisplayName();

            case "command_syntax":
            case "value":
                if (str != null) return str;
                break;

            default:
                break;
        }
        return "null";
    }
}
