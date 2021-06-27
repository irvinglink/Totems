package com.github.irvinglink.AmethystLib.utils.chat;

import com.github.irvinglink.AmethystLib.MClass;
import org.bukkit.OfflinePlayer;

public interface IReplacementHook {

    MClass plugin = MClass.getPlugin(MClass.class);

    String replace(OfflinePlayer paramPlayer1, OfflinePlayer paramPlayer2, String paramString1, String var);

}
