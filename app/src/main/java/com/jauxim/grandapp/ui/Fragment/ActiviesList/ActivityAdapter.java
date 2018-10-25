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
import com.jauxim.grandapp.Constants;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.Utils;
import com.jauxim.grandapp.models.ActivityListItemModel;
import com.jauxim.grandapp.ui.Activity.ActivityInfo.ActivityInfo;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.MyViewHolder> {

    private List<ActivityListItemModel> activityList;
    public Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, gauge, distance, time;
        public ImageView image, star;

        public MyViewHolder(View view) {
            super(view);
            time = view.findViewById(R.id.tvTime);
            image = view.findViewById(R.id.ivImage);
            gauge = view.findViewById(R.id.tvGauge);
            title = view.findViewById(R.id.tvTitle);
            distance = view.findViewById(R.id.tvDistance);
            star = view.findViewById(R.id.ivStar);


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
        final ActivityListItemModel activity = activityList.get(position);
        holder.title.setText(activity.getTitle());
        //holder.author.setText(activity.get());
        String distanceWalked = Utils.getWalkingDistance(41.501598, 2.387201, 41.529333, 2.435116);
        //String distanceWalked = Utils.getWalkingDistance(userLat, userLong, activity.getLatitude(), activity.getLongitude());

        holder.distance.setText(distanceWalked);
        holder.time.setText(Utils.getCountDownTime(activity.getTimestampStart()));
        holder.gauge.setText(activity.getnUsers() + "/" + activity.getMaxCapacity());

        Glide.with(holder.image.getContext()).load(activity.getImage()).into(holder.image);

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
                intent.putExtra(Constants.ACTIVITY_ID, activity.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }
}