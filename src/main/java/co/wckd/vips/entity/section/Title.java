package co.wckd.vips.entity.section;

import co.wckd.vips.entity.VipType;
import co.wckd.vips.util.Strings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Title extends VipSection<List<String>> {

    @Override
    public void apply(Player player, VipType type) {

        if (!isPresent()) return;

        List<String> section = getSection();
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
