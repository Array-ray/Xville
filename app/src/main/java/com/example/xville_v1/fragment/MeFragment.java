package com.example.xville_v1.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.xville_v1.authentication.LoginActivity;
import com.example.xville_v1.R;

public class MeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

    private Activity mActivity;
    private AppCompatActivity mAppCompatActivity;

    protected Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.drawer_me_schedule, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //find the activity
        mActivity = getActivity();
        mAppCompatActivity = (AppCompatActivity) mActivity;

        //initialize the toolbar
        toolbar = getView().findViewById(R.id.toolbar);
        mAppCompatActivity.setSupportActionBar(toolbar);

        //inflate the drawer and navigationview to the current view
        drawer = getView().findViewById(R.id.drawer_layout);
        navigationView = getView().findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        initView();

    }

    // can not resolve onBackPressed in fragment
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = getView().findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    private void toLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);

    }

    private void initView() {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_Night_mode) {
            // Handle the camera action
        } else if (id == R.id.nav_Reminder_setting) {

        } else if (id == R.id.nav_Push_notification) {

        } else if (id == R.id.nav_Push_notification) {

        }

        DrawerLayout drawer = getView().findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.activity_main_drawer, menu);
        super.onCreateOptionsMenu(menu,inflater);
//        getMenuInflater().inflate(R.menu.main, menu); this is only used in activity
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





}
