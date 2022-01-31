package com.github.irvinglink.Totems.commands.admin.subcommands;

import com.github.irvinglink.Totems.commands.builders.SubCommand;
import org.bukkit.command.CommandSender;

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
        return "/totem editor";
    }

    /*
        /totem editor
     */
    @Override
    public void perform(CommandSender sender, String[] args) {

        if (args.length == 1) {
            // CODIGO
            return;
        }

    }

}
