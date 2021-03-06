package com.jYIM.jy.chomework;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


public class ListActivity extends AppCompatActivity {

    public TabLayout tabLayout;
    public ViewPager viewPager;
    public Toolbar toolbar;
    private String id,pwd;
    private ArrayList<String> class_name= new ArrayList<String>();
    private ArrayList<Homework_info> d_day_list= new ArrayList<Homework_info>();
    private ArrayList<Homework_info> not_yet_list= new ArrayList<Homework_info>();
    private ArrayList<String> d_day_num = new ArrayList<String>();
    private ArrayList<Homework_info> complete_list= new ArrayList<Homework_info>();
    private TextView toolbar_text;
    Intent beforeIntent;
    long calDate,now;
    Date FirstDate;
    SimpleDateFormat format;
    private boolean login_flag;
    Parsing parsing = new Parsing(null,null);

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAffinity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        SharedPreferences prefs = getSharedPreferences("activity_login",0);
        login_flag = prefs.getBoolean("login_or_list",false);

        //로그인 화면 인텐트에서 getExtra()로 넘겨준 값을 저장
        beforeIntent = getIntent();
        id = prefs.getString("id","");
        pwd = prefs.getString("pwd","");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Homework_info>>(){}.getType();
        String json = prefs.getString("d_day_list", "");
        d_day_list = gson.fromJson(json, type);
        class_name = new ArrayList<String>(prefs.getStringSet("class_name", null));

        if(d_day_list.isEmpty() || class_name.isEmpty()){
            class_name = (ArrayList<String>)beforeIntent.getSerializableExtra("class_name");
            d_day_list = beforeIntent.getParcelableArrayListExtra("d_day_list");
        }

        Collections.sort(d_day_list);

        //기간 남은 과제, 해결한 과제 분류
        for(Homework_info homework_info:d_day_list){
            try {
                now = System.currentTimeMillis();
                format = new SimpleDateFormat("yyyy.MM.dd a K:mm", Locale.KOREA);
                FirstDate = format.parse(homework_info.getD_day());
                calDate = FirstDate.getTime();
                if(calDate - now > 0 && homework_info.getNow_progress().equals("미제출")){
                    long diffday = (calDate - now) / (24 * 60 * 60 * 1000);
                    not_yet_list.add(homework_info);
                    d_day_num.add(String.valueOf(diffday));
                }else{
                    complete_list.add(homework_info);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //Adapter로 list전달
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("class_name",class_name);
        bundle.putParcelableArrayList("not_yet_list",not_yet_list);
        bundle.putParcelableArrayList("complete_list",complete_list);
        bundle.putStringArrayList("d_day_num",d_day_num);

        toolbar_text = (TextView)findViewById(R.id.toolbar_text);
        viewPager = (ViewPager) findViewById(R.id.pager);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), bundle);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()){
                    case 0:
                        toolbar_text.setText("전체 과제");
                        tabLayout.getTabAt(0).setIcon(R.drawable.clicked_calendar);
                        tabLayout.getTabAt(1).setIcon(R.drawable.complete);
                        tabLayout.getTabAt(2).setIcon(R.drawable.list);
                        tabLayout.getTabAt(3).setIcon(R.drawable.settings);
                        break;
                    case 1:
                        toolbar_text.setText("지난 과제");
                        tabLayout.getTabAt(0).setIcon(R.drawable.calendar);
                        tabLayout.getTabAt(1).setIcon(R.drawable.clicked_complete);
                        tabLayout.getTabAt(2).setIcon(R.drawable.list);
                        tabLayout.getTabAt(3).setIcon(R.drawable.settings);
                        break;
                    case 2:
                        toolbar_text.setText("수강 과목");
                        tabLayout.getTabAt(0).setIcon(R.drawable.calendar);
                        tabLayout.getTabAt(1).setIcon(R.drawable.complete);
                        tabLayout.getTabAt(2).setIcon(R.drawable.clicked_list);
                        tabLayout.getTabAt(3).setIcon(R.drawable.settings);
                        break;
                    case 3:
                        toolbar_text.setText("설정");
                        tabLayout.getTabAt(0).setIcon(R.drawable.calendar);
                        tabLayout.getTabAt(1).setIcon(R.drawable.complete);
                        tabLayout.getTabAt(2).setIcon(R.drawable.list);
                        tabLayout.getTabAt(3).setIcon(R.drawable.clicked_settings);
                        break;

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("activity_login",0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("login_or_list",true);
        editor.apply();
    }

}
