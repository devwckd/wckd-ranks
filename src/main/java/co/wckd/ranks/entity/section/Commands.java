package co.wckd.ranks.entity.section;

import co.wckd.ranks.entity.RankType;
import co.wckd.ranks.util.Strings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Commands extends RankSection<List<String>> {

    @Override
    public void onActivate(Player player, RankType type) {
        if (!isActivatePresent()) return;
        executeCommands(player, type, getOnActivateSection());
    }

    @Override
    public void onChangeTo(Player player, RankType type) {
        if (!isChangeToPresent()) return;
        executeCommands(player, type, getOnChangeToSection());
    }

    @Override
    public void onChangedFrom(Player player, RankType type) {
        if (!isChangedFromPresent()) return;
        executeCommands(player, type, getOnChangedFromSection());
    }

    public void executeCommands(Player player, RankType type, List<String> section) {

        for (String command : section) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Strings.prepareVipTypeMessage(command, player, type));
        }

    }

}
