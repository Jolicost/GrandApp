package com.jauxim.grandapp.ui.Fragment.ActiviesList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jauxim.grandapp.Constants;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.UserLocationUtils;
import com.jauxim.grandapp.Utils.Utils;
import com.jauxim.grandapp.models.ActivityListItemModel;
import com.jauxim.grandapp.ui.Activity.ActivityInfo.ActivityInfo;

import java.util.List;

import static android.graphics.Color.rgb;

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
        holder.title.setPadding(5, 5, 5 ,5);

        //holder.author.setText(activity.get());
        /*
        UserLocationUtils userLocUtils = new UserLocationUtils();
        Pair<Double, Double> userLoc = userLocUtils.getActualUserPosition();
        */

        //float distance = Utils.getAbsoluteDistance(userLoc.first, userLoc.second, 41.529333, 2.435116);
        float distance = Utils.getAbsoluteDistance(41.501598, 2.387201, 41.529333, 2.435116);
        //String distanceWalked = String.valueOf(Utils.getAbsoluteDistance(41.501598, 2.387201, 41.529333, 2.435116));
        //String distanceWalked = Utils.getWalkingDistance(userLat, userLong, activity.getLatitude(), activity.getLongitude());
        String distanceWalked;
        if(distance >= 1000) {
            distanceWalked = String.format("%.2f", distance/1000) + " km";
        } else {
            distanceWalked = String.format("%.2f", distance) + " m";
        }

        holder.distance.setText(distanceWalked);
        holder.distance.setPadding(5, 5, 5 ,5);

        String countDownTime = Utils.getCountDownTime(activity.getTimestampStart());

        holder.time.setText(countDownTime);
        holder.time.setTextSize(22);
        holder.time.setPadding(5, 5, 5 ,5);

        if(countDownTime.equalsIgnoreCase("gone!")) {
            holder.time.setTextColor(rgb(216, 19, 19));
        } else {
            holder.time.setTextColor(rgb(11, 188, 37));
        }

        holder.gauge.setText(activity.getnUsers() + "/" + activity.getMaxCapacity());
        holder.gauge.setTextSize(20);
        holder.gauge.setPadding(5, 5, 5 ,5);

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