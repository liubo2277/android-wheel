package com.wheeldemo;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.library_wheel.ProvincesFragment;
import com.library_wheel.SelectTimeFragment;

import cascade.activity.BaseActivity;

public class MainActivity extends FragmentActivity implements ProvincesFragment.OnSelectListener, SelectTimeFragment.OnSelectTimeListener {
    private TextView resultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        resultTv = (TextView) findViewById(R.id.result_select);
//        initProvinceDatas();
        SelectTimeFragment fragment = SelectTimeFragment.newInstance();
//        ProvincesFragment fragment = ProvincesFragment.newInstance(mProvinceDatas, mCitisDatasMap, mDistrictDatasMap);
//       fragment.setOnSelectListener(this);
        fragment.setOnSelectTimeListener(this);
        fragment.setShowAll(false);
        getSupportFragmentManager().beginTransaction().add(R.id.content, fragment).commitAllowingStateLoss();
    }

    @Override
    public void onSelect(String province, String city, String area) {
        resultTv.setText(province + "#" + city + "#" + area);
    }

    @Override
    public void onSlectTime(int year, int month, int day, int hour, int minute) {
        resultTv.setText(year + "#" + month + "#" + day + "#" + hour + "#" + minute);
    }
}
