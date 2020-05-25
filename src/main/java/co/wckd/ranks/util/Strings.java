package co.wckd.ranks.util;

import co.wckd.ranks.entity.rank.RankType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Strings {

    public static final String AVAILABLE_CHARACTERS = "abcdefghijlkmnopqrstuwxyzABCDEFGHIJKLMNOPQRSTUWXYZ0123456789";
    public static final Random RANDOM = new Random();

    public static String prepareVipTypeMessage(String message, Player player, RankType type) {
        return message
                .replace("{player}", player.getName())
                .replace("{name}", type.getIdentifier())
                .replace("{pretty_name}", type.getPrettyName());
    }

    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String generateKey() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                stringBuilder.append(AVAILABLE_CHARACTERS.charAt(RANDOM.nextInt(AVAILABLE_CHARACTERS.length())));
            }
            if (i != 3) stringBuilder.append("-");
        }

        return stringBuilder.toString();
    }

}
