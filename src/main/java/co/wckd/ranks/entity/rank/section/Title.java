package co.wckd.ranks.entity.rank.section;

import co.wckd.ranks.entity.rank.RankType;
import co.wckd.ranks.util.Strings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Title extends RankSection<List<String>> {

    @Override
    public void onActivate(Player player, RankType type) {
        if (!isActivatePresent()) return;
        sendTitle(player, type, getOnActivateSection());
    }

    @Override
    public void onChangeTo(Player player, RankType type) {
        if (!isChangeToPresent()) return;
        sendTitle(player, type, getOnChangeToSection());
    }

    @Override
    public void onChangedFrom(Player player, RankType type) {
        if (!isChangedFromPresent()) return;
        sendTitle(player, type, getOnChangedFromSection());
    }

    private void sendTitle(Player player, RankType type, List<String> section) {
        String range = section.get(0);
        if (range.equalsIgnoreCase("all")) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendTitle(Strings.prepareVipTypeMessage(section.get(1), player, type),
                        Strings.prepareVipTypeMessage(section.get(2), player, type));
            }
        } else {
            player.sendTitle(Strings.prepareVipTypeMessage(section.get(1), player, type),
                    Strings.prepareVipTypeMessage(section.get(2), player, type));
        }
    }

}
