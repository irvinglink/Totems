package com.github.irvinglink.Totems.commands.admin;

import com.github.irvinglink.Totems.commands.admin.subcommands.EditorSubCommand;
import com.github.irvinglink.Totems.commands.admin.subcommands.ReloadSubCommand;
import com.github.irvinglink.Totems.commands.builders.CommandBuilder;
import com.github.irvinglink.Totems.commands.builders.SubCommand;
import com.github.irvinglink.Totems.enums.config.MESSAGES;
import com.github.irvinglink.Totems.utils.Paginator;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TotemAdminCommand extends CommandBuilder implements TabCompleter {

    private final List<SubCommand> subCommands = new ArrayList<>();

    public TotemAdminCommand(String cmdName, String permission, boolean console) {
        super(cmdName, permission, console);

        subCommands.add(new EditorSubCommand());
        subCommands.add(new ReloadSubCommand());

    }


    @Override
    protected void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0 || (args[0].equalsIgnoreCase("help") && (args.length == 2 || args.length == 1))) {

            // PAGE SYSTEM
            if (args.length == 2 && !(NumberUtils.isNumber(args[1]))) {
                player.sendMessage(Objects.requireNonNull(chat.replace(player, MESSAGES.ONLY_NUMBERS.getMessage(), true)));
                return;
            }

            int currentPage = (args.length == 2) ? (Integer.parseInt(args[1]) == 0) ? 1 : Integer.parseInt(args[1]) : 1;
            final int perpageCmds = Math.min(subCommands.size(), 8);


            // MESSAGES
            StringBuilder a = new StringBuilder();
            a.append(chat.getPluginPrefix());
            a.append(" ");
            a.append("&a------------ &bHelp Page&a ------------");
            player.sendMessage(chat.str(a.toString()));

            new Paginator<SubCommand>().getPageElements(currentPage, perpageCmds, subCommands).forEach(subCommand -> {

                StringBuilder x = new StringBuilder();
                x.append("&e");
                x.append(subCommand.getSyntax());
                x.append(" &7");
                x.append(subCommand.getDescription());

                TextComponent component = chat.clickedMessage_Hover(chat.str(x.toString()), chat.str("&eClick here!"), subCommand.getSyntax());
                player.spigot().sendMessage(component);
            });

            return;

        }

        int attempts = 0;

        for (int i = 0; i < subCommands.size(); i++) {

            SubCommand subCommand = subCommands.get(i);

            if (args[0].equalsIgnoreCase(subCommand.getName())) {
                subCommand.perform(sender, args);
                return;
            }

            attempts++;
        }

        if (attempts == subCommands.size())
            player.sendMessage(Objects.requireNonNull(chat.replace(player, MESSAGES.COMMAND_NO_EXISTS.getMessage(), true)));

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return null;

        List<String> output = new ArrayList<>();

        if (args.length == 1) {

            List<String> subCommandStrList = subCommands.stream().map(SubCommand::getName).collect(Collectors.toList());

            if (!(args[0].isEmpty())) {

                subCommandStrList.forEach(x -> {
                    if (x.toLowerCase().startsWith((args[0].toLowerCase()))) output.add(x);
                });

            } else return subCommandStrList;

        }

        return output;
    }
}
