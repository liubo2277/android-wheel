package com.wheeldemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2015/10/12.
 */
public class StartActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn1){
            startActivity(new Intent(this,MainActivity.class));
        }else if(v.getId()==R.id.btn2){
            startActivity(new Intent(this,SecondActivity.class));
        }
    }
}
