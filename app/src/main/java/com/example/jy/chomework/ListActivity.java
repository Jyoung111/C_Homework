package com.example.jy.chomework;

import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;


public class ListActivity extends AppCompatActivity {

    public TabLayout tabLayout;
    public ViewPager viewPager;
    public Toolbar toolbar;
    private String id,pwd;
    private ArrayList<String> class_name;
    private ArrayList<Homework_info> d_day_list;
    private TextView toolbar_text;
    Intent beforeIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //로그인 화면 인텐트에서 getExtra()로 넘겨준 값을 저장
        beforeIntent = getIntent();
        id = beforeIntent.getStringExtra("id");
        pwd = beforeIntent.getStringExtra("pwd");
        class_name = (ArrayList<String>)beforeIntent.getSerializableExtra("class_name");
        d_day_list = beforeIntent.getParcelableArrayListExtra("d_day_list");


        //Adapter로 list전달
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("class_name",class_name);
        bundle.putParcelableArrayList("d_day_list",d_day_list);

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
                        break;
                    case 1:
                        toolbar_text.setText("끝낸 과제");
                        break;
                    case 2:
                        toolbar_text.setText("수강 과목");
                        break;
                    case 3:
                        toolbar_text.setText("설정");
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
}
