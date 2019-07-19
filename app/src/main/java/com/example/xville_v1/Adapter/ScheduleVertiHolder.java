package com.example.xville_v1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xville_v1.R;

public class ScheduleVertiHolder extends RecyclerView.ViewHolder {

    //myView for view holder
    View mView;

    //component
    private ImageView poster;
    private TextView title;
    private TextView about;
    private TextView location;
    private TextView time;

    public ScheduleVertiHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        poster = mView.findViewById(R.id.schedule_pos);
        title = mView.findViewById(R.id.tit_schedule);
        about = mView.findViewById(R.id.about_schedule);
        location = mView.findViewById(R.id.schedule_location);
        time = mView.findViewById(R.id.schedule_time);
    }

    public void setScheduleTitle(String tit){
        //Load data
        title.setText(tit);
    }
    public void FillinHolder(Context ctx, String img, String tit, String abt, String tim,
                             String loc){
        //Load poster
        Glide.with(ctx).load(img).placeholder(R.drawable.img_placeholder_upload).into(poster);
        title.setText(tit);
        about.setText(abt);
        time.setText(tim);
        location.setText(loc);
    }

}
