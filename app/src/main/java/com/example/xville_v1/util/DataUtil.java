package com.example.xville_v1.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xville_v1.Event;
import com.example.xville_v1.R;

import java.util.ArrayList;
import java.util.List;


public class DataUtil  {

    protected List<Event> events;
    private LayoutInflater layoutInflater;
    private Context context;

    public DataUtil(List<Event> events, Context context) {
        this.events = events;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }


    public List<ImageView> getHomeEventsHeaderInfo(){
        List<ImageView> data = new ArrayList<>();

        //create a view inflate by an layout item consist of imageView, two TextView
        View view = layoutInflater.inflate(R.layout.event_poster,null);
        ImageView imageView;
        TextView title, desc;

        imageView = view.findViewById(R.id.poster);
        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.description);


        return data;
    }
}
