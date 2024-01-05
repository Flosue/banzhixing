package cn.zmy.banzhixing.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;



import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.zmy.banzhixing.R;
import cn.zmy.banzhixing.main.helper.DialHelper;
import cn.zmy.banzhixing.emergency.helper.EmergencyHelper;

/**
 * 即将拨打紧急联系人的页面
 * */
public class WillCallEmergencyActivity extends AppCompatActivity
        implements View.OnClickListener {
    private TextView mTvSeconds;
    private Button mBtnCancel;

    private Handler mTimerHandler;
    private int mSeconds;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_will_call_emergency_activity);

        mTvSeconds = findViewById(R.id.tvSeconds);
        mBtnCancel = findViewById(R.id.btnCancel);
        mBtnCancel.setOnClickListener(this);

        //启动定时器
        mSeconds = 5;
        mTimerHandler = new Handler() {
            @SuppressLint("SetTextI18n")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (mSeconds < 0) {
                    DialHelper.dial(WillCallEmergencyActivity.this, EmergencyHelper.getEmergencyPhoneNumber(getApplicationContext()));
                    finish();
                    return;
                }
                mTvSeconds.setText("" + mSeconds--);
                sendEmptyMessageDelayed(0, 1000);
            }
        };
        mTimerHandler.sendEmptyMessage(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimerHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
