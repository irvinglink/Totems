package com.github.irvinglink.AmethystLib.commands.builders;

import com.github.irvinglink.AmethystLib.MClass;
import com.github.irvinglink.AmethystLib.utils.chat.Chat;
import org.bukkit.command.CommandSender;


public interface SubCommand extends Runnable {

    MClass plugin = MClass.getPlugin(MClass.class);
    Chat chat = plugin.getChat();

    String getName();

    String getDescription();

    String getSyntax();

    void perform(CommandSender sender, String[] args);

}
