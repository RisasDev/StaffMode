package dev.risas.objects.freeze.listeners;

import dev.risas.StaffMode;
import dev.risas.objects.freeze.Freeze;
import dev.risas.objects.staff.StaffHandler;
import dev.risas.utilities.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Risas
 * Project: StaffMode
 * Date: 23-11-2021 - 21:29
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */
public class FreezeListener implements Listener {

    public FreezeListener(StaffMode plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onFrozenQuit(PlayerQuitEvent event) {
        Freeze freeze = Freeze.getFreeze(event.getPlayer().getUniqueId());

        if (freeze != null && freeze.isFrozen()) {
            StaffHandler.sendMessageAllStaff("&4" + freeze.getName() + " &7has left the server while frozen.", true);
            freeze.unFreezePlayer(false);
        }
    }

    @EventHandler
    private void onFrozenClickInventory(InventoryClickEvent event) {
        Freeze freeze = Freeze.getFreeze(event.getWhoClicked().getUniqueId());

        if (freeze != null && freeze.isFrozen()) {
            event.setCancelled(true);
            freeze.getPlayer().updateInventory();
        }
    }

    @EventHandler
    private void onFrozenBreak(BlockBreakEvent event) {
        Freeze freeze = Freeze.getFreeze(event.getPlayer().getUniqueId());

        if (freeze != null && freeze.isFrozen()) {
            event.setCancelled(true);
            freeze.getPlayer().sendMessage(ChatUtil.translate("&cYou can't break blocks while you're frozen."));
        }
    }

    @EventHandler
    private void onFrozenPlace(BlockPlaceEvent event) {
        Freeze freeze = Freeze.getFreeze(event.getPlayer().getUniqueId());

        if (freeze != null && freeze.isFrozen()) {
            event.setCancelled(true);
            freeze.getPlayer().sendMessage(ChatUtil.translate("&cYou can't place blocks while you're frozen."));
        }
    }

    @EventHandler
    private void onFrozenPearl(PlayerInteractEvent event) {
        Freeze freeze = Freeze.getFreeze(event.getPlayer().getUniqueId());

        if (freeze != null && freeze.isFrozen()) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (event.getItem() == null) return;

                if (event.getItem().getType().equals(Material.ENDER_PEARL)) {
                    event.setCancelled(true);
                    freeze.getPlayer().updateInventory();
                    freeze.getPlayer().sendMessage(ChatUtil.translate("&cYou can't use ender pearls while you're frozen."));
                }
            }
        }
    }

    @EventHandler
    private void onFrozenHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {

            Player player = (Player) event.getEntity();
            Player target = (Player) event.getDamager();

            Freeze playerFreeze = Freeze.getFreeze(player.getUniqueId());
            Freeze targetFreeze = Freeze.getFreeze(target.getUniqueId());

            if (playerFreeze != null && playerFreeze.isFrozen()) {
                event.setCancelled(true);
                target.sendMessage(ChatUtil.translate("&cYou can't hit " + playerFreeze.getName() + " while they're frozen."));
            }
            if (targetFreeze != null && targetFreeze.isFrozen()) {
                event.setCancelled(true);
                target.sendMessage(ChatUtil.translate("&cYou can't damage players while you're frozen."));
            }
        }
    }

    @EventHandler
    private void onFrozenDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Freeze freeze = Freeze.getFreeze(event.getEntity().getUniqueId());

            if (freeze != null && freeze.isFrozen()) {
                event.setCancelled(true);
            }
        }
    }
}
