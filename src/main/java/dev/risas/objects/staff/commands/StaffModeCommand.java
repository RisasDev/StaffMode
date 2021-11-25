package dev.risas.objects.staff.commands;

import dev.risas.objects.staff.Staff;
import dev.risas.utilities.ChatUtil;
import dev.risas.utilities.command.BaseCommand;
import dev.risas.utilities.command.Command;
import dev.risas.utilities.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * Created by Risas
 * Project: StaffMode
 * Date: 23-11-2021 - 21:53
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */
public class StaffModeCommand extends BaseCommand {

    @Command(name = "staffmode", aliases = {"staff", "mod"}, permission = "electronstaff.command.staffmode")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        Staff staff = Staff.getStaff(player.getUniqueId());

        if (staff == null) {
            player.sendMessage(ChatUtil.translate("&cYou are not a staff member!"));
            return;
        }

        if (staff.isStaffMode()) {
            staff.disableStaffMode(true);
        }
        else {
            staff.enableStaffMode(true);
        }
    }
}
