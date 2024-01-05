package cn.zmy.banzhixing.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import cn.zmy.banzhixing.R;
import cn.zmy.banzhixing.main.helper.CameraLightHelper;
import cn.zmy.banzhixing.main.helper.RingHelper;

/**
 * 一键求助功能页面
 * */
public class OneKeyHelpFragment extends Fragment implements View.OnClickListener {
    private CheckedTextView mCTVLight;
    private CheckedTextView mCTVRing;
    private CheckedTextView mCTVDial;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_one_key_help_fragment, container, false);
        mCTVLight = view.findViewById(R.id.tvLight);
        mCTVRing = view.findViewById(R.id.tvRing);
        mCTVDial = view.findViewById(R.id.tvDial);

        mCTVLight.setOnClickListener(this);
        mCTVRing.setOnClickListener(this);
        mCTVDial.setOnClickListener(this);
        view.findViewById(R.id.btnOneKeyHelp).setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CameraLightHelper.release();
        RingHelper.release();
    }

    @Override
    public void onClick(View v) {
        if (v == mCTVLight) {
            mCTVLight.toggle();
            CameraLightHelper.enableLight(getContext(), mCTVLight.isChecked());
        } else if (v == mCTVRing) {
            mCTVRing.toggle();
            RingHelper.enableRing(getContext(), mCTVRing.isChecked());
        } else if (v == mCTVDial) {
            launchWillCallEmergency();
        } else if (v.getId() == R.id.btnOneKeyHelp) {
            mCTVLight.setChecked(true);
            mCTVRing.setChecked(true);
            CameraLightHelper.enableLight(getContext(), true);
            RingHelper.enableRing(getContext(), true);
            launchWillCallEmergency();
        }
    }

    private void launchWillCallEmergency() {
        Intent intent = new Intent(getActivity(), WillCallEmergencyActivity.class);
        startActivity(intent);
    }
}
