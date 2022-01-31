package com.github.irvinglink.Totems.commands.admin;

import com.github.irvinglink.Totems.commands.admin.subcommands.EditorSubCommand;
import com.github.irvinglink.Totems.commands.builders.CommandBuilder;
import com.github.irvinglink.Totems.commands.builders.SubCommand;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TotemAdminCommand extends CommandBuilder {

    private final List<SubCommand> subCommands = new ArrayList<>();

    public TotemAdminCommand(String cmdName, String permission, boolean console) {
        super(cmdName, permission, console);

        subCommands.add(new EditorSubCommand());

    }


    @Override
    protected void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0 || (args[0].equalsIgnoreCase("help") && (args.length == 2 || args.length == 1))) {


            // PAGE SYSTEM
            if (args.length == 2 && !(NumberUtils.isNumber(args[1]))) {
                player.sendMessage(Objects.requireNonNull(chat.replace(player,plugin.getLang().getString("Only_Numbers") , true)));
                return;
            }

            int currentPage = (args.length == 2) ? (Integer.parseInt(args[1]) == 0) ? 1 : Integer.parseInt(args[1]) : 1;

            final int perpageCmds = Math.min(subCommands.size(), 8);

            int highestNumber = currentPage * perpageCmds;
            if (highestNumber > subCommands.size()) highestNumber = subCommands.size();

            int lowestNumber = highestNumber - perpageCmds;

            int totalPages = (int) (Math.ceil((double) subCommands.size() / (double) perpageCmds));

            if (currentPage > totalPages) currentPage = totalPages;

            // MESSAGES
            StringBuilder a = new StringBuilder();
            a.append(chat.getPluginPrefix());
            a.append(" ");
            a.append("&aDeveloper System32 &aBeta&f: &6" + plugin.getVersion());
            player.sendMessage(chat.str(a.toString()));

            for (int i = lowestNumber; i < highestNumber; i++) {

                SubCommand subCommand = subCommands.get(i);
                StringBuilder x = new StringBuilder();
                x.append("&e");
                x.append(subCommand.getSyntax());
                x.append(" &7");
                x.append(subCommand.getDescription());

                TextComponent component = chat.clickedMessage_Hover(chat.str(x.toString()), chat.str("&eClick here!"), subCommand.getSyntax());
                player.spigot().sendMessage(component);

            }

            return;

        }

    }

}
