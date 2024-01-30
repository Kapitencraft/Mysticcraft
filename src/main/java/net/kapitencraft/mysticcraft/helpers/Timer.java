package net.kapitencraft.mysticcraft.helpers;

import net.minecraft.Util;

public class Timer {
    private static long millis;

    public static void start() {
        millis = Util.getMillis();
    }

    public static long getPassedTime() {
        return Util.getMillis() - millis;
    }
}
