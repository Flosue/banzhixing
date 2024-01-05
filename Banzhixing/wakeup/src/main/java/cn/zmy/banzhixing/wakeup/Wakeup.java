package cn.zmy.banzhixing.wakeup;

import android.content.Context;
import android.os.Environment;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.VoiceWakeuper;
import com.iflytek.cloud.WakeuperListener;
import com.iflytek.cloud.util.ResourceUtil;

public final class Wakeup {
    private static final String APP_ID = "5eb6cd44";
    private static final int curThresh = 1450;
    private static final String keep_alive = "1";
    private static final String ivwNetMode = "0";
    // 语音唤醒对象
    private static VoiceWakeuper sVoiceWakeuper;

    private Wakeup() {

    }

    public static void init(Context context) {
        StringBuffer param = new StringBuffer();
        param.append("appid=" + APP_ID);
        param.append(",");
        // 设置使用v5+
        param.append(SpeechConstant.ENGINE_MODE+"="+SpeechConstant.MODE_MSC);
        SpeechUtility.createUtility(context, param.toString());
    }

    public static void start(Context context, WakeuperListener listener) {
        sVoiceWakeuper = VoiceWakeuper.createWakeuper(context, null);
        // 清空参数
        sVoiceWakeuper.setParameter(SpeechConstant.PARAMS, null);
        // 唤醒门限值，根据资源携带的唤醒词个数按照“id:门限;id:门限”的格式传入
        sVoiceWakeuper.setParameter(SpeechConstant.IVW_THRESHOLD, "0:"+ curThresh);
        // 设置唤醒模式
        sVoiceWakeuper.setParameter(SpeechConstant.IVW_SST, "wakeup");
        // 设置持续进行唤醒
        sVoiceWakeuper.setParameter(SpeechConstant.KEEP_ALIVE, keep_alive);
        // 设置闭环优化网络模式
        sVoiceWakeuper.setParameter(SpeechConstant.IVW_NET_MODE, ivwNetMode);
        // 设置唤醒资源路径
        sVoiceWakeuper.setParameter(SpeechConstant.IVW_RES_PATH, getResource(context));
        // 设置唤醒录音保存路径，保存最近一分钟的音频
        sVoiceWakeuper.setParameter( SpeechConstant.IVW_AUDIO_PATH, Environment.getExternalStorageDirectory().getPath()+"/msc/ivw.wav" );
        sVoiceWakeuper.setParameter( SpeechConstant.AUDIO_FORMAT, "wav" );
        sVoiceWakeuper.startListening(listener);
    }

    public static void stop() {
        if (sVoiceWakeuper != null) {
            sVoiceWakeuper.stopListening();
        }
    }

    private static String getResource(Context context) {
        return ResourceUtil.generateResourcePath(
                context,
                ResourceUtil.RESOURCE_TYPE.assets,
                "ivw/"+ APP_ID +".jet");
    }
}
