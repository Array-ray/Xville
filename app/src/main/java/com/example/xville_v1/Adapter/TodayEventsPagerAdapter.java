package com.example.xville_v1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xville_v1.Model.EventToday;
import com.example.xville_v1.R;

import java.util.List;

public class TodayEventsPagerAdapter extends PagerAdapter {

    private List<EventToday> events;
    private LayoutInflater layoutInflater;
    private Context context;

    public TodayEventsPagerAdapter(List<EventToday> events, Context context) {
        this.events = events;
        this.context = context;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);

        //create a view inflate by an layout item consist of imageView, two TextView
        View view = layoutInflater.inflate(R.layout.item_today_card_event, container, false);

        ImageView imageView;
        TextView title, desc;

        imageView = view.findViewById(R.id.today_poster);
        title = view.findViewById(R.id.today_title);
        desc = view.findViewById(R.id.today_description);

        //Glide.with(context).load(events.get(position).getLocalPos()).into(imageView);
        imageView.setImageResource(events.get(position).getLocalPos());
        title.setText(events.get(position).getTitle());
        desc.setText(events.get(position).getDescription());

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
        super.destroyItem(container, position, object);
    }
}
