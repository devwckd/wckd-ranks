package co.wckd.ranks.entity.rank.section;

import co.wckd.ranks.entity.rank.RankType;
import org.bukkit.entity.Player;

import java.util.List;

import static co.wckd.ranks.util.Strings.colorize;
import static co.wckd.ranks.util.Strings.prepareVipTypeMessage;

public class Messages extends RankSection<List<String>> {

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
            player.sendMessage(colorize(prepareVipTypeMessage(command, player, type)));
        }

    }

}
