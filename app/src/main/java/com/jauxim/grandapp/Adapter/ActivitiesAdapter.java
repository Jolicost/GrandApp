package com.jauxim.grandapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jauxim.grandapp.ActivityModel;
import com.jauxim.grandapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.MyViewHolder> {

    private List<ActivityModel> activityList;
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

    public ActivitiesAdapter(Context context, List<ActivityModel> moviesList) {
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
        ActivityModel activity = activityList.get(position);
        holder.title.setText(activity.getName());
        holder.author.setText(activity.getOrganizer());
        holder.distance.setText(activity.getLocation().first+"m");
        holder.ratingBar.setRating(activity.getRating());
        Picasso.get().load(activity.getUrl()).into(holder.image);

        if (activity.getOrganizer().toLowerCase().contains("Ayuntamiento".toLowerCase())){
            holder.star.setVisibility(View.VISIBLE);
        }else{
            holder.star.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }
}