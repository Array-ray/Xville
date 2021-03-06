package com.example.xville_v1.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xville_v1.Adapter.ScheduleVertiHolder;
import com.example.xville_v1.Model.Event;
import com.example.xville_v1.R;
import com.example.xville_v1.authentication.GuideRoleActivity;
import com.example.xville_v1.authentication.LoginActivity;
import com.example.xville_v1.util.DeleteDialog;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

    //Activity
    private Activity mActivity;
    private AppCompatActivity mAppCompatActivity;

    //Get bundle data
    private String userID;
    private String userName;

    //View
    protected Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private RecyclerView mRecycleSchedule;
    private TextView mName;
    private TextView navHeaderName;

    //Firebase
    //database reference (point to the specific child)
    private DatabaseReference mScheduleRef;
    private DatabaseReference mReadScheRef; // for reading the Schedule child in firebase

    //query
    private Query mQueryschedule;

    //Title list stored in Schdule in firebase
    private List<String> scheduleList;

    //Firebase user
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    //delete dailog
    private DeleteDialog deleteDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.drawer_me_schedule, container, false);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //userID = getArguments().getString("USERID");

        //get Preference for user
        SharedPreferences preferences=this.getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);

        //2、取出数据 用户id和用户名
        userName = preferences.getString("USERNAME",null);
        userID = preferences.getString("USERID",null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //find the activity
        mActivity = getActivity();
        mAppCompatActivity = (AppCompatActivity) mActivity;

        //intialization the view
        initView();

        //initialize the toolbar
        toolbar = getView().findViewById(R.id.toolbar);
        mAppCompatActivity.setSupportActionBar(toolbar);
        toolbar.setTitle("Schedule");


        //inflate the drawer and navigationview to the current view
        drawer = getView().findViewById(R.id.drawer_layout);
        navigationView = getView().findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        //show the username
        mName.setText("Hi " + userName);

        //Get a list of event title
//        getScheduleList();

        //Populate the event schedule view
        //Firebase database
        mScheduleRef = FirebaseDatabase.getInstance().getReference().child("Schedule");
        if(mScheduleRef.child(userID) != null){
            populateRecyclerEvent();
        }
    }

    private void populateRecyclerEvent() {

        //Query, filter the event held today, filter time
        mQueryschedule = FirebaseDatabase.getInstance()
                .getReference()
                .child("Schedule")
                .child(userID)
                .limitToLast(50);

        //New an option, bind the query and data model
        FirebaseRecyclerOptions<Event> options =
                new FirebaseRecyclerOptions.Builder<Event>()
                        .setQuery(mQueryschedule, Event.class)
                        .build();


        final FirebaseRecyclerAdapter<Event, ScheduleVertiHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Event, ScheduleVertiHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull ScheduleVertiHolder holder, int position, @NonNull Event model) {
                        //if(scheduleList.contains(model.getTitle())){
                            holder.FillinHolder(getContext(),model.getImg(),model.getTitle(),
                                    model.getAbout(),model.getTime(),model.getLocation());
                        //}
                    }

                    @NonNull
                    @Override
                    public ScheduleVertiHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.item_listschedule, viewGroup, false);
                        return new ScheduleVertiHolder(view);
                    }
                };

        //Set adapter
        firebaseRecyclerAdapter.startListening();
        mRecycleSchedule.setAdapter(firebaseRecyclerAdapter);

        /**
         * swipe and delete
         */
        ItemTouchHelper.SimpleCallback simpleItemCallback =
                new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {


                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                mRecycleSchedule.getLayoutParams());

                        deleteDialog = new DeleteDialog(getContext(),params);

                        deleteDialog.setTitle("Careful!!!");
                        deleteDialog.setMessage("Are you going to remove");
                        deleteDialog.setYesOnclickListener("YES", new DeleteDialog.onYesOnclickListener() {
                            @Override
                            public void onYesClick() {

                                Toast.makeText(getContext(), "Successufully removed ", Toast.LENGTH_SHORT).show();
                                //Remove swiped item from list and notify the RecyclerView
                                final int position = viewHolder.getAdapterPosition();

                                firebaseRecyclerAdapter.getRef(position).removeValue();

                                deleteDialog.dismiss();

                            }
                        });
                        deleteDialog.setNoOnclickListener("NO", new DeleteDialog.onNoOnclickListener() {
                            @Override
                            public void onNoClick() {

                                deleteDialog.dismiss();
                            }
                        });

                        deleteDialog.show();

                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemCallback);
        itemTouchHelper.attachToRecyclerView(mRecycleSchedule);


    }

    private void getScheduleList() {
        scheduleList = new ArrayList<>();
        mReadScheRef = FirebaseDatabase.getInstance().getReference().child("Schedule");
        if(mReadScheRef.child(userID) != null){
            mReadScheRef.child(userID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Clear all the items in array list
                    scheduleList.clear();
                    //User for loop to add the children of one particular user
                    for (DataSnapshot titleSnapshot: dataSnapshot.getChildren()){
                        scheduleList.add(titleSnapshot.getValue(String.class));
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Datasnapshop error", Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Toast.makeText(getActivity(), "You haven't add any events to your schedule", Toast.LENGTH_LONG).show();
        }
    }

    private void toLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);

    }

    private void initView() {
        mRecycleSchedule = getView().findViewById(R.id.schedule_recycler);
        mRecycleSchedule.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Textview username
        mName = getView().findViewById(R.id.me_name);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_Night_mode) {
            // Handle the camera action
        } else if (id == R.id.nav_Reminder_setting) {

        } else if (id == R.id.nav_Push_notification) {

        } else if (id == R.id.switch_signOut) {
            SignOutUser();
        }

        DrawerLayout drawer = getView().findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void SignOutUser() {
        if(currentUser != null){
            mAuth.signOut();

            //Remove value from database
            DatabaseReference removeUserRef = FirebaseDatabase.getInstance().getReference();
            if (removeUserRef.child("Users").child(userID) != null){
                removeUserRef.child("Users").child(userID).removeValue();
                Toast.makeText(getContext(), "User sign out successfully", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getActivity().getApplicationContext(), GuideRoleActivity.class);
                startActivity(i);
            }else{
                Toast.makeText(getContext(), "No user exists", Toast.LENGTH_LONG).show();
            }

        }
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
