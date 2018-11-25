package com.example.jy.chomework;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeCustomAdapter extends BaseAdapter {

    LayoutInflater inflater = null;
    private ArrayList<Home_ItemData> home_data = null;
    private int hListCnt = 0;


    public HomeCustomAdapter(ArrayList<Home_ItemData> _home_Data) {
        this.home_data = _home_Data;
        this.hListCnt = home_data.size();
    }

    @Override
    public int getCount() {
        return hListCnt;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            final Context context = parent.getContext();
            if(inflater == null){
                inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.home_custom_listview_item,parent,false);
        }

        TextView textTitle = (TextView)convertView.findViewById(R.id.textTitle);
        TextView class_name = (TextView)convertView.findViewById(R.id.class_name);
        TextView dday = (TextView)convertView.findViewById(R.id.ddayView);
        TextView now_progress = (TextView)convertView.findViewById(R.id.now_progress);

        textTitle.setText(home_data.get(position).strTitle);
        class_name.setText(home_data.get(position).strClass_name);
        dday.setText(home_data.get(position).d_day);
        now_progress.setText(home_data.get(position).now_progress);
        //제출 =  초록색, 미제출 = 주황색, 평가완료 = 회색
        if(home_data.get(position).now_progress.equals("제출")){
            now_progress.setTextColor(Color.parseColor("#6c9f20"));
        }else if(home_data.get(position).now_progress.equals("평가완료")){
            now_progress.setTextColor(Color.parseColor("#969696"));
        }
        else{
            now_progress.setTextColor(Color.parseColor("#ff8c40"));
        }

        return convertView;
    }
}
