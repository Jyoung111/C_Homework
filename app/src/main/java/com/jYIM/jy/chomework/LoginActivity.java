package com.jYIM.jy.chomework;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {

    private boolean flag = false;
    public Button button;
    public EditText idText;
    public EditText passText;
    public TextView textView;
    public String a="",id, pwd,b;
    private Element chk;
    private ArrayList<String> class_name = new ArrayList<String>();
    private ArrayList<String> kj_list = new ArrayList<String>();
    private ArrayList<String> homework_list = new ArrayList<String>();
    private ArrayList<String> homework_name_list = new ArrayList<String>();
    private ArrayList<String> progress_list = new ArrayList<String>();
    private ArrayList<Homework_info> d_day_list = new ArrayList<Homework_info>();
    private Parsing parsing = new Parsing(null,null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idText = (EditText)findViewById(R.id.idText);
        passText = (EditText)findViewById(R.id.passText);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Https 접속을 위한 코드
                SSLConnect ssl = new SSLConnect();
                ssl.postHttps("https://clc.chosun.ac.kr/ilos/lo/login.acl",1000,1000);

                id = idText.getText().toString();
                pwd =  passText.getText().toString();

                parsing = new Parsing(id,pwd);
                parsing.execute();

                CheckTypesTask task = new CheckTypesTask();
                task.execute();
            }
        });
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAffinity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("activity_login",0);
        SharedPreferences.Editor editor = prefs.edit();

        flag = parsing.isFlag();
        class_name = parsing.getClass_name();
        d_day_list = parsing.getD_day_list();
        Set<String> class_name_set = new HashSet<String>(class_name);

        //Gson 방식으로 preference에 넘겨준다.
        //preference 데이터 전달
        Gson gson = new Gson();
        String json = gson.toJson(d_day_list);
        editor.putString("d_day_list", json);
        editor.putStringSet("class_name",class_name_set);
        editor.putString("id",id);
        editor.putString("pwd",pwd);
        editor.putBoolean("login_flag",flag);
        //True는 login false는 list
        editor.putBoolean("login_or_list",false);
        editor.apply();
    }

    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        CustomProgressDialog progressDlg = new CustomProgressDialog(
                LoginActivity.this);

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
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressDlg.dismiss(); // 없애기
            flag = parsing.isFlag();
            class_name = parsing.getClass_name();
            d_day_list = parsing.getD_day_list();
            if(id.matches("") || pwd.matches("") ){
                Toast.makeText(LoginActivity.this,"아이디 또는 비밀번호가 입력되지 않았습니다.",Toast.LENGTH_SHORT).show();
            }else {
                //로그인
                if(flag){
                    Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                    intent.putExtra("class_name",class_name);
                    intent.putParcelableArrayListExtra("d_day_list",d_day_list);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 잘못됬습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            super.onPostExecute(result);
        }
    }
}
