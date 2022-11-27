package dev.wave.punishment;

public class Util {

    public static long getTime(final String[] args) {
        final String arg = args[1].toLowerCase();

        String modifierString;
        int modifier;
        if (arg.contains("hour")) {
            modifier = 3600;
            modifierString = "hour";
        } else if (arg.contains("minute")) {
            modifierString = "minute";
            modifier = 60;
        } else if (arg.contains("second")) {
            modifierString = "second";
            modifier = 1;
        } else if (arg.contains("week")) {
            modifierString = "week";
            modifier = 604800;
        } else if (arg.contains("day")) {
            modifierString = "day";
            modifier = 86400;
        } else if (arg.contains("year")) {
            modifierString = "year";
            modifier = 31449600;
        } else if (arg.contains("month")) {
            modifierString = "month";
            modifier = 2620800;
        } else {
            modifier = 1;
            modifierString = "second";
        }

        double time = 0.0D;

        String timeString = args[1].replace(modifierString, "").replace("s", "");

        try {
            time = Double.parseDouble(timeString);
        } catch (NumberFormatException ignored) {
            return -1L;
        }

        return (long) (modifier * time) * 1000L;
    }

}
