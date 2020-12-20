package com.everlastingseo.organicpandit.subcriptionplan;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs; public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {


        Bundle bundle = new Bundle();
        bundle.putString("edttext", "data From Activity");

        switch (position) {
            case 0:
                StandredPlanFragment homeFragment = new StandredPlanFragment();
                homeFragment.setArguments(bundle);
                return homeFragment;
            case 1:
                PremiumPlanFragment sportFragment = new PremiumPlanFragment();
                return sportFragment;

            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}