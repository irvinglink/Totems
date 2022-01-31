package com.github.irvinglink.Totems.commands.builders;

import com.github.irvinglink.Totems.MClass;
import com.github.irvinglink.Totems.utils.chat.Chat;
import org.bukkit.command.CommandSender;


public interface SubCommand {

    MClass plugin = MClass.getPlugin(MClass.class);
    Chat chat = plugin.getChat();

    String getName();

    String getDescription();

    String getSyntax();

    void perform(CommandSender sender, String[] args);

}
