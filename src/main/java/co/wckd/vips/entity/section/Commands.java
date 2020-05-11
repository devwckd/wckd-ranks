package co.wckd.vips.entity.section;

import co.wckd.vips.entity.VipType;
import co.wckd.vips.util.Strings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Commands extends VipSection<List<String>> {

    @Override
    public void apply(Player player, VipType type) {

        if (!isPresent()) return;

        for (String command : getSection()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Strings.prepareVipTypeMessage(command, player, type));
        }

    }

}
