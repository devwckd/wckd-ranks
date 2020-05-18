package co.wckd.ranks.util;

import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Lang {

    private final FileConfiguration lang;
    private final String prefix;
    private final List<String> times;

    public Lang(FileConfiguration lang) {
        this.lang = lang;
        this.prefix = lang.getString("prefix", "");
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

        long hour = (millis / (1000 * 60 * 60)) % 24;
        long day = (millis / (1000 * 60 * 60 * 24)) % 7;
        long week = (millis / (1000 * 60 * 60 * 24 * 7)) % 30;
        double month = ((millis / (2.628e+9)) % 12);
        double year = ((millis / (3.154e+10)) % 10);


        StringBuilder stringBuilder = new StringBuilder();

        if (year != 0)
            stringBuilder.append(year).append(" ").append(checkPlural(year, times.get(8), times.get(9)));

        if (month != 0)
            stringBuilder.append(month).append(" ").append(checkPlural(month, times.get(6), times.get(7)));

        if (week != 0)
            stringBuilder.append(week).append(" ").append(checkPlural(week, times.get(4), times.get(5)));

        if (day != 0)
            stringBuilder.append(day).append(" ").append(checkPlural(day, times.get(2), times.get(3)));

        if (hour != 0)
            stringBuilder.append(hour).append(" ").append(checkPlural(hour, times.get(0), times.get(1)));

        return stringBuilder.toString();

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
