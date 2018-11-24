package com.example.jy.chomework;

import android.content.Context;
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
        TextView textDate = (TextView)convertView.findViewById(R.id.textDate);
        TextView now_progress = (TextView)convertView.findViewById(R.id.now_progress);

        textTitle.setText(complete_itemData.get(position).strTitle);
        textDate.setText(complete_itemData.get(position).strClass_name);
        now_progress.setText(complete_itemData.get(position).now_progress);

        return convertView;
    }
}
