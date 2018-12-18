package com.jauxim.grandapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class NotificationsModel {

    @SerializedName("nearActivity")
    @Expose
    private Boolean nearActivityCreated;

    @SerializedName("joinedActivity")
    @Expose
    private Boolean userJoinedActivity;

    @SerializedName("finishedActivity")
    @Expose
    private Boolean joinedActivityEnded;

    public NotificationsModel() {

    }

    public NotificationsModel(Boolean joinedActivityEnded, Boolean userJoinedActivity, Boolean nearActivityCreated) {
        this.joinedActivityEnded = joinedActivityEnded;
        this.userJoinedActivity = userJoinedActivity;
        this.nearActivityCreated = nearActivityCreated;
    }

    public Boolean getNearActivityCreated() {
        return nearActivityCreated;
    }

    public void setNearActivityCreated(Boolean nearActivityCreated) {
        this.nearActivityCreated = nearActivityCreated;
    }

    public Boolean getUserJoinedActivity() {
        return userJoinedActivity;
    }

    public void setUserJoinedActivity(Boolean userJoinedActivity) {
        this.userJoinedActivity = userJoinedActivity;
    }

    public Boolean getJoinedActivityEnded() {
        return joinedActivityEnded;
    }

    public void setJoinedActivityEnded(Boolean joinedActivityEnded) {
        this.joinedActivityEnded = joinedActivityEnded;
    }
}
