package co.wckd.ranks.util;

import co.wckd.ranks.RanksPlugin;

public class Logs {

    public static void consoleLog(String toLog) {
        System.out.println("[" + RanksPlugin.getInstance().getDescription().getName() + "] " + toLog);
    }

}
