package cn.zmy.banzhixing.main.helper;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

public final class DialHelper {
    private DialHelper() {

    }

    public static void dial(Context context, String phoneNumber) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + phoneNumber);
            intent.setData(data);
            context.startActivity(intent);
        }
    }
}
