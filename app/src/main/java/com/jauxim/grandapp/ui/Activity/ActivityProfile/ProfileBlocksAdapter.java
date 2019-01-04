package com.jauxim.grandapp.ui.Activity.ActivityProfile;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jauxim.grandapp.Constants;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.models.BlockModel;
import com.jauxim.grandapp.models.EmergencyContactsModel;

import java.util.List;

public class ProfileBlocksAdapter extends RecyclerView.Adapter<ProfileBlocksAdapter.MyViewHolder> {

    private List<BlockModel> blocksList;
    public Activity context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView ivBlockedPic;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tvName);
            ivBlockedPic = view.findViewById(R.id.ivBlockedPic);
        }
    }

    public ProfileBlocksAdapter(Activity context, List<BlockModel> blocksList) {
        this.blocksList = blocksList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.block_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final BlockModel blocksModel = blocksList.get(position);
        holder.name.setText(blocksModel.getName());
        Glide.with(context)
                .load(blocksModel.getPic())
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(holder.ivBlockedPic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityProfile.class);
                intent.putExtra(Constants.PROFILE_ID, blocksModel.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return blocksList.size();
    }
}