package co.wckd.vips.util;

import co.wckd.vips.entity.VipType;
import co.wckd.vips.entity.section.PrettyName;
import org.bukkit.entity.Player;

public class Strings {

    public static String prepareVipTypeMessage(String message, Player player, VipType type) {
        PrettyName prettyName = type.getPrettyName();
        String prettyNameString = prettyName.isPresent() ? prettyName.getSection() : type.getIdentifier();

        return message
                .replace("{player}", player.getName())
                .replace("{name}", type.getIdentifier())
                .replace("{pretty_name}", prettyNameString);
    }

}
