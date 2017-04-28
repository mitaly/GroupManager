package com.techeducation.groupmanager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitaly on 5/3/17.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    List<EventsFeed> allUsersBeenList;

    //MyViewHolders initializes the views in listIte
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,date,time,venue,desc;


        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.tvEventName);
            date = (TextView)itemView.findViewById(R.id.tvEventDate);
            time = (TextView)itemView.findViewById(R.id.tvEventTime);
            venue = (TextView)itemView.findViewById(R.id.txtEvntVenue);
            desc = (TextView)itemView.findViewById(R.id.tvEventDesc);
            Log.i("showObj3","view holder events+ "+itemView.toString());
        }
    }

    public EventsAdapter(List<EventsFeed> allUsersBeenList) {
        this.allUsersBeenList = allUsersBeenList;
        Log.i("showobj1","list "+allUsersBeenList.toString());
    }

    //onCreateViewHolder inflates the listItem
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("showObj1","onCreateViewHolder  ");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item,parent,false);
        Log.i("showObj1","onCreateViewHolder itemview "+itemView.toString());
        return new MyViewHolder(itemView);
    }

    //onBindViewHolder puts the data into views defined in listItem
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EventsFeed allUsersBean = allUsersBeenList.get(position);
        // holder.profileImg.setImageResource(allUsersBean.getProfilePhoto());
        Log.i("showobj1","event object in adapter: "+allUsersBean.toString());
        holder.name.setText(allUsersBean.getName());
        holder.date.setText(allUsersBean.getDate());
        holder.time.setText(allUsersBean.getTime());
        holder.venue.setText(allUsersBean.getVenue());
        holder.desc.setText(allUsersBean.getDesc());

    }

    //Get size of List
    @Override
    public int getItemCount() {
        Log.i("showobj1","length of list+ "+allUsersBeenList.size());
        return allUsersBeenList.size();
    }

}
