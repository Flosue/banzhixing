package cn.zmy.banzhixing.main.helper;

import android.content.Context;

import cn.zmy.banzhixing.utils.SharedPreferencesUtils;

public final class TimingProtectHelper {
    private static final String SP_TIMING_PROTECT = "timing_protect";
    private static final String KEY_PROTECT_TIME = "protect_time";

    private TimingProtectHelper() {

    }

    public static long getProtectTime(Context context) {
        return SharedPreferencesUtils.getLong(context, SP_TIMING_PROTECT, KEY_PROTECT_TIME, 0);
    }

    public static void setProtectTime(Context context, long time) {
        SharedPreferencesUtils.setLong(context, SP_TIMING_PROTECT, KEY_PROTECT_TIME, time);
    }

    public static int getCountdownSeconds(Context context) {
        long currentTime = System.currentTimeMillis();
        long lastProtectedTime = TimingProtectHelper.getProtectTime(context);
        return (int) ((lastProtectedTime - currentTime) / 1000);
    }
}
