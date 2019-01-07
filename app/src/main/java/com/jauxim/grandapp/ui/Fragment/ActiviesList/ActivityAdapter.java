package com.jauxim.grandapp.ui.Fragment.ActiviesList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jauxim.grandapp.Constants;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.Utils.RxBus;
import com.jauxim.grandapp.Utils.SingleShotLocationProvider;
import com.jauxim.grandapp.Utils.Utils;
import com.jauxim.grandapp.models.ActivityListItemModel;
import com.jauxim.grandapp.ui.Activity.ActivityInfo.ActivityInfo;

import java.util.List;

import rx.Observer;

import static android.graphics.Color.rgb;
import static com.jauxim.grandapp.Constants.ACTIVITY_MINE;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.MyViewHolder> {

    private List<ActivityListItemModel> activityList;
    public Activity context;
    private SingleShotLocationProvider.GPSCoordinates userLocation;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, gauge, distance, time;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            time = view.findViewById(R.id.tvTime);
            image = view.findViewById(R.id.ivImage);
            gauge = view.findViewById(R.id.tvGauge);
            title = view.findViewById(R.id.tvTitle);
            distance = view.findViewById(R.id.tvDistance);

        }
    }

    public ActivityAdapter(Activity context, List<ActivityListItemModel> moviesList) {
        this.activityList = moviesList;
        this.context = context;

        userLocation = DataUtils.getLocation(context);
        RxBus.instanceOf().getLocationObservable().subscribe(new Observer<SingleShotLocationProvider.GPSCoordinates>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(SingleShotLocationProvider.GPSCoordinates newLoc) {
                userLocation = newLoc;
                notifyDataSetChanged();
            }
        });
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

        if (userLocation != null) {
            float distance = Utils.getAbsoluteDistance(activity.getLatitude(), activity.getLongitude(), userLocation.latitude, userLocation.longitude);
            //float distance = Float.parseFloat(Utils.getWalkingDistance(userLocation.latitude, userLocation.longitude, 41.501598, 2.387201));

            String distanceWalked;
            if (distance >= 1000) {
                distanceWalked = String.format("%.2f", distance / 1000) + " km";
            } else {
                distanceWalked = String.format("%.2f", distance) + " m";
            }

            holder.distance.setText(distanceWalked);
        }


        String countDownTime = Utils.getCountDownTime(activity.getTimestampStart(), activity.getTimestampEnd());

        holder.time.setText(countDownTime);

        if (countDownTime.equalsIgnoreCase("gone!")) {
            holder.time.setTextColor(rgb(216, 19, 19));
        } else {
            holder.time.setTextColor(rgb(11, 188, 37));
        }

        if (activity.getParticipants()!=null)
        holder.gauge.setText(activity.getParticipants().size() + "/" + activity.getMaxCapacity());

        if (activity.getMaxCapacity()!=null && activity!=null) {
            if (activity.getMaxCapacity() <= activity.getParticipants().size())
                holder.gauge.setTextColor(rgb(216, 19, 19));
            else {
                holder.gauge.setTextAppearance(context, R.style.textDescription);
                holder.gauge.setTextSize(18);
            }
        }

        Glide.with(holder.image.getContext()).load(activity.getImage()).into(holder.image);

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