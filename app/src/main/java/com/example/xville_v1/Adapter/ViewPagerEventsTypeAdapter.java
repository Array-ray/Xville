package com.example.xville_v1.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerEventsTypeAdapter extends FragmentPagerAdapter {

    private final List<Fragment> eventlstFragment = new ArrayList<>();
    //private final List<String> eventlstTitles = new ArrayList<>();


    public ViewPagerEventsTypeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return eventlstFragment.get(i);
    }

    @Override
    public int getCount() {
        return eventlstFragment.size();
    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return eventlstTitles.get(position);
//    }

    public void AddFragment (Fragment fragment){
        eventlstFragment.add(fragment);
    }
}
