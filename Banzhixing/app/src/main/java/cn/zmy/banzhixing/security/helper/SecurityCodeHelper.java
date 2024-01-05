package cn.zmy.banzhixing.security.helper;

import android.content.Context;

import cn.zmy.banzhixing.utils.SharedPreferencesUtils;

public final class SecurityCodeHelper {
    private static final String SP_SECURITY = "security";
    private static final String KEY_SECURITY_CODE = "security_code";

    private SecurityCodeHelper() {

    }

    public static String getSecurityCode(Context context) {
        return SharedPreferencesUtils.getString(context, SP_SECURITY, KEY_SECURITY_CODE, "");
    }

    public static void setSecurityCode(Context context, String code) {
        SharedPreferencesUtils.setString(context, SP_SECURITY, KEY_SECURITY_CODE, code);
    }
}
