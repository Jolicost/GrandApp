package com.jauxim.grandapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ActivityListItemModel {

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("capacity")
    @Expose
    private Long maxCapacity;

    @SerializedName("nUsers")
    @Expose
    private Long nUsers;

    @SerializedName("images")
    @Expose
    private List<String> images;

    @SerializedName("lat")
    @Expose
    private Double latitude;

    @SerializedName("long")
    @Expose
    private Double longitude;

    @SerializedName("timestampStart")
    @Expose
    private Long timestampStart;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Long maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Long getnUsers() {
        return nUsers;
    }

    public void setnUsers(Long nUsers) {
        this.nUsers = nUsers;
    }

    public String getImage() {
        if (images!=null && !images.isEmpty())
            return images.get(0);
        return "";
    }

    public void setImage(String image) {
        this.images.set(0, image);
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getTimestampStart() {
        return timestampStart;
    }

    public void setTimestampStart(Long timestampStart) {
        this.timestampStart = timestampStart;
    }
}