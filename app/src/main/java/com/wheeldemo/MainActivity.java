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

public class MainActivity extends FragmentActivity implements  SelectTimeFragment.OnSelectTimeListener {
    private TextView resultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);
        resultTv = (TextView) findViewById(R.id.result_select);
        SelectTimeFragment fragment = SelectTimeFragment.newInstance();
        fragment.setOnSelectTimeListener(this);
        fragment.setShowAll(false);
        getSupportFragmentManager().beginTransaction().add(R.id.content, fragment).commitAllowingStateLoss();
    }


    @Override
    public void onSlectTime(int year, int month, int day, int hour, int minute) {
        resultTv.setText(year + "#" + month + "#" + day + "#" + hour + "#" + minute);
    }
}
