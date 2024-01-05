package cn.zmy.banzhixing.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.lkp.numbercodeview.bottomsheet.BottomSheetNumberCodeViewActivity;

import cn.zmy.banzhixing.R;
import cn.zmy.banzhixing.main.helper.TimingProtectHelper;
import cn.zmy.banzhixing.security.helper.SecurityCodeHelper;

/**
 * 定时守护页面
 * */
public class TimingProtectFragment extends Fragment
        implements View.OnClickListener {
    private static final String[] TIME_STRING_ARRAY = new String[] {
            "00:10:00",
            "00:20:00",
            "00:30:00"
    };
    private static final int[] TIME_ARRAY = new int[] {
            10 * 60,
            20 * 60,
            30 * 60
    };

    private View mTimingProtectSetRoot;
    private View mTimingProtectedRoot;
    private NumberPicker mProtectTimingPicker;
    private TextView mTvProtectedTime;

    private ProtectTimingHandler mProtectTimingHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_timing_protect_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTimingProtectSetRoot = view.findViewById(R.id.timingProtectSetRoot);
        mTimingProtectedRoot = view.findViewById(R.id.timingProtectedRoot);
        mProtectTimingPicker = view.findViewById(R.id.timePicker);
        mTvProtectedTime = view.findViewById(R.id.tvProtectedTime);

        view.findViewById(R.id.tvConfirmProtectTimingSet).setOnClickListener(this);
        view.findViewById(R.id.ivProtectedStop).setOnClickListener(this);

        mProtectTimingPicker.setDisplayedValues(TIME_STRING_ARRAY);
        mProtectTimingPicker.setMaxValue(TIME_STRING_ARRAY.length - 1);
        mProtectTimingPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        int countdownSeconds = TimingProtectHelper.getCountdownSeconds(getContext());
        if (countdownSeconds <= 0) {
            showSetProtectTimingView();
        } else {
            showTimingProtectedView(countdownSeconds);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopCountdown();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvConfirmProtectTimingSet: {
                int index = mProtectTimingPicker.getValue();
                int countdownSeconds = TIME_ARRAY[index];

                long protectTime = System.currentTimeMillis() + (countdownSeconds * 1000);
                TimingProtectHelper.setProtectTime(getContext(), protectTime);

                TimingProtectService.start(getContext(), countdownSeconds);
                showTimingProtectedView(countdownSeconds);
                break;
            }
            case R.id.ivProtectedStop: {
                //用户点击了退出自动守护按钮
                String title = getString(R.string.security_input_verify_code);
                BottomSheetNumberCodeViewActivity.show(this, title);
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        //检验用户输入的安全码是否正确
        String code = BottomSheetNumberCodeViewActivity.getResultCode(data);
        String actualCode = SecurityCodeHelper.getSecurityCode(getContext());
        if (TextUtils.equals(code, actualCode)) {
            TimingProtectHelper.setProtectTime(getContext(), 0);
            TimingProtectService.stop(getContext());
            showSetProtectTimingView();
            Toast.makeText(getContext(), R.string.main_timing_exited, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), R.string.security_code_verify_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private void showSetProtectTimingView() {
        mProtectTimingPicker.setValue(1);
        mTimingProtectSetRoot.setVisibility(View.VISIBLE);
        mTimingProtectedRoot.setVisibility(View.GONE);
    }

    private void showTimingProtectedView(int countdownSeconds) {
        mTimingProtectSetRoot.setVisibility(View.GONE);
        mTimingProtectedRoot.setVisibility(View.VISIBLE);
        startCountdown(countdownSeconds);
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
            if (mCountdownSeconds < 0) {
                showSetProtectTimingView();
                return;
            }
            String text = DateUtils.formatElapsedTime(mCountdownSeconds--);
            mTvProtectedTime.setText(text);

            sendEmptyMessageDelayed(0, 1000);
        }
    }
}
