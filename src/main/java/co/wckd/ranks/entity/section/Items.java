package co.wckd.ranks.entity.section;

import co.wckd.ranks.entity.RankType;
import co.wckd.ranks.util.Stacks;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Items extends VipSection<List<ItemStack>> {

    @Override
    public void apply(Player player, RankType type) {

        if (!isPresent()) return;

        for (ItemStack stack : getSection()) {
            Stacks.give(player, stack);
        }

    }

}
