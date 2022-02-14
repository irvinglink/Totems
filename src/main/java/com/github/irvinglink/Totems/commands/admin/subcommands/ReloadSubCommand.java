package com.github.irvinglink.Totems.commands.admin.subcommands;

import com.github.irvinglink.Totems.commands.builders.SubCommand;
import com.github.irvinglink.Totems.enums.config.MESSAGES;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadSubCommand implements SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reloads the plugin files.";
    }

    @Override
    public String getSyntax() {
        return "/totemadmin reload";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if (args.length == 1) {
            plugin.reloadConfig();
            player.sendMessage(chat.replace(MESSAGES.RELOAD_FILES.getMessage(), true));
        } else
            player.sendMessage(chat.replace(getSyntax(), MESSAGES.WRONG_USAGE.getMessage(), true));

    }
}
