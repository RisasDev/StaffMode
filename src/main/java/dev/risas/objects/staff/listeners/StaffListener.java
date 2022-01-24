package dev.risas.objects.staff.listeners;

import dev.risas.StaffMode;
import dev.risas.objects.staff.Staff;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

/**
 * Created by Risas
 * Project: StaffMode
 * Date: 23-11-2021 - 0:25
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */
public class StaffListener implements Listener {

    public StaffListener(StaffMode plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onStaffJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("staffmode.staff")) {
            Staff staff = new Staff(player.getUniqueId());
            staff.enableStaffMode(true);
        }

        for (Staff staff : Staff.getStaffs().values()) {
            player.hidePlayer(staff.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onStaffLeave(PlayerQuitEvent event) {
        Staff staff = Staff.getStaff(event.getPlayer().getUniqueId());

        if (staff != null) {
            staff.disableStaffMode(true);
            Staff.getStaffs().remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    private void onStaffDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Staff staff = Staff.getStaff(event.getEntity().getUniqueId());

            if (staff != null) {
                if (staff.isStaffMode() || staff.isVanished()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onStaffHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Staff staff = Staff.getStaff(event.getDamager().getUniqueId());

            if (staff != null && staff.isStaffMode()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onStaffBreak(BlockBreakEvent event) {
        Staff staff = Staff.getStaff(event.getPlayer().getUniqueId());

        if (staff != null && staff.isStaffMode()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onStaffPlace(BlockPlaceEvent event) {
        Staff staff = Staff.getStaff(event.getPlayer().getUniqueId());

        if (staff != null && staff.isStaffMode()) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onStaffInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Staff staff = Staff.getStaff(player.getUniqueId());

        if (staff != null) {
            if (staff.isStaffMode()) {
                event.setCancelled(true);
                player.updateInventory();
            }
        }
    }

    @EventHandler
    private void onStaffPickupItem(PlayerPickupItemEvent event) {
        Staff staff = Staff.getStaff(event.getPlayer().getUniqueId());

        if (staff != null) {
            if (staff.isStaffMode() || staff.isVanished()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onStaffDropItem(PlayerDropItemEvent event) {
        Staff staff = Staff.getStaff(event.getPlayer().getUniqueId());

        if (staff != null && staff.isStaffMode()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onStaffChangeWorld(PlayerChangedWorldEvent event) {
        Staff staff = Staff.getStaff(event.getPlayer().getUniqueId());

        if (staff != null && staff.isVanished()) {
            staff.enableVanish(false);
        }
    }
}
