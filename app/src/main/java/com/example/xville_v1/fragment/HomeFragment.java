package com.example.xville_v1.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xville_v1.Adapter.HomeHeaderEventAdapter;
import com.example.xville_v1.Event;
import com.example.xville_v1.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    protected ViewPager mVPagerHeader;
    List<Event> events;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //The views in Home page: A ViewPager (contain a list of posters)
        initView();

    }

    private void initView() {
        //add the item to List<events>
        events = new ArrayList<>();
        events.add(new Event("Hari raya", "This is description",R.mipmap.pos_art_exibition ));
        events.add(new Event("The second", "This is description",R.mipmap.pos_swim_workshop ));

        mVPagerHeader = getView().findViewById(R.id.now_is_showing);
        HomeHeaderEventAdapter adapter = new HomeHeaderEventAdapter(getActivity(), events);
        mVPagerHeader.setAdapter(adapter);
        mVPagerHeader.setPadding(90, 10,90,0);
    }
}
