package com.example.jy.chomework;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BootingActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booting);

        SharedPreferences prefs = getSharedPreferences("activity_login",0);
        boolean login_flag = prefs.getBoolean("login_flag",false);

        if(login_flag){
           intent = new Intent(this, ListActivity.class);
        }else{
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
    }
}
