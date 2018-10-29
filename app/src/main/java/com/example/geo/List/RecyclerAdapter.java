package com.example.geo.List;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.geo.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>  {
    private ArrayList<Trackable> listTrackable;
    private Context mContext;
    private ArrayList<Trackable> mFilteredList;

    public RecyclerAdapter(ArrayList<Trackable> listTrackable, Context mContext) {
        this.listTrackable = listTrackable;
        this.mContext = mContext;
        this.mFilteredList = listTrackable;


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView textViewName;
        public AppCompatTextView textViewDesc;
        public AppCompatTextView textViewWebsite;
        public AppCompatTextView textViewCategory;
        public ImageView overflow;

        public MyViewHolder(View view) {
            super(view);

            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewName);
            textViewDesc = (AppCompatTextView) view.findViewById(R.id.textViewDesc);
            textViewWebsite = (AppCompatTextView) view.findViewById(R.id.textViewWebsite);
            textViewCategory = (AppCompatTextView) view.findViewById(R.id.textViewCategory);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_track_recycler, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.textViewName.setText(listTrackable.get(position).getName());
        holder.textViewDesc.setText(listTrackable.get(position).getDesc());
        holder.textViewWebsite.setText(listTrackable.get(position).getWebsite());
        holder.textViewCategory.setText(listTrackable.get(position).getCategory());
    }
    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }
}
