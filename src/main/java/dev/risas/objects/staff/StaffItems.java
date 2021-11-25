package dev.risas.objects.staff;

import dev.risas.utilities.ChatUtil;
import dev.risas.utilities.ItemBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
public enum StaffItems {

    COMPASS("&6Compass", new ItemStack(Material.COMPASS),0 ,0),
    INSPECTOR("&6Inspector", new ItemStack(Material.BOOK),0,1),
    WORLD_EDIT("&6World Edit", new ItemStack(Material.WOOD_AXE),0,2),
    FREEZE("&6Freeze", new ItemStack(Material.PACKED_ICE), 0,4),
    RANDOM_TELEPORT("&6Random Teleport", new ItemStack(Material.WATCH), 0,7),
    VANISH_OFF("&6Vanish&7: &c&lOFF", new ItemStack(Material.INK_SACK), 8,8),
    VANISH_ON("&6Vanish&7: &a&lON", new ItemStack(Material.INK_SACK), 10,8);

    private final String displayName;
    private final ItemStack itemStack;
    private final int data;
    private final int slot;

    public ItemStack getItem() {
        return new ItemBuilder(this.itemStack)
                .name(ChatUtil.translate(this.displayName))
                .data(this.data)
                .build();
    }

    public boolean canUse(ItemStack itemStack) {
        return itemStack.isSimilar(getItem());
    }

    public static void giveItems(Staff staff) {
        for (StaffItems item : StaffItems.values()) {
            staff.getPlayer().getInventory().setItem(item.getSlot(), item.getItem());
        }

        staff.getPlayer().getInventory().setArmorContents(staff.getArmorStaff());
        staff.getPlayer().updateInventory();
    }
}
