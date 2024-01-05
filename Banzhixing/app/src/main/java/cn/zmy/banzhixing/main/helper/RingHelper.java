package cn.zmy.banzhixing.main.helper;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

public final class RingHelper {
    private static MediaPlayer sMediaPlayer;

    private RingHelper() {

    }

    public static void enableRing(Context context, boolean enable) {
        try {
            if (enable) {
                Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                if (sMediaPlayer == null) {
                    sMediaPlayer = new MediaPlayer();
                }
                if (sMediaPlayer.isPlaying()) {
                    sMediaPlayer.stop();
                    sMediaPlayer.reset();
                }
                sMediaPlayer.setDataSource(context, alert);
                sMediaPlayer.setVolume(1.0f, 1.0f);
                sMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);//告诉mediaPlayer播放的是铃声流
                sMediaPlayer.setLooping(true);
                sMediaPlayer.prepare();
                sMediaPlayer.start();
            } else {
                release();
            }

        } catch (Exception e) {
            e.printStackTrace();
            release();
        }
    }

    public static void release() {
        if (sMediaPlayer != null) {
            sMediaPlayer.stop();
            sMediaPlayer.release();
            sMediaPlayer = null;
        }
    }
}
