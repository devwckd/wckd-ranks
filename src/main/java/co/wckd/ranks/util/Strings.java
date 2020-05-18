package co.wckd.ranks.util;

import co.wckd.ranks.entity.RankType;
import co.wckd.ranks.entity.section.PrettyName;
import org.bukkit.entity.Player;

public class Strings {

    public static String prepareVipTypeMessage(String message, Player player, RankType type) {
        PrettyName prettyName = type.getPrettyName();
        String prettyNameString = prettyName.isPresent() ? prettyName.getSection() : type.getIdentifier();

        return message
                .replace("{player}", player.getName())
                .replace("{name}", type.getIdentifier())
                .replace("{pretty_name}", prettyNameString);
    }

}
