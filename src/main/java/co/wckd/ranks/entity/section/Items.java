package co.wckd.ranks.entity.section;

import co.wckd.ranks.entity.RankType;
import co.wckd.ranks.util.Stacks;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Items extends RankSection<List<ItemStack>> {

    @Override
    public void onActivate(Player player, RankType type) {
        if (!isActivatePresent()) return;
        giveItems(player, type, getOnActivateSection());
    }

    @Override
    public void onChangeTo(Player player, RankType type) {
        if (!isChangeToPresent()) return;
        giveItems(player, type, getOnChangeToSection());
    }

    @Override
    public void onChangedFrom(Player player, RankType type) {
        if (!isChangedFromPresent()) return;
        giveItems(player, type, getOnChangedFromSection());
    }

    public void giveItems(Player player, RankType type, List<ItemStack> section) {
        for (ItemStack stack : section) {
            Stacks.give(player, stack);
        }
    }

}
