package dev.risas.objects.freeze.listeners;

import dev.risas.StaffMode;
import dev.risas.objects.freeze.Freeze;
import dev.risas.objects.staff.Staff;
import dev.risas.utilities.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Risas
 * Project: StaffMode
 * Date: 24-11-2021 - 23:08
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */
public class FreezeChatListener implements Listener {

    public FreezeChatListener(StaffMode plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onFrozenChat(AsyncPlayerChatEvent event) {
        Freeze freeze = Freeze.getFreeze(event.getPlayer().getUniqueId());

        if (freeze != null && freeze.isFrozen()) {
            event.setCancelled(true);

            freeze.getPlayer().sendMessage(ChatUtil.translate("&c[Freeze Chat] &7" + freeze.getName() + ": &f" + event.getMessage()));

            Staff staff = freeze.getStaff();

            if (staff != null) {
                staff.getPlayer().sendMessage(ChatUtil.translate("&c[Freeze Chat] &7" + freeze.getName() + ": &f" + event.getMessage()));
            }
        }
    }
}
