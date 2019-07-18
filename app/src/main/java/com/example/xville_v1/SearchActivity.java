package com.example.xville_v1;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.xville_v1.Adapter.SearchAdapter;
import com.example.xville_v1.Model.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private String searchContent;
    private TextView textViewSeachContent;
    private RecyclerView mSearchListRecycleView;
    private List<Event> eventList;

    private DatabaseReference eventDatabaseRef;
    private SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSearchRequireInfo();

        initView();

        //get the transferred data
        Bundle bundle = this.getIntent().getExtras();

        searchContent = bundle.getString("searchContent");

        readItems(searchContent);
    }
    private void readItems(final String searchContent){

        Query query = eventDatabaseRef;

        eventList =new ArrayList<>();
        query.addValueEventListener(new ValueEventListener() {
            // itemsDatabaseRef.child(searchContent).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                eventList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Event event= snapshot.getValue(Event.class);
                        if(event.getTitle().equals(searchContent))
                        {eventList.add(event);}
                    }
                    adapter = new SearchAdapter(SearchActivity.this,eventList);
                    mSearchListRecycleView.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void getSearchRequireInfo() {

        Bundle bundle = this.getIntent().getExtras();

        searchContent = bundle.getString("searchContent");
    }

    private void initView() {
        //display header
      //  setUpToolbar();
        //display title in header
        setTitle("Search Result");

        mSearchListRecycleView = findViewById(R.id.search_event_recyclerviewer);

//        mMessageRecyclerView = findViewById(R.id.message);
        mSearchListRecycleView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        mSearchListRecycleView.setLayoutManager(linearLayoutManager);

        eventDatabaseRef = FirebaseDatabase.getInstance().getReference("Events");
    }


}


