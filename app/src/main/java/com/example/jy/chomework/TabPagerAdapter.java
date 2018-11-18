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

    public TabPagerAdapter(FragmentManager fm, int tabCount, Bundle bundle) {
        super(fm);
        this.tabCount = tabCount;
        this.bundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {
        class_name = bundle.getStringArrayList("class_name");
        Log.i("TAG",""+class_name);

        // Returning the current tabs
        switch (position) {
            case 0:
                HomeFragment tabFragment1 = new HomeFragment();


                return tabFragment1;
            case 1:
                CompleteFragment tabFragment2 = new CompleteFragment();
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
