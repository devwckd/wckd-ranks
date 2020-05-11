package co.wckd.vips.util;

import co.wckd.vips.VipsPlugin;

public class Logs {

    public static void consoleLog(String toLog) {
        System.out.println("[" + VipsPlugin.getInstance().getDescription().getName() + "] " + toLog);
    }

}
