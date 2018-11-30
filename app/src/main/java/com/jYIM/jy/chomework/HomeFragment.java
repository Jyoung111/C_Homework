package com.jYIM.jy.chomework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private ArrayList<Homework_info> not_yet_list;
    private ArrayList<String> d_day_num = new ArrayList<String>();
    private ArrayList<Home_ItemData> homework_list = new ArrayList<Home_ItemData>();
    public ListView list;
    public View view;
    Intent beforeIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        not_yet_list = getArguments().getParcelableArrayList("not_yet_list");
        d_day_num = getArguments().getStringArrayList("d_day_num");
        //Log.i("TAG",""+d_day_list);

        view = inflater.inflate(R.layout.fragment_home, container, false);

        list = (ListView) view.findViewById(R.id.listView);

        int index = 0;
        for(Homework_info homework_info:not_yet_list){
            Home_ItemData home_Item = new Home_ItemData();
            home_Item.d_day = "D - " + d_day_num.get(index);
            home_Item.strTitle = homework_info.homework_name;
            home_Item.strClass_name = homework_info.class_name;
            home_Item.now_progress = homework_info.now_progress;
            homework_list.add(home_Item);
            index++;
        }

        HomeCustomAdapter adapter = new HomeCustomAdapter(homework_list);
        list.setAdapter(adapter);

        //return inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

}
