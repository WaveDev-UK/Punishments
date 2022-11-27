package dev.wave.punishment;

public class Util {

    public static long getTime(final String[] args) {
        final String arg = args[1].toLowerCase();
        int modifier;
        if ("hour".startsWith(arg)) {
            modifier = 3600;
        } else if ("minute".startsWith(arg)) {
            modifier = 60;
        } else if ("second".startsWith(arg)) {
            modifier = 1;
        } else if ("week".startsWith(arg)) {
            modifier = 604800;
        } else if ("day".startsWith(arg)) {
            modifier = 86400;
        } else if ("year".startsWith(arg)) {
            modifier = 31449600;
        } else if ("month".startsWith(arg)) {
            modifier = 2620800;
        } else {
            modifier = 0;
        }

        double time = 0.0D;

        try {
            time = Double.parseDouble(args[1]);
        } catch (NumberFormatException ignored) {
            return -1L;
        }

        for (int j = 0; j < args.length - 2; ++j) {
            args[j] = args[(j + 2)];
        }

        args[args.length - 1] = "";
        args[args.length - 2] = "";
        return (long) (modifier * time) * 1000L;
    }

}
