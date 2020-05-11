package co.wckd.vips.util;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class TimeUtils {

    private TimeUtils() {
    }

    public static final Map<Character, Long> TIME_VALUES = new ImmutableMap.Builder<Character, Long>()
            .put('h', 60 * 60 * 1000L)
            .put('d', 24 * 60 * 60 * 1000L)
            .put('w', 7 * 24 * 60 * 60 * 1000L)
            .put('m', 30 * 24 * 60 * 60 * 1000L)
            .build();

    public static Long millisFromString(String time) {

        int timeLenght = time.length();
        char last = time.charAt(timeLenght - 1);

        String toParse = time;
        Long multiplier = 1000L;

        if (Character.isLetter(last)) {

            toParse = time.substring(0, timeLenght - 1);
            multiplier = TIME_VALUES.get(last);
            if (multiplier == null) return null;

        }

        int timeInt;

        try {
            timeInt = Integer.parseInt(toParse);
        } catch (Exception $) {
            return null;
        }

        return timeInt * multiplier;

    }

    public static String format(long time) {

        long second = (time / 1000) % 60;
        long minute = (time / (1000 * 60)) % 60;
        long hour = (time / (1000 * 60 * 60)) % 24;
        long day = (time / (1000 * 60 * 60 * 24)) % 7;
        long week = (time / (1000 * 60 * 60 * 24 * 7)) % 30;

        StringBuilder builder = new StringBuilder();

        if (week > 0) {
            builder.append(week).append(" ").append(week == 1 ? "week" : "weeks").append(" ");
        }

        if (day > 0) {
            builder.append(day).append(" ").append(day == 1 ? "day" : "days").append(" ");
        }

        if (hour > 0) {
            builder.append(hour).append(" ").append(hour == 1 ? "hour" : "hours").append(" ");
        }

        if (minute > 0) {
            builder.append(minute).append(" ").append(minute == 1 ? "minute" : "minutes").append(" ");
        }

        if (second > 0) {
            builder.append(second).append(" ").append(second == 1 ? "second" : "seconds").append(" ");
        }

        return builder.toString().trim();

    }

}
