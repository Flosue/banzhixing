package cn.zmy.banzhixing.main;

import android.location.Location;
import android.os.Bundle;
/*
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
*/
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.text.TextUtils;
import android.view.MenuItem;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;


import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.MyLocationStyle;

import cn.zmy.banzhixing.R;
import cn.zmy.banzhixing.main.helper.AMapHelper;
import cn.zmy.banzhixing.main.helper.ShareHelper;

/**
 * 主页面
 * */
public class MainGPS extends AppCompatActivity {
    private MapView mMapView;
    private BottomNavigationView mNavigationView;

    private OneKeyHelpFragment mOneKeyHelpFragment;
    private TimingProtectFragment mTimingProtectFragment;
    private PersonalFragment mPersonalFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapView = findViewById(R.id.mapView);
        mNavigationView = findViewById(R.id.bottomNavigationTab);


        mMapView.onCreate(savedInstanceState);
        initMapView();

        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return internalNaviTabSelected(item.getItemId());
            }
        });
    }

    private boolean internalNaviTabSelected(int tabId) {
        switch (tabId) {
            case R.id.main_menu_gps: {
                removeAllFragments();
                break;
            }
            case R.id.main_menu_one_key_help: {
                showOneKeyHelpFragment();
                break;
            }
            case R.id.main_menu_timing_protect: {
                showTimingProtectFragment();
                break;
            }
            case R.id.main_menu_location_share: {
                Location location = mMapView.getMap().getMyLocation();
                String myLocationWebUrl = AMapHelper.getMyLocationWebUrl(location);
                if (!TextUtils.isEmpty(myLocationWebUrl)) {
                    ShareHelper.shareText(MainGPS.this, myLocationWebUrl);
                }
                return false;
            }
            case R.id.main_menu_personal: {
                showPersonalFragment();
                break;
            }
        }
        return true;
    }

    private void initMapView() {
        AMap map = mMapView.getMap();

        map.moveCamera(CameraUpdateFactory.zoomTo(20F));

        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true);

        map.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        map.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mNavigationView != null) {
            internalNaviTabSelected(mNavigationView.getSelectedItemId());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    private void removeAllFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mOneKeyHelpFragment != null) {
            transaction.remove(mOneKeyHelpFragment);
        }
        if (mTimingProtectFragment != null) {
            transaction.remove(mTimingProtectFragment);
        }
        if (mPersonalFragment != null) {
            transaction.remove(mPersonalFragment);
        }
        transaction.commit();
    }

    private void showOneKeyHelpFragment() {
        removeAllFragments();
        if (mOneKeyHelpFragment == null) {
            mOneKeyHelpFragment = new OneKeyHelpFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, mOneKeyHelpFragment)
                .commit();
    }

    private void showTimingProtectFragment() {
        removeAllFragments();
        if (mTimingProtectFragment == null) {
            mTimingProtectFragment = new TimingProtectFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, mTimingProtectFragment)
                .commit();
    }

    private void showPersonalFragment() {
        removeAllFragments();
        if (mPersonalFragment == null) {
            mPersonalFragment = new PersonalFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, mPersonalFragment)
                .commit();
    }
}
