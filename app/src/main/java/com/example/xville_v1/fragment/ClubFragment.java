package com.example.xville_v1.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.xville_v1.Model.Club;
import com.example.xville_v1.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClubFragment extends Fragment{

    //Views
    private RecyclerView mRecycleList;
    private ImageView moment;
    private Toolbar toolbar;
    private Dialog mDialog;

    //Firebase
    private DatabaseReference ClubRef;
    //query
    private Query mQueryclub;

    //Activity
    private Activity mActivity;
    private AppCompatActivity mAppCompatActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_club, container, false);
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

        //RecycleView
        mRecycleList = getView().findViewById(R.id.recycle_club_list);
        mRecycleList.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Dialog
        mDialog = new Dialog(getContext());
        mDialog.setContentView(R.layout.dialog_clubinfo);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //icon for jumping to the club moment
        moment = getView().findViewById(R.id.imv_moment);
    }

    @Override
    public void onStart() {
        super.onStart();
        toolbar.setTitle("Club");

        //Firebase database
        ClubRef = FirebaseDatabase.getInstance().getReference().child("Clubs");

        //Query
        mQueryclub = FirebaseDatabase.getInstance()
                .getReference()
                .child("Clubs")
                .limitToLast(50);

        //Option
        FirebaseRecyclerOptions<Club> options =
                new FirebaseRecyclerOptions.Builder<Club>()
                        .setQuery(mQueryclub, Club.class)
                        .build();

        //Firebase Recycler adapter
        FirebaseRecyclerAdapter<Club, ClubsViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Club, ClubsViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final ClubsViewHolder holder, int position, @NonNull final Club model) {
                        holder.FillinHolder(getContext(),model.getName(),model.getBrief(), model.getImg());

                        final String nam = model.getName();
                        final String profilUrl = model.getImg();
                        holder.getClubHolder().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //send data to dialog
                                TextView tv = getView().findViewById(R.id.info_club_name);
                                TextView des = getView().findViewById(R.id.info_club_des);
                                ImageView profile = getView().findViewById(R.id.info_club_avatar);
                                tv.setText(nam);
                                des.setText("This is "+ nam);
                                Glide.with(getContext()).load(profilUrl).placeholder(R.mipmap.img_default_profile).into(profile);

                                mDialog.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ClubsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.item_listclub, viewGroup, false);

                        return new ClubsViewHolder(view);
                    }
                };

        firebaseRecyclerAdapter.startListening();
        mRecycleList.setAdapter(firebaseRecyclerAdapter);


//        moment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                used for jump to activity
//                Intent intent = new Intent(getActivity(), ClubMomentActivity.class);
//                startActivity(intent);
//
//                used for jump to fragment
//                Fragment fragment = new ClubMomentFragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.content_frame, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });
  }


    //Holder to hold the view for one club in the list, should be the static class
    public static class ClubsViewHolder extends RecyclerView.ViewHolder{

        private TextView clubName, clubBrief;
        private CircleImageView clubImage;
        private View mView;
        private LinearLayout clubHolder;


        //Binding the view with ID in xml layout resource
        public ClubsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            clubName= mView.findViewById(R.id.tv_club_name);
            clubBrief= mView.findViewById(R.id.tv_club_brief);
            clubImage= mView.findViewById(R.id.iv_club_profile_img);
            clubHolder= mView.findViewById(R.id.item_club);
        }

        public void FillinHolder(Context ctx, String name, String brief, String img) {
            clubName.setText(name);
            clubBrief.setText(brief);
            Glide.with(ctx).load(img).placeholder(R.mipmap.img_default_profile).into(clubImage);
            Toast.makeText(ctx, "iTEMS SHOW", Toast.LENGTH_LONG).show();
        }

        public LinearLayout getClubHolder(){
            return clubHolder;
        }
    }



}
