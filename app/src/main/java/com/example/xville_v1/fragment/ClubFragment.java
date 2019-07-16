package com.example.xville_v1.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.xville_v1.Model.Club;
import com.example.xville_v1.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClubFragment extends Fragment{

    //Views
    private View mclubsView;
    private RecyclerView mRecycleList;
    private ImageView moment;

    //Firebase
    Query ClubRef;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mclubsView = inflater.inflate(R.layout.fragment_club, container, false);
        return mclubsView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //RecycleView for club list
        mRecycleList = getView().findViewById(R.id.recycle_club_list);
//        mRecycleList.setHasFixedSize(true);
        mRecycleList.setLayoutManager(new LinearLayoutManager(getContext()));

        //Firebase database
        ClubRef = FirebaseDatabase.getInstance().getReference().child("Clubs");

        //jump to the club moment
        moment = getView().findViewById(R.id.imv_moment);
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseClubDisplay();

        moment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //used for jump to activity
//                Intent intent = new Intent(getActivity(), ClubMomentActivity.class);
//                startActivity(intent);

                //used for jump to fragment
                Fragment fragment = new ClubMomentFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }


    public void firebaseClubDisplay() {
        FirebaseRecyclerOptions<Club> options =
                new FirebaseRecyclerOptions.Builder<Club>()
                        .setQuery(ClubRef, Club.class)
                        .build();

        FirebaseRecyclerAdapter<Club, ClubsViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Club, ClubsViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ClubsViewHolder holder, int position, @NonNull Club model) {
                        TextView cn = getView().findViewById(R.id.tv_club_name);
                        cn.setText(model.getName());
                        holder.FillinHolder(getContext(),model.getName(),model.getBrief(), model.getImg());
                    }

                    @NonNull
                    @Override
                    public ClubsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.item_listclub, viewGroup, false);

                        return new ClubsViewHolder(view);
                    }
                };




        //bind the firebaseRecyclerAdapter to the Recyclelist
        firebaseRecyclerAdapter.startListening();
        mRecycleList.setAdapter(firebaseRecyclerAdapter);

        Toast.makeText(getActivity(),"Started display", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    //Holder to hold the view for one club in the list, should be the static class
    public static class ClubsViewHolder extends RecyclerView.ViewHolder{

        private TextView clubName, clubBrief;
        private CircleImageView clubImage;
        View mView;
//

        //Binding the view with ID in xml layout resource
        public ClubsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void FillinHolder(Context ctx, String name, String brief, String img){
            clubName= mView.findViewById(R.id.tv_club_name);
            clubBrief= mView.findViewById(R.id.tv_club_brief);
            clubImage= mView.findViewById(R.id.iv_club_profile_img);

            clubName.setText(name);
            clubBrief.setText(brief);
            Glide.with(ctx).load(img).placeholder(R.drawable.common_full_open_on_phone).into(clubImage);
            //Picasso.get().load(model.getImg()).placeholder(R.drawable.common_full_open_on_phone).into(holder.clubImage);
            Toast.makeText(ctx,"iTEMS SHOW", Toast.LENGTH_LONG).show();
        }



    }



}
