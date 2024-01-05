package com.lkp.numbercodeview.bottomsheet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.lkp.numbercodeview.BaseNumberCodeView;
import com.lkp.numbercodeview.R;


/**
 * Created by linkaipeng on 16/8/2.
 */
public class BottomSheetNumberCodeViewActivity extends AppCompatActivity
    implements BaseNumberCodeView.OnInputNumberCodeCallback,
        BottomSheetNumberCodeView.OnHideBottomLayoutListener {

    public static final int REQUEST_CODE = 1001;
    public static final String KEY_DATA_NUMBER = "KeyDataNumber";
    private static final String KEY_DATA_IS_PASSWORD = "KeyDataIsPassword";
    private static final String KEY_DATA_TITLE = "keyTitle";

    public static void show(Activity activity, String title){
        Intent intent = new Intent(activity, BottomSheetNumberCodeViewActivity.class);
        intent.putExtra(KEY_DATA_IS_PASSWORD, true);
        intent.putExtra(KEY_DATA_TITLE, title);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    public static void show(Fragment fragment, String title){
        Intent intent = new Intent(fragment.getActivity(), BottomSheetNumberCodeViewActivity.class);
        intent.putExtra(KEY_DATA_IS_PASSWORD, true);
        intent.putExtra(KEY_DATA_TITLE, title);
        fragment.startActivityForResult(intent, REQUEST_CODE);
    }

    public static String getResultCode(Intent intent) {
        if (intent == null) {
            return null;
        }
        return intent.getStringExtra(KEY_DATA_NUMBER);
    }

    private BottomSheetNumberCodeView mNumberCodeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.push_bottom_to_top, 0);
        setContentView(R.layout.activity_bottom_sheet_number_code_view);
        initView();
    }

    private void initView(){
        boolean isPassword = getIntent().getBooleanExtra(KEY_DATA_IS_PASSWORD, true);
        String title = getIntent().getStringExtra(KEY_DATA_TITLE);
        mNumberCodeView = findViewById(R.id.bottom_sheet_number_code_view);
        mNumberCodeView.setNumberCodeCallback(this);
        mNumberCodeView.setOnHideBottomLayoutListener(this);
        mNumberCodeView.setIsPassword(isPassword);
        mNumberCodeView.showNumberCodeLayout();
        mNumberCodeView.setTitle(title);
    }

    @Override
    public void onResult(String code) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_DATA_NUMBER, code);
        setResult(RESULT_OK, resultIntent);
    }

    @Override
    public void onHide() {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (mNumberCodeView.isNumberCodeLayoutShowing()) {
            mNumberCodeView.hideNumberCodeLayout();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.push_top_to_bottom);
    }
}
