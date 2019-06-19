package com.example.xville_v1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xville_v1.Event;
import com.example.xville_v1.R;

import java.util.List;

public class HomeHeaderEventAdapter extends PagerAdapter {

    protected Context context;
    private List<Event> events;
    private LayoutInflater layoutInflater;

    public HomeHeaderEventAdapter(Context context, List<Event> events){
        this.context = context;
        this.events = events;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);

        //create a view inflate by an layout item consist of imageView, two TextView
        View view = layoutInflater.inflate(R.layout.event_poster, container, false);

        ImageView imageView;
        TextView title, desc;

        imageView = view.findViewById(R.id.poster);
        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.description);

        imageView.setImageResource(events.get(position).getLocalPos());
        title.setText(events.get(position).getTitle());
        desc.setText(events.get(position).getDescription());

        container.addView(view, 0);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
        super.destroyItem(container, position, object);
    }
}
