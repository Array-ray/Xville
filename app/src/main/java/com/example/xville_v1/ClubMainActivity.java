package com.example.xville_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.xville_v1.Adapter.ViewPagerEventsTypeAdapter;
import com.example.xville_v1.fragment.ClubGridEventFragment;
import com.example.xville_v1.fragment.ClubListEventFragment;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class ClubMainActivity extends AppCompatActivity {

    //Tab layout and view pager
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerEventsTypeAdapter adapter;

    //ImageView
    private ImageView posterbackground;

    //Bundle for receive data
    private Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_main);

        //GET data from intent
        bundle = getIntent().getExtras();

        //Tab layout
        tabLayout = findViewById(R.id.events_tab);
        viewPager = findViewById(R.id.club_event_content);

        //initialize adapter and add fragments to adapter
        adapter = new ViewPagerEventsTypeAdapter(getSupportFragmentManager());

        Fragment clubFirstFragment = new ClubGridEventFragment();
        clubFirstFragment.setArguments(bundle);

        adapter.AddFragment(clubFirstFragment);
        adapter.AddFragment(new ClubListEventFragment());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //set icon for tab
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_tab_grid);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_tab_list);

        //Floating action button
        FloatingActionButton fab = findViewById(R.id.create_event);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClubMainActivity.this, PostEventActivity.class);
                startActivity(i);
                finish();
            }
        });

        posterbackground = findViewById(R.id.iv);
        Glide.with(this).load(R.mipmap.logo).apply(bitmapTransform(new BlurTransformation(14, 1))).into(posterbackground);
    }

}
