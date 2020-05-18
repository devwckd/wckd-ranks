package co.wckd.ranks.entity.section;

import co.wckd.ranks.entity.RankType;
import co.wckd.ranks.util.Strings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Commands extends VipSection<List<String>> {

    @Override
    public void apply(Player player, RankType type) {

        if (!isPresent()) return;

        for (String command : getSection()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Strings.prepareVipTypeMessage(command, player, type));
        }

    }

}
