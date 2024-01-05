package cn.zmy.banzhixing.main.helper;

import android.content.Context;
import android.content.Intent;

public final class ShareHelper {
    private ShareHelper() {

    }

    public static void shareText(Context context, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        context.startActivity(intent);
    }
}
