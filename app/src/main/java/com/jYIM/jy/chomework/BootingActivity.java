package com.jYIM.jy.chomework;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BootingActivity extends AppCompatActivity {

    Intent intent;
    boolean login_flag = false;
    private Parsing parsing = new Parsing(null,null);
    String id="",pwd="";
    private ArrayList<String> class_name = new ArrayList<String>();
    private ArrayList<Homework_info> d_day_list = new ArrayList<Homework_info>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booting);

        SharedPreferences prefs = getSharedPreferences("activity_login",0);
        login_flag = prefs.getBoolean("login_flag",false);
        id = prefs.getString("id","");
        pwd = prefs.getString("pwd","");

        if(login_flag){
            intent = new Intent(BootingActivity.this, ListActivity.class);
        }else{
            intent = new Intent(this, LoginActivity.class);
        }
        startLoading();
    }
    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(login_flag){

                parsing = new Parsing(id, pwd);
                parsing.execute();

                CheckTypesTask task = new CheckTypesTask();
                task.execute();
                }else {
                    finish();
                    startActivity(intent);
                }
            }
        },2000);
    }
    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        CustomProgressDialog progressDlg = new CustomProgressDialog(
                BootingActivity.this);

        @Override
        protected void onPreExecute() {

            progressDlg.getWindow().setBackgroundDrawable(
                    new ColorDrawable(
                            android.graphics.Color.TRANSPARENT));
            progressDlg.show();

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

//            try {
//                for (int i = 0; i < 5; i++) {
//                    Thread.sleep(500);
//                }
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDlg.dismiss(); // 없애기
            SharedPreferences prefs = getSharedPreferences("activity_login",0);
            SharedPreferences.Editor editor = prefs.edit();

            class_name = parsing.getClass_name();
            d_day_list = parsing.getD_day_list();

            Set<String> class_name_set = new HashSet<String>(class_name);

            //Gson 방식으로 preference에 넘겨준다.
            //preference 데이터 전달
            Gson gson = new Gson();
            String json = gson.toJson(d_day_list);
            editor.putString("d_day_list", json);
            editor.putStringSet("class_name",class_name_set);
            editor.apply();

            startActivity(intent);
        }
    }

}
