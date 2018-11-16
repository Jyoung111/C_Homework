package com.example.jy.chomework;

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
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;


public class LoginActivity extends AppCompatActivity {

    public Button button;
    public EditText idText;
    public EditText passText;
    public TextView textView;
    public String a,id, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button = (Button) findViewById(R.id.button);
        idText = (EditText)findViewById(R.id.idText);
        passText = (EditText)findViewById(R.id.passText);
        textView = (TextView) findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SSLConnect ssl = new SSLConnect();
                ssl.postHttps("https://clc.chosun.ac.kr/ilos/lo/login.acl",1000,1000);

                new Web_Login().execute();
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
                id = idText.getText().toString();
                pwd =  passText.getText().toString();
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
                //data.put("returnURL", "http://clc.chosun.ac.kr/ilos/main/member/login_form.acl");
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
                Elements elem = doc3.select("div.m-box2");
                //div.m-box2


                a = "";
                for (Element el : elem){
                    a += el.text() +"\n";
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i("TAG",""+a);
            textView.setText(a);
            super.onPostExecute(result);
        }
    }
}
