package com.example.xville_v1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xville_v1.Model.Event;
import com.example.xville_v1.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ItemViewHolder>  {

    private Context mContext;
    private List<Event> eventList;
    private String event_title;
    private String event_time;
    private String eventlocation;
    private String event_about;
    private String event_type;
    private String event_img;

    public SearchAdapter(Context mContext, List<Event> eventList){
        this.mContext = mContext;
        this.eventList = eventList;
    }



    public void onBindViewHolder(@NonNull SearchAdapter.ItemViewHolder holder, int position) {

        Event event = eventList.get(position);

        holder.mTextViewPos_title.setText(event.getTitle());
        holder.mTextViewPos_heldByClub.setText(event.getHeldbyClub());
        holder.mTextViewPos_location.setText(event.getLocation());
        Glide.with(mContext).load(event.getImg()).into(holder.mImageViewList_poster);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    @NonNull
    @Override
    public SearchAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //for item_row
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_listevent, viewGroup, false);
        //SearchAdapter.ItemViewHolder viewHolder = new SearchAdapter.ItemViewHolder(view);
        return new SearchAdapter.ItemViewHolder(view);
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageViewList_poster;
        private TextView mTextViewPos_title;
        private TextView mTextViewPos_heldByClub;
        private TextView mTextViewPos_location;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mImageViewList_poster = (ImageView)itemView.findViewById(R.id.list_poster);
            mTextViewPos_title = (TextView)itemView.findViewById(R.id.pos_title);
            mTextViewPos_heldByClub = (TextView)itemView.findViewById(R.id.pos_heldByClub);
            mTextViewPos_location = (TextView)itemView.findViewById(R.id.pos_location);

        }

    }
}
