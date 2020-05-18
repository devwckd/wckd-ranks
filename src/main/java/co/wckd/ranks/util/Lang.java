package co.wckd.ranks.util;

import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Lang {

    private final FileConfiguration lang;
    private final String prefix;
    private final List<String> times;

    public Lang(FileConfiguration lang) {
        this.lang = lang;
        this.prefix = ChatColor.translateAlternateColorCodes('&', lang.getString("prefix", ""));
        this.times = new ArrayList<>();
        loadTimes();
    }

    private String get(String path) {
        List<String> stringList = lang.getStringList(path);
        if (stringList.isEmpty()) return null;
        stringList.replaceAll(s -> s = ChatColor.translateAlternateColorCodes('&', s));
        return String.join("\n", stringList);
    }

    public void send(CommandSender sender, String path, Pair<String, String>... replaces) {
        String message = get(path);

        if (message == null) {
            // TODO: log
            return;
        }

        for (Pair<String, String> replace : replaces) {
            message = message.replace(replace.getKey(), replace.getRight());
        }
        sender.sendMessage(message.replace("{prefix}", prefix));
    }

    public String formatTime(long millis) {

        if (millis == -1) return times.get(10);

        long day = TimeUnit.MILLISECONDS.toDays(millis);
        final long year = day / 365;
        day %= 365;
        final long month = day / 30;
        day %= 30;
        final long week = day / 7;
        day %= 7;
        final long hour = TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis));

        StringBuilder stringBuilder = new StringBuilder(" ");

        if ((int) year != 0)
            stringBuilder.append((int) year).append(" ").append(checkPlural((int) year, times.get(8), times.get(9))).append(" ");

        if ((int) month != 0)
            stringBuilder.append((int) month).append(" ").append(checkPlural((int) month, times.get(6), times.get(7))).append(" ");

        if ((int) week != 0)
            stringBuilder.append((int) week).append(" ").append(checkPlural((int) week, times.get(4), times.get(5))).append(" ");

        if ((int) day != 0)
            stringBuilder.append((int) day).append(" ").append(checkPlural((int) day, times.get(2), times.get(3))).append(" ");

        if ((int) hour != 0)
            stringBuilder.append((int) hour).append(" ").append(checkPlural((int) hour, times.get(0), times.get(1))).append(" ");

        return stringBuilder.toString().trim();

    }

    private String checkPlural(double num, String singular, String plural) {
        if (num == 1) return singular;
        else return plural;
    }

    private void loadTimes() {
        times.add(lang.getString("hour", ""));
        times.add(lang.getString("hours", ""));
        times.add(lang.getString("day", ""));
        times.add(lang.getString("days", ""));
        times.add(lang.getString("week", ""));
        times.add(lang.getString("weeks", ""));
        times.add(lang.getString("month", ""));
        times.add(lang.getString("months", ""));
        times.add(lang.getString("year", ""));
        times.add(lang.getString("years", ""));
        times.add(lang.getString("lifetime", ""));
    }


}
