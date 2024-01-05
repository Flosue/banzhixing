package cn.zmy.banzhixing;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.amap.api.maps2d.MapsInitializer;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.WakeuperListener;
import com.iflytek.cloud.WakeuperResult;

import cn.zmy.banzhixing.emergency.helper.EmergencyHelper;
import cn.zmy.banzhixing.main.TimingProtectService;
import cn.zmy.banzhixing.main.helper.DialHelper;
import cn.zmy.banzhixing.main.helper.TimingProtectHelper;
import cn.zmy.banzhixing.wakeup.Wakeup;

public class TheApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //注册高德地图
        //MapsInitializer.setApiKey("222816a5bd3b329cd7c50acc6e27391a");
        MapsInitializer.setApiKey("e32265118e9e83dff96d7b25cabea8cc");
        //注册语音唤醒
        Wakeup.init(this);
        Wakeup.start(this, new WakeuperListener() {
            @Override
            public void onBeginOfSpeech() {

            }

            @Override
            public void onResult(WakeuperResult wakeuperResult) {
                String phone = EmergencyHelper.getEmergencyPhoneNumber(getApplicationContext());
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), R.string.emergency_phone_not_exist, Toast.LENGTH_SHORT).show();
                } else {
                    DialHelper.dial(getApplicationContext(), phone);
                }
            }

            @Override
            public void onError(SpeechError speechError) {

            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }

            @Override
            public void onVolumeChanged(int i) {

            }
        });

        //注册广播，监听定时守护事件
        IntentFilter filter = new IntentFilter(TimingProtectService.ACTION_TIME_COME);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String phoneNumber = EmergencyHelper.getEmergencyPhoneNumber(getApplicationContext());
                if (!TextUtils.isEmpty(phoneNumber)) {
                    DialHelper.dial(getApplicationContext(), phoneNumber);
                }
            }
        }, filter);

        //读取之前的定时守护数据
        int countdownSeconds = TimingProtectHelper.getCountdownSeconds(getApplicationContext());
        if (countdownSeconds > 0) {
            TimingProtectService.start(this, countdownSeconds);
        }
    }
}
