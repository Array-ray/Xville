package com.example.xville_v1.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.xville_v1.Model.Event;
import com.example.xville_v1.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ClubGridEventFragment extends Fragment {

    //Bundle and intent, from club main activity
    private String clubName;

    //View
    private RecyclerView mRecyclePostGrid;

    //Firebase
    //database reference (point to the specific child)
    private DatabaseReference mPostGridRef;

    //query
    private Query mQueryPostGridRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.club_fragment_first, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clubName = getArguments().getString("CLUBNAME");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //View
        mRecyclePostGrid = getView().findViewById(R.id.club_recycler_grid);

        //LayoutManager (for layout purpose) and adapter
        //GRID
        mRecyclePostGrid.setLayoutManager(new GridLayoutManager(getActivity(),3));
        //mRecyclePostGrid.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onStart() {
        super.onStart();
        //Firebase database
        mPostGridRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        if(mPostGridRef.child("no no club") != null){
            loadPostInGridForm();
        }
    }

    private void loadPostInGridForm() {
        mQueryPostGridRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("Posts")
                .child("no no club")
                .limitToLast(50);

        FirebaseRecyclerOptions<Event> options =
                new FirebaseRecyclerOptions.Builder<Event>()
                        .setQuery(mQueryPostGridRef, Event.class)
                        .build();

        //New an adapter. Override two methods
        FirebaseRecyclerAdapter<Event, EventsHoriHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Event, EventsHoriHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull EventsHoriHolder holder, int position, @NonNull Event model) {
                        holder.FillinHolder(getContext(), model.getImg());

                    }

                    @NonNull
                    @Override
                    public EventsHoriHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.item_scroll_poster_home, viewGroup, false);
                        return new EventsHoriHolder(view);
                    }
                };

        //Set adapter
        firebaseRecyclerAdapter.startListening();
        mRecyclePostGrid.setAdapter(firebaseRecyclerAdapter);
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
}
