package cn.zmy.banzhixing.emergency.helper;

import android.content.Context;

import cn.zmy.banzhixing.utils.SharedPreferencesUtils;

public final class EmergencyHelper {
    private static final String KEY_SP_NAME = "emergency";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";

    private EmergencyHelper() {

    }

    public static String getEmergencyPhoneNumber(Context context) {
        return SharedPreferencesUtils.getString(context, KEY_SP_NAME, KEY_PHONE, "");
    }

    public static void setEmergencyPhoneNumber(Context context, String phone) {
        SharedPreferencesUtils.setString(context, KEY_SP_NAME, KEY_PHONE, phone);
    }

    public static String getEmergencyName(Context context) {
        return SharedPreferencesUtils.getString(context, KEY_SP_NAME, KEY_NAME, "");
    }

    public static void setEmergencyName(Context context, String name) {
        SharedPreferencesUtils.setString(context, KEY_SP_NAME, KEY_NAME, name);
    }
}
