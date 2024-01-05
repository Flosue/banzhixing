package cn.zmy.banzhixing.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
/*import android.support.v4.app.FragmentActivity;*/
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lkp.numbercodeview.bottomsheet.BottomSheetNumberCodeViewActivity;

import cn.zmy.banzhixing.R;
import cn.zmy.banzhixing.emergency.ModifyEmergencyActivity;
import cn.zmy.banzhixing.loginandregister.loginActivity;
import cn.zmy.banzhixing.security.helper.SecurityCodeHelper;


/**
 * 个人中心页面
 * */
public class PersonalFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_personal_fragment, container, false);
        view.findViewById(R.id.tvModifyEmergency).setOnClickListener(this);
        view.findViewById(R.id.tvSecurityCode).setOnClickListener(this);
        view.findViewById(R.id.bt_main_personal_quit).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvModifyEmergency: {
                Intent intent = new Intent(getActivity(), ModifyEmergencyActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.tvSecurityCode: {
                String title = getString(R.string.security_input_new_code);
                BottomSheetNumberCodeViewActivity.show(this, title);
                break;
            }
            case R.id.bt_main_personal_quit:{
                /*Intent intent = new Intent(this, loginActivity.class);*/
                Intent intent = new Intent();
                intent.setClass(this.getContext(),loginActivity.class);
                startActivity(intent);
                onDestroy();
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
        String code = BottomSheetNumberCodeViewActivity.getResultCode(data);
        if (!TextUtils.isEmpty(code)) {
            SecurityCodeHelper.setSecurityCode(getContext(), code);
            Toast.makeText(getContext(), R.string.security_code_modify_success, Toast.LENGTH_SHORT).show();
        }
    }
}
