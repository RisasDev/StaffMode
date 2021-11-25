package dev.risas.utilities;

import dev.risas.objects.staff.Staff;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Risas
 * Project: StaffMode
 * Date: 23-11-2021 - 2:09
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@UtilityClass
public class PlayerUtil {

    public void clear(Player player, boolean armor, boolean inventory) {
        if (armor) player.getInventory().setArmorContents(null);
        if (inventory) player.getInventory().clear();
    }

    public void denyMovement(final Player player) {
        player.setWalkSpeed(0.0F);
        player.setFlySpeed(0.0F);
        player.setFoodLevel(1);
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 200));
    }

    public void allowMovement(final Player player) {
        player.setWalkSpeed(0.2F);
        player.setFlySpeed(0.1F);
        player.setFoodLevel(20);
        player.removePotionEffect(PotionEffectType.JUMP);
    }

    public Inventory customPlayerInventory(Player target) {
        Inventory inventory = Bukkit.getServer().createInventory(null, 54, "Inventario de " + target.getName());

        inventory.setContents(target.getInventory().getContents());

        inventory.setItem(45, target.getInventory().getHelmet() == null ? null : target.getInventory().getHelmet());
        inventory.setItem(46, target.getInventory().getChestplate() == null ? null : target.getInventory().getChestplate());
        inventory.setItem(47, target.getInventory().getLeggings() == null ? null : target.getInventory().getLeggings());
        inventory.setItem(48, target.getInventory().getBoots() == null ? null : target.getInventory().getBoots());

        return inventory;
    }

    public List<Player> getOnlinePlayers() {
        List<Player> playerList = new ArrayList<>();

        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            Staff staff = Staff.getStaff(online.getUniqueId());
            if (staff == null) playerList.add(online);
        }

        return playerList;
    }
}
