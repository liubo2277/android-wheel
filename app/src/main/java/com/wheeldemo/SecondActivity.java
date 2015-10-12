package com.wheeldemo;

import android.os.Bundle;
import android.widget.TextView;

import com.library_wheel.ProvincesFragment;
import com.library_wheel.SelectTimeFragment;

import cascade.activity.BaseActivity;

/**
 * Created by Administrator on 2015/10/12.
 */
public class SecondActivity extends BaseActivity implements ProvincesFragment.OnSelectListener {
    private TextView resultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        resultTv = (TextView) findViewById(R.id.result_select);
        initProvinceDatas();
        ProvincesFragment fragment = ProvincesFragment.newInstance(mProvinceDatas, mCitisDatasMap, mDistrictDatasMap);
        fragment.setOnSelectListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.content, fragment).commitAllowingStateLoss();
    }

    @Override
    public void onSelect(String province, String city, String area) {
        resultTv.setText(province + "#" + city + "#" + area);
    }


}
