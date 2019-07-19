package com.example.xville_v1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xville_v1.R;

public class EventsVertiHolder extends RecyclerView.ViewHolder{

    //myView for view holder
    View mView;

    //Linear layout
    private LinearLayout card;

    //component
    private ImageView poster;
    private TextView title;
    private TextView heldbyClub;
    private TextView time;
    private TextView location;


    //default constructor
    public EventsVertiHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        poster = mView.findViewById(R.id.list_poster);
        title = mView.findViewById(R.id.pos_title);
        heldbyClub = mView.findViewById(R.id.pos_heldByClub);
        time = mView.findViewById(R.id.pos_date);
        location = mView.findViewById(R.id.pos_location);

        card = mView.findViewById(R.id.event_card);
    }

    public void FillinHolder(Context ctx, String img, String tit, String hbClub, String tim,
                                    String loc){
        //Load poster
        Glide.with(ctx).load(img).placeholder(R.drawable.img_placeholder_upload).into(poster);
        title.setText(tit);
        heldbyClub.setText(hbClub);
        time.setText(tim);
        location.setText(loc);
    }

    public LinearLayout getEventsVertiHolder(){
        return card;
    }

}

