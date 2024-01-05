package cn.zmy.banzhixing.main;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

/**
 * 定时守护的后台服务
 * */
public class TimingProtectService extends Service {
    public static final String ACTION_TIME_COME = "ACTION_TIME_COME";
    private static final String KEY_COUNT_DOWN_SECONDS = "countdown_seconds";

    public static void start(Context context, int countdownSeconds) {
        Intent intent = new Intent(context, TimingProtectService.class);
        intent.putExtra(KEY_COUNT_DOWN_SECONDS, countdownSeconds);
        context.startService(intent);
    }

    public static void stop(Context context) {
        Intent intent = new Intent(context, TimingProtectService.class);
        context.stopService(intent);
    }

    private ProtectTimingHandler mProtectTimingHandler;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int countdownSeconds = intent.getIntExtra(KEY_COUNT_DOWN_SECONDS, 0);
        if (countdownSeconds > 0) {
            startCountdown(countdownSeconds);
        } else {
            stopCountdown();
        }
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopCountdown();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startCountdown(int countdownSeconds) {
        stopCountdown();
        mProtectTimingHandler = new ProtectTimingHandler(countdownSeconds);
        mProtectTimingHandler.start();
    }

    private void stopCountdown() {
        if (mProtectTimingHandler != null) {
            mProtectTimingHandler.stop();
        }
    }

    @SuppressLint("HandlerLeak")
    private class ProtectTimingHandler extends Handler {
        private int mCountdownSeconds;

        ProtectTimingHandler(int countdownSeconds) {
            mCountdownSeconds = countdownSeconds;
        }

        void start() {
            sendEmptyMessage(0);
        }

        void stop() {
            removeCallbacksAndMessages(null);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mCountdownSeconds-- < 0) {
                onTimeCome();
                return;
            }
            sendEmptyMessageDelayed(0, 1000);
        }

        private void onTimeCome() {
            stopSelf();
            Intent intent = new Intent(ACTION_TIME_COME);
            sendBroadcast(intent);
        }


    }


}
