package co.wckd.vips.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Stacks {

    public static void give(Player player, ItemStack stack) {

        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation().add(0, 1, 0), stack);
            return;
        }
        player.getInventory().addItem(stack);

    }

}
