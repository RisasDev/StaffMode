package dev.risas;

import dev.risas.objects.freeze.FreezeHandler;
import dev.risas.objects.freeze.listeners.FreezeChatListener;
import dev.risas.objects.freeze.listeners.FreezeListener;
import dev.risas.objects.staff.StaffHandler;
import dev.risas.objects.staff.commands.FreezeCommand;
import dev.risas.objects.staff.commands.StaffModeCommand;
import dev.risas.objects.staff.commands.VanishCommand;
import dev.risas.objects.staff.listeners.StaffItemsListener;
import dev.risas.objects.staff.listeners.StaffListener;
import dev.risas.utilities.command.CommandManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class StaffMode extends JavaPlugin {

    private CommandManager commandManager;

    @Override
    public void onEnable() {
        this.commandManager = new CommandManager(this);

        new StaffListener(this);
        new StaffItemsListener(this);
        new FreezeListener(this);
        new FreezeChatListener(this);

        new StaffModeCommand();
        new VanishCommand();
        new FreezeCommand();
    }

    @Override
    public void onDisable() {
        StaffHandler.disable();
        FreezeHandler.disable();
    }

    public static StaffMode get() {
        return getPlugin(StaffMode.class);
    }
}