package com.example.jy.chomework;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount;
    private Bundle bundle;
    private ArrayList<String> class_name ;
    private ArrayList<String> d_day_num ;
    private ArrayList<Homework_info> not_yet_list;
    private ArrayList<Homework_info> complete_list;
    private ArrayList<Homework_info> d_day_list;

    public TabPagerAdapter(FragmentManager fm, int tabCount, Bundle bundle) {
        super(fm);
        this.tabCount = tabCount;
        this.bundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {
        class_name = bundle.getStringArrayList("class_name");
        not_yet_list = bundle.getParcelableArrayList("not_yet_list");
        complete_list = bundle.getParcelableArrayList("complete_list");
        d_day_num = bundle.getStringArrayList("d_day_num");
        d_day_list = bundle.getParcelableArrayList("d_day_list");

        // Returning the current tabs
        switch (position) {
            case 0:
                HomeFragment tabFragment1 = new HomeFragment();
                bundle.putParcelableArrayList("not_yet_list",not_yet_list);
                bundle.putStringArrayList("d_day_num",d_day_num);
                tabFragment1.setArguments(bundle);
                return tabFragment1;
            case 1:
                CompleteFragment tabFragment2 = new CompleteFragment();
                bundle.putParcelableArrayList("complete_list",complete_list);
                tabFragment2.setArguments(bundle);
                return tabFragment2;
            case 2:
                Now_classFragment tabFragment3 = new Now_classFragment();
                bundle.putStringArrayList("class_name",class_name);
                tabFragment3.setArguments(bundle);
                return tabFragment3;
            case 3:
                SettingsFragment tabFragment4 = new SettingsFragment();
                return tabFragment4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

}
