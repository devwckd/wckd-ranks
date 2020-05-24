package co.wckd.ranks.util;

import co.wckd.ranks.entity.RankType;
import org.bukkit.entity.Player;

public class Strings {

    public static String prepareVipTypeMessage(String message, Player player, RankType type) {

        return message
                .replace("{player}", player.getName())
                .replace("{name}", type.getIdentifier())
                .replace("{pretty_name}", type.getPrettyName());
    }

}
