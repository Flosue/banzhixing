package cn.zmy.banzhixing.emergency;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import cn.zmy.banzhixing.R;
import cn.zmy.banzhixing.emergency.helper.EmergencyHelper;

/**
 * 修改紧急联系人页面
 * */
public class ModifyEmergencyActivity extends AppCompatActivity {
    private EditText mEditTextName;
    private EditText mEditTextPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.emergency_modify_title);
        setContentView(R.layout.emergency_modify_activity);

        mEditTextName = findViewById(R.id.etName);
        mEditTextPhone = findViewById(R.id.etPhone);

        String name = EmergencyHelper.getEmergencyName(this);
        String phone = EmergencyHelper.getEmergencyPhoneNumber(this);
        mEditTextName.setText(name);
        mEditTextPhone.setText(phone);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_text, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String name = mEditTextName.getText().toString();
        String phone = mEditTextPhone.getText().toString();
        EmergencyHelper.setEmergencyName(this, name);
        EmergencyHelper.setEmergencyPhoneNumber(this, phone);
        finish();
        return true;
    }
}
