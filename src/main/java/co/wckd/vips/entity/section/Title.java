package co.wckd.vips.entity.section;

import co.wckd.vips.entity.VipType;
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
                onlinePlayer.sendTitle(section.get(0), section.get(1));
            }
        } else {
            player.sendTitle(section.get(0), section.get(1));
        }

    }

}
