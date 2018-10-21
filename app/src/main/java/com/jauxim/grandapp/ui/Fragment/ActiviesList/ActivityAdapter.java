package com.jauxim.grandapp.ui.Fragment.ActiviesList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jauxim.grandapp.ActivityModel;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.models.ActivityListItemModel;
import com.jauxim.grandapp.ui.Activity.ActivityInfo.ActivityInfo;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.MyViewHolder> {

    private List<ActivityListItemModel> activityList;
    public Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, author, distance;
        public ImageView image, star;
        public RatingBar ratingBar;

        public MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image_activity);
            author = view.findViewById(R.id.organizer_activity);
            title = view.findViewById(R.id.title_activity);
            distance = view.findViewById(R.id.distance_activity);
            ratingBar = view.findViewById(R.id.rating_activity);
            star = view.findViewById(R.id.star);
        }
    }

    public ActivityAdapter(Context context, List<ActivityListItemModel> moviesList) {
        this.activityList = moviesList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ActivityListItemModel activity = activityList.get(position);
        holder.title.setText(activity.getTitle());
        //holder.author.setText(activity.get());
        //holder.distance.setText(activity.get().first+"m");
        holder.ratingBar.setRating((float) (activity.getRating()));
        Glide.with(holder.image.getContext())
                .load(activity.getImage())
                .into(holder.image);

        /*
        if (activity.getOrganizer().toLowerCase().contains("Ayuntamiento".toLowerCase())){
            holder.star.setVisibility(View.VISIBLE);
        }else{
            holder.star.setVisibility(View.GONE);
        }
        */

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityInfo.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }
}