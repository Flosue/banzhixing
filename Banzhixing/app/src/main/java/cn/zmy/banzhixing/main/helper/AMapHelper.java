package cn.zmy.banzhixing.main.helper;

import android.annotation.SuppressLint;
import android.location.Location;

public final class AMapHelper {
    private static final String APP_KEY = "222816a5bd3b329cd7c50acc6e27391a";

    private AMapHelper() {

    }

    @SuppressLint("DefaultLocale")
    public static String getMyLocationWebUrl(Location location) {
        if(location == null) {
            return null;
        }
        return String.format("https://m.amap.com/navi/?dest=%f,%f&destName=Location&hideRouteIcon=1&key=%s",
                location.getLongitude(), location.getLatitude(), APP_KEY);
    }
}
