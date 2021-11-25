package dev.risas.objects.staff.listeners;

import dev.risas.StaffMode;
import dev.risas.objects.freeze.Freeze;
import dev.risas.objects.staff.Staff;
import dev.risas.objects.staff.StaffItems;
import dev.risas.utilities.ChatUtil;
import dev.risas.utilities.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Risas
 * Project: StaffMode
 * Date: 23-11-2021 - 16:35
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */
public class StaffItemsListener implements Listener {

    public StaffItemsListener(StaffMode plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Staff staff = Staff.getStaff(event.getPlayer().getUniqueId());

        if (staff != null && staff.isStaffMode()) {

            if (event.getRightClicked() instanceof Player) {
                Player target = (Player) event.getRightClicked();

                if (StaffItems.FREEZE.canUse(staff.getPlayer().getItemInHand())) {
                    Freeze freeze;

                    if (Freeze.getFreezes().get(target.getUniqueId()) == null) {
                        freeze = new Freeze(target.getUniqueId());
                    }
                    else {
                        freeze = Freeze.getFreezes().get(target.getUniqueId());
                    }

                    freeze.setStaff(staff);

                    if (freeze.isFrozen()) {
                        freeze.unFreezePlayer(true);
                    }
                    else {
                        freeze.freezePlayer(true);
                    }
                }
                else if (StaffItems.INSPECTOR.canUse(staff.getPlayer().getItemInHand())) {
                    staff.getPlayer().openInventory(PlayerUtil.customPlayerInventory(target));
                }
            }
        }
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        Staff staff = Staff.getStaff(event.getPlayer().getUniqueId());

        if (staff != null && staff.isStaffMode()) {

            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                ItemStack item = staff.getPlayer().getInventory().getItemInHand();

                if (item == null || item.getType().equals(Material.AIR)) return;

                if (StaffItems.RANDOM_TELEPORT.canUse(item)) {
                    if (PlayerUtil.getOnlinePlayers().isEmpty()) {
                        staff.getPlayer().sendMessage(ChatUtil.translate("&cNo hay jugadores en el servidor."));
                        return;
                    }

                    Player randomPlayer = PlayerUtil.getOnlinePlayers().get(ThreadLocalRandom.current().nextInt(PlayerUtil.getOnlinePlayers().size()));

                    if (randomPlayer != null) {
                        staff.getPlayer().teleport(randomPlayer);
                        staff.getPlayer().sendMessage(ChatUtil.translate("&6Teletransportado a &e" + randomPlayer.getName() + "&6."));
                    }
                }
                else if (StaffItems.VANISH_ON.canUse(item)) {
                    staff.disableVanish(true);
                    staff.getPlayer().getInventory().setItem(StaffItems.VANISH_OFF.getSlot(), StaffItems.VANISH_OFF.getItem());
                }
                else if (StaffItems.VANISH_OFF.canUse(item)) {
                    staff.enableVanish(true);
                    staff.getPlayer().getInventory().setItem(StaffItems.VANISH_ON.getSlot(), StaffItems.VANISH_ON.getItem());
                }
            }
        }
    }
}
