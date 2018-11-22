package com.example.jy.chomework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import android.content.Context;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;


public class HomeFragment extends Fragment {
    private ArrayList<Homework_info> d_day_list;
    private ArrayList<String> d_day = new ArrayList<String>();
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

        d_day_list = getArguments().getParcelableArrayList("d_day_list");
        //Log.i("TAG",""+d_day_list);


        view = inflater.inflate(R.layout.fragment_home, container, false);

        list = (ListView) view.findViewById(R.id.listView);
        for(Homework_info homework_info:d_day_list){
            d_day.add(homework_info.getD_day());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                d_day);
        list.setAdapter(adapter);

        //return inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }
}
