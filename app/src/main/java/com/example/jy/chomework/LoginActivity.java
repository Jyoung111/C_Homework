package com.example.jy.chomework;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idText = (EditText)findViewById(R.id.idText);
        passText = (EditText)findViewById(R.id.passText);

    }

    @Override
    protected void onStart() {
        super.onStart();
        button = (Button) findViewById(R.id.button);
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

                //메인 홈페이지에서 각 과목마다 url을 획득한다.
                Document doc3 = response1.parse();
                Elements elem = doc3.select("div.m-box2 > ol > li > em.sub_open");
                for (Element el : elem) {
                    String id_elem = el.attr("kj");
                    kj_list.add(id_elem);
                    class_name.add(el.text());
                }

                //모든 과제 링크 수집
                int index1=0,index2 = 0;
                for (String kj: kj_list) {
                    homework_list.clear();
                    Document doc4 = Jsoup.connect("http://clc.chosun.ac.kr/ilos/st/course/eclass_room2.acl")
                            .cookies(loginTryCookie)
                            .data("KJKEY", kj, "returnURI", "/ilos/st/course/report_list_form.acl", "encoding", "utf-8")
                            .ignoreContentType(true)
                            .post();

                    //로그인 체크
                    chk = doc4.select("script").first();
                    if (chk == null) {
                        flag = true;
                        //로그인 json형식으로 return
                        try {
                            JSONObject jsonObject = new JSONObject(doc4.text());
                            b = "http://clc.chosun.ac.kr";
                            b += (String) jsonObject.get("returnURL");
                        } catch (JSONException e) {
                            Log.e("MYAPP", "unexpected JSON exception", e);
                        }

                        Connection.Response response5 = Jsoup.connect(b)
                                .cookies(loginTryCookie)
                                .execute();

                        Document doc5 = response5.parse();
                        Elements elem3 = doc5.select("table>tbody>tr>td>a.site-link");
                        Elements elem4 = doc5.select("table>tbody>tr>td:eq(6)");

                        for (Element el : elem3) {
                            String homework_url = "http://clc.chosun.ac.kr" + el.attr("href");
                            homework_list.add(homework_url);
                        }
                        for (Element el : elem4) {
                            progress_list.add(el.text());
                        }


                        for(String url:homework_list) {
                            Connection.Response response6 = Jsoup.connect(url)
                                    .cookies(loginTryCookie)
                                    //.data("RT_SEQ","1242889")
                                    .method(Connection.Method.GET)
                                    .execute();

                            Document doc6 = response6.parse();
                            Elements elem6 = doc6.select("table.bbsview>tbody>tr:eq(3)>td");
                            Elements elem7 = doc6.select("table.bbsview>tbody>tr:eq(0)>td");

                            for (int i = 0;i<elem6.size();i++) {
                                d_day_list.add(new Homework_info(elem7.get(i).text(), elem6.get(i).text(), class_name.get(index1), progress_list.get(index2)));
                                a += elem6.text() + "\n";
                            }
                            index2++;
                       }

                index1++;

                } else {
                    return null;
                }

            }

            } catch(IOException e){
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
                    intent.putParcelableArrayListExtra("d_day_list",d_day_list);
                    startActivity(intent);
                    flag = false;
                }else{
                    Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 잘못됬습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            super.onPostExecute(result);
        }
    }
}
