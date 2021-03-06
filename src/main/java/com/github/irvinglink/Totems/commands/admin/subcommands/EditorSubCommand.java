package com.github.irvinglink.Totems.commands.admin.subcommands;

import com.github.irvinglink.Totems.commands.builders.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EditorSubCommand implements SubCommand {

    @Override
    public String getName() {
        return "editor";
    }

    @Override
    public String getDescription() {
        return "Opens the editor";
    }

    @Override
    public String getSyntax() {
        return "/totemadmin editor";
    }

    /*
        /totem editor
     */
    @Override
    public void perform(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if (args.length == 1) {
            plugin.getEditorManager().toggleEditor(player.getUniqueId());
            return;
        }

    }

}
