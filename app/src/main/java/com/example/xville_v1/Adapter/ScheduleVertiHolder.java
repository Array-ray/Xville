package com.example.xville_v1.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.xville_v1.R;

public class ScheduleVertiHolder extends RecyclerView.ViewHolder {

    //myView for view holder
    View mView;

    //component
    private TextView title;

    public ScheduleVertiHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        title = mView.findViewById(R.id.tit_schedule);
    }

    public void setScheduleTitle(String tit){
        //Load data
        title.setText(tit);
    }

}
