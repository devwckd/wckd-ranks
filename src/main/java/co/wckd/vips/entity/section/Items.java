package co.wckd.vips.entity.section;

import co.wckd.vips.entity.VipType;
import co.wckd.vips.util.Stacks;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Items extends VipSection<List<ItemStack>> {

    @Override
    public void apply(Player player, VipType type) {

        if (!isPresent()) return;

        for (ItemStack stack : getSection()) {
            Stacks.give(player, stack);
        }

    }

}
