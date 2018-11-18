package com.example.jy.chomework;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.Thread.sleep;


public class LoginActivity extends AppCompatActivity {

    private boolean flag = false;
    public Button button,button2;
    public EditText idText;
    public EditText passText;
    public TextView textView;
    public String a,id, pwd,b="http://clc.chosun.ac.kr";
    private ArrayList<String> class_name = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        idText = (EditText)findViewById(R.id.idText);
        passText = (EditText)findViewById(R.id.passText);
        textView = (TextView) findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SSLConnect ssl = new SSLConnect();
                ssl.postHttps("https://clc.chosun.ac.kr/ilos/lo/login.acl",1000,1000);
                id = idText.getText().toString();
                pwd =  passText.getText().toString();

                new Web_Login().execute();

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
    }

    public class Web_Login extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }


        @Override
        protected Void doInBackground(Void... params) {

            try {

                String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36";

                // 로그인 페이지 접속
                Connection.Response loginPageResponse = Jsoup.connect("http://clc.chosun.ac.kr/ilos/main/member/login_form.acl")
                        .timeout(5000)
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                        .header("Referer", "http://clc.chosun.ac.kr/ilos/main/main_form.acl")
                        .header("Upgrade-Insecure-Requests", "1")
                        .method(Connection.Method.GET)
                        .execute();

                // 로그인 페이지에서 얻은 쿠키
                Map<String, String> loginTryCookie = loginPageResponse.cookies();

                // 전송할 폼 데이터
                Map<String, String> data = new HashMap<>();
                data.put("class", "A");
                data.put("usr_id", id);
                data.put("usr_pwd", pwd);

                // 로그인(POST)
                Connection.Response response = Jsoup.connect("https://clc.chosun.ac.kr/ilos/lo/login.acl")
                        .timeout(5000)
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                        .cookies(loginTryCookie)
                        .header("Referer", "http://clc.chosun.ac.kr/ilos/main/main_form.acl")
                        .header("Upgrade-Insecure-Requests", "1")
                        .userAgent(userAgent)
                        .data(data)
                        .method(Connection.Method.POST)
                        .execute();

                // 로그인 성공 후 얻은 쿠키.
                // 쿠키 중 TSESSION 이라는 값을 확인할 수 있다.
                Map<String, String> loginCookie = response.cookies();

                Connection.Response response1 = Jsoup.connect("http://clc.chosun.ac.kr/ilos/main/main_form.acl")
                        .cookies(loginTryCookie)
                        .execute();


                Document doc3 = response1.parse();
                Elements elem = doc3.select("div.m-box2 > ol > li > em.sub_open");

                Document doc4 = Jsoup.connect("http://clc.chosun.ac.kr/ilos/st/course/eclass_room2.acl")
                        .cookies(loginTryCookie)
                        .data("KJKEY","01201834280401", "returnURI","/ilos/st/course/submain_form.acl","encoding","utf-8")
                        .ignoreContentType(true)
                        .post();

                //로그인 체크
                Element chk = doc4.select("script").first();
                if(chk == null){
                    flag = true;
                    try{
                        JSONObject jsonObject = new JSONObject(doc4.text());
                        b += (String) jsonObject.get("returnURL");
                    }catch (JSONException e) {
                        Log.e("MYAPP", "unexpected JSON exception", e);
                    }
                    Connection.Response response5 = Jsoup.connect(b)
                            .cookies(loginTryCookie)
                            .execute();

                    Document doc5 = response5.parse();
                    Elements elem3 = doc5.select("div.submain-noticebox");


                    a = "";
                    for (Element el : elem){
                        a += el.text() +"\n";
                        class_name.add(el.text()+"\n");
                    }

                    for (Element el : elem3){
                        a += el.text() +"\n";
                    }
                }
                else{
                    return null;
                }
            } catch (IOException e) {
                Log.e("MYAPP", "error!", e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if(id.matches("") || pwd.matches("") ){
                Toast.makeText(LoginActivity.this,"아이디 또는 비밀번호가 입력되지 않았습니다.",Toast.LENGTH_SHORT).show();
            }else {
                //로그인
                if(flag){
                    Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                    intent.putExtra("id",id);
                    intent.putExtra("pwd",pwd);
                    intent.putExtra("class_name",class_name);
                    startActivity(intent);
                    flag = false;
                }else{
                    Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 잘못됬습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            Log.i("TAG",""+a);
            textView.setText(a);
            super.onPostExecute(result);
        }
    }
}
