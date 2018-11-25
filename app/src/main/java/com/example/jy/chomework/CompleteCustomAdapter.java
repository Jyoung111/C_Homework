package com.example.jy.chomework;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CompleteCustomAdapter extends BaseAdapter {

    LayoutInflater inflater = null;
    private ArrayList<Complete_ItemData> complete_itemData = new ArrayList<Complete_ItemData>();
    private int aListCount = 0;

    public CompleteCustomAdapter(ArrayList<Complete_ItemData> _cItemData) {
        this.complete_itemData = _cItemData;
        this.aListCount = _cItemData.size();
    }

    @Override
    public int getCount() {
        return aListCount;
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
            convertView = inflater.inflate(R.layout.complete_custom_listview_item,parent,false);
        }
        TextView textTitle = (TextView)convertView.findViewById(R.id.textTitle);
        TextView class_name = (TextView)convertView.findViewById(R.id.class_name);
        TextView end_date = (TextView)convertView.findViewById(R.id.end_date);
        TextView now_progress = (TextView)convertView.findViewById(R.id.now_progress);

        textTitle.setText(complete_itemData.get(position).strTitle);
        class_name.setText(complete_itemData.get(position).strClass_name);
        end_date.setText(complete_itemData.get(position).end_date);
        now_progress.setText(complete_itemData.get(position).now_progress);

        //제출 =  초록색, 미제출 = 주황색, 평가완료 = 회색
        if(complete_itemData.get(position).now_progress.equals("제출")){
            now_progress.setTextColor(Color.parseColor("#6c9f20"));
        }else if(complete_itemData.get(position).now_progress.equals("평가완료")){
            now_progress.setTextColor(Color.parseColor("#969696"));
        }
        else{
            now_progress.setTextColor(Color.parseColor("#ff8c40"));
        }

        return convertView;
    }
}
