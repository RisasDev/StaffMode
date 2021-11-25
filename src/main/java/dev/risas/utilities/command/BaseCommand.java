package dev.risas.utilities.command;

import dev.risas.StaffMode;

public abstract class BaseCommand {

    public BaseCommand() {
        StaffMode.get().getCommandManager().registerCommands(this);
    }

    public abstract void onCommand(CommandArgs command);
}
