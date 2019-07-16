package com.example.xville_v1.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.xville_v1.R;


public class EventsViewHolder extends RecyclerView.ViewHolder{

    ImageView mPoster;
    TextView mTitle, mByClub, mDate, mLocation;

    public EventsViewHolder(@NonNull View itemView) {
        super(itemView);

        mPoster = itemView.findViewById(R.id.list_poster);
        mTitle = itemView.findViewById(R.id.pos_title);
        mByClub = itemView.findViewById(R.id.pos_heldByClub);
        mDate = itemView.findViewById(R.id.pos_date);
        mLocation = itemView.findViewById(R.id.pos_location);
    }


}

