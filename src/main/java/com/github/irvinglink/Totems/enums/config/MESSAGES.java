package com.github.irvinglink.Totems.enums.config;

import com.github.irvinglink.Totems.MClass;
import lombok.Getter;

import java.util.Objects;

public enum MESSAGES {

    PREFIX("prefix", "&7[&bTotems&7] "),
    COMMAND_NO_EXISTS("Command_No_Exists", "%totems_prefix% &cThat command does not exists&7."),
    NO_PERMISSION("No_Permission", "%totems_prefix% &cYou do not have permission to use this command."),
    NO_PERMISSION_CONSOLE("No_Permission_Console", "%totems_prefix% &cThis action can not be performed via console&7."),
    WRONG_USAGE("Wrong_Usage", "%totems_prefix% &cWrong usage. &b%totems_command_syntax%"),
    NOT_ENOUGH_ARGS("Not_Enough_Args", "%totems_prefix% &cNot enough arguments."),
    NOT_USE_SYMBOLS("Not_Use_Symbols", "%totems_prefix% &cYou can not use symbols&7."),
    RELOAD_FILES("Reload_Files", "%totems_prefix% &aConfiguration files had been reloaded&7."),
    ONLY_NUMBERS("Only_Numbers", "%totems_prefix% &cOnly numbers are allowed&7."),
    NO_COLORS("No_Colors", "%totems_prefix% &cYou can not use colors&7."),
    PLAYER_NOT_FOUND("Player_Not_Found", "%totems_prefix% &cPlayer not found."),
    PLAYER_NOT_ONLINE("Player_Not_Online", "%totems_prefix% &cPlayer is not online."),

    COMMAND_TOTEMADMIN_EDITOR_ENABLE("Commands.TotemAdmin.Editor_Enable", "%totems_prefix% &aTotemEditor has been enabled."),
    COMMAND_TOTEMADMIN_EDITOR_DISABLE("Commands.TotemAdmin.Editor_Disable", "%totems_prefix% &aTotemEditor has been disabled."),

    ;


    private final MClass plugin = MClass.getPlugin(MClass.class);
    @Getter private final String path;
    @Getter private final String defaultMessage;

    MESSAGES(String path, String defaultMessage) {
        this.path = path;
        this.defaultMessage = defaultMessage;
    }

    public String getMessage() {
        String message = Objects.requireNonNull(plugin.getLang().getString(path));
        return (message.equals("null")) ? defaultMessage : message;
    }

}
