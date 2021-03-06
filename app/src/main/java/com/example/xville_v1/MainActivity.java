package com.example.xville_v1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.xville_v1.fragment.ClubFragment;
import com.example.xville_v1.fragment.HomeFragment;
import com.example.xville_v1.fragment.MeFragment;
import com.example.xville_v1.fragment.TodayFragment;

public class MainActivity extends AppCompatActivity {

    protected BottomNavigationView mBottomNav;

    //Bundle for receive data
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        mBottomNav.setOnNavigationItemSelectedListener(navListener);

        //GET data from intent
        Bundle extras = getIntent().getExtras();
        String userID = extras.getString("USERID");

        //using Bundle to send data
        bundle = new Bundle();
        bundle.putString("USERID", userID);
        TodayFragment defaultFragment = new TodayFragment();
        defaultFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                defaultFragment).commit();
    }

    //navigation
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.bottom_nav_home:
                            selectedFragment = new HomeFragment();
                            selectedFragment.setArguments(bundle);
                            break;

                        case R.id.bottom_nav_today:
                            selectedFragment = new TodayFragment();
                            selectedFragment.setArguments(bundle);
                            break;

                        case R.id.bottom_nav_club:
                            selectedFragment = new ClubFragment();
                            selectedFragment.setArguments(bundle);
                            break;

                        case R.id.bottom_nav_me:
                            selectedFragment = new MeFragment();
                            selectedFragment.setArguments(bundle);
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    public void initView() {
        mBottomNav = findViewById(R.id.bottom_nav);
    }
}

