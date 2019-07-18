package com.example.xville_v1.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.xville_v1.Adapter.EventsVertiHolder;
import com.example.xville_v1.EventDetailActivity;
import com.example.xville_v1.Model.Event;
import com.example.xville_v1.R;
import com.example.xville_v1.SearchActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener{

    //Bundle and intent, from login activity
    private String userID;
    private String userName;

    //Views
    protected Toolbar toolbar;
    private RecyclerView mRecycleHorizontal;
    private RecyclerView mRecycleVertical;

    //Category button

    //Firebase
    //database reference (point to the specific child)
    private DatabaseReference mEventHori;
    private DatabaseReference mEventVerti;
    //query
    private Query mQueryhori;
    private Query mQueryVerti;

    //Activity
    private Activity mActivity;
    private AppCompatActivity mAppCompatActivity;

    private ImageButton ImageButtonHomeSearchIcon;
    private EditText mSearchEditText;

    List<Event> events;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userID = getArguments().getString("USERID");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //find the activity
        mActivity = getActivity();
        mAppCompatActivity = (AppCompatActivity) mActivity;

        //initialize the toolbar
        toolbar = getView().findViewById(R.id.toolbar);
        mAppCompatActivity.setSupportActionBar(toolbar);//find the activity
        mActivity = getActivity();
        mAppCompatActivity = (AppCompatActivity) mActivity;

        //initialize the toolbar
        toolbar = getView().findViewById(R.id.toolbar);
        mAppCompatActivity.setSupportActionBar(toolbar);


        //View
        mRecycleHorizontal = getView().findViewById(R.id.recycle_event_hori);
        mRecycleVertical = getView().findViewById(R.id.recycle_event_verti);
        ImageButtonHomeSearchIcon = getView().findViewById(R.id.home_search_icon);

        mSearchEditText =getView().findViewById(R.id.home_search_content);

        ImageButtonHomeSearchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchContent = mSearchEditText.getText().toString();

                Intent i = new Intent(getActivity(), SearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("searchContent", searchContent);
                i.putExtras(bundle);

                startActivity(i);
            }
        });

        //LayoutManager (for layout purpose) and adapter
        //Horizontal
        mRecycleHorizontal.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        //Vertical
        mRecycleVertical.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void onStart() {
        super.onStart();
        toolbar.setTitle("Browse");

        //Firebase database
        mEventHori = FirebaseDatabase.getInstance().getReference().child("Events");
        mEventVerti = FirebaseDatabase.getInstance().getReference().child("Events");

        //Query, filter the event held today, filter time
        mQueryhori = FirebaseDatabase.getInstance()
                .getReference()
                .child("Events")
                .limitToLast(50);

        mQueryVerti = FirebaseDatabase.getInstance()
                .getReference()
                .child("Events")
                .limitToLast(50);

        //populate horizontal recyclerview
        populateHorizontalEvent(mQueryhori);
        populateVerticalEvent(mQueryVerti);
    }

    private void populateHorizontalEvent(Query mQueryhori) {
        //New an option, bind the query and data model
        FirebaseRecyclerOptions<Event> options =
                new FirebaseRecyclerOptions.Builder<Event>()
                        .setQuery(mQueryhori, Event.class)
                        .build();

        //New an adapter. Override two methods
        FirebaseRecyclerAdapter<Event, HomeFragment.EventsHoriHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Event, HomeFragment.EventsHoriHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull HomeFragment.EventsHoriHolder holder, int position, @NonNull Event model) {
                        holder.FillinHolder(getContext(), model.getImg());
                        final String tmp = model.getTitle();
                        holder.getEventsHoriCard().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(getActivity().getApplicationContext(), EventDetailActivity.class);
                                i.putExtra("EventName", tmp);
                                i.putExtra("UserId", userID);
                                getActivity().startActivity(i);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public HomeFragment.EventsHoriHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.item_scroll_poster_home, viewGroup, false);
                        return new EventsHoriHolder(view);
                    }
                };


        //Set adapter
        firebaseRecyclerAdapter.startListening();
        mRecycleHorizontal.setAdapter(firebaseRecyclerAdapter);
        Toast.makeText(getActivity(),"Started display", Toast.LENGTH_LONG).show();
    }

    protected static class EventsHoriHolder extends RecyclerView.ViewHolder {

        //myView for view holder
        View mView;

        //component
        private CardView card;
        private ImageView poster;


        //default constructor
        public EventsHoriHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            card = mView.findViewById(R.id.hori_poster_card);
            poster= mView.findViewById(R.id.hori_poster_img);
        }

        public void FillinHolder(Context ctx, String img){

            //Load image
            Glide.with(ctx).load(img).placeholder(R.drawable.common_full_open_on_phone).into(poster);
            //Picasso.get().load(model.getImg()).placeholder(R.drawable.common_full_open_on_phone).into(holder.clubImage);
        }

        public CardView getEventsHoriCard(){
            return card;
        }
    }

    private void populateVerticalEvent(Query mQueryVerti) {
        //New an option, bind the query and data model
        FirebaseRecyclerOptions<Event> options =
                new FirebaseRecyclerOptions.Builder<Event>()
                        .setQuery(mQueryVerti, Event.class)
                        .build();

        //New an adapter. Override two methods
        FirebaseRecyclerAdapter<Event, EventsVertiHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Event, EventsVertiHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull EventsVertiHolder holder, int position, @NonNull Event model) {
                        holder.FillinHolder(getContext(), model.getImg(), model.getTitle(),
                                model.getHeldbyClub(),model.getTime(),model.getLocation());

                        final String tmp = model.getTitle();
                        holder.getEventsVertiHolder().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(getActivity().getApplicationContext(), EventDetailActivity.class);
                                i.putExtra("EventName", tmp);
                                i.putExtra("UserId", userID);
                                getActivity().startActivity(i);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public EventsVertiHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.item_listevent, viewGroup, false);
                        return new EventsVertiHolder(view);
                    }
                };

        //Set adapter
        firebaseRecyclerAdapter.startListening();
        mRecycleVertical.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.search, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public void onClick(View v) {

    }




//    private void initView() {
//        //add the item to List<events>
//        events = new ArrayList<>();
//        events.add(new Event("Hari raya", "This is description",R.mipmap.pos_art_exibition ));
//        events.add(new Event("The second", "This is description",R.mipmap.pos_swim_workshop ));
//
//        mVPagerHeader = getView().findViewById(R.id.now_is_showing);
//        HomeHeaderEventAdapter adapter = new HomeHeaderEventAdapter(getActivity(), events);
//        mVPagerHeader.setAdapter(adapter);
//        mVPagerHeader.setPadding(0, 10,15,0);
//
//        //let the event reference refer to the Events set
//        mEventRef = FirebaseDatabase.getInstance().getReference().child("Events");
//
//        //fill in the content for rollPoster
//        Glide.with(getActivity()).load(img).placeholder(R.drawable.common_full_open_on_phone).into(clubImage);
//    }
//
//    private void firebaseSearch(String st){
//        String searhText = st;
//        Query firebaseSearchQuery = mEventRef.orderByChild("title").startAt(searhText).endAt(searhText + "\uf8ff");
//
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//
//        super.onCreateOptionsMenu(menu, inflater);
//    }
}
