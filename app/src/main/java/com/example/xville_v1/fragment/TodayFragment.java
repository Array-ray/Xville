package com.example.xville_v1.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xville_v1.Adapter.TodayEventsPagerAdapter;
import com.example.xville_v1.Model.EventToday;
import com.example.xville_v1.R;

import java.util.ArrayList;
import java.util.List;

public class TodayFragment extends Fragment {

    private ViewPager mPager;
    private TodayEventsPagerAdapter mAdapter;
    private List<EventToday> todayEvents;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_today, container, false);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        todayEvents = new ArrayList<>();
        todayEvents.add(new EventToday("Hari raya", "This is description",R.mipmap.pos_art_exibition ));
        todayEvents.add(new EventToday("The second", "This is description",R.mipmap.pos_swim_workshop ));
        mAdapter = new TodayEventsPagerAdapter(todayEvents, getContext());

        mPager = getView().findViewById(R.id.today_viewpager);
        mPager.setAdapter(mAdapter);
        mPager.setPadding(0, 16,0,0);

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//    }
}


