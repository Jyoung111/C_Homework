package com.jYIM.jy.chomework;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Now_classFragment extends Fragment {

    private ArrayList<String> class_name;
    public ListView list;
    public View view;
    Intent beforeIntent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        class_name = getArguments().getStringArrayList("class_name");


        view = inflater.inflate(R.layout.fragment_now_class, container, false);

        list = (ListView) view.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                class_name);
        list.setAdapter(adapter);

        //return inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

}