package com.example.jy.chomework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CompleteFragment extends Fragment {
    private ArrayList<Homework_info> complete_list;
    private ArrayList<Complete_ItemData> homework_list = new ArrayList<Complete_ItemData>();
    public ListView list;
    public View view;
    Intent beforeIntent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        complete_list = getArguments().getParcelableArrayList("complete_list");
        //Log.i("TAG",""+d_day_list);

        view = inflater.inflate(R.layout.fragment_home, container, false);

        list = (ListView) view.findViewById(R.id.listView);
        for(Homework_info homework_info:complete_list){
            Complete_ItemData complete_itemData = new Complete_ItemData();
            complete_itemData.strTitle = homework_info.getHomework_name();
            complete_itemData.strClass_name = homework_info.getClass_name();
            complete_itemData.end_date = homework_info.getD_day();
            complete_itemData.now_progress = homework_info.getNow_progress();
            homework_list.add(complete_itemData);
        }

        CompleteCustomAdapter adapter = new CompleteCustomAdapter(homework_list);
        list.setAdapter(adapter);

        //return inflater.inflate(R.layout.fragment_home, container, false);
        return view;

        //return inflater.inflate(R.layout.fragment_complete, container, false);
    }

}
