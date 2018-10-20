package com.jauxim.grandapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ActivityListItemModel {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("userId")
    @Expose
    private Long userId;

    @SerializedName("rating")
    @Expose
    private Long rating;

    @SerializedName("type")
    @Expose
    private Long type;

    @SerializedName("capacity")
    @Expose
    private Long capacity;

    @SerializedName("members")
    @Expose
    private Long members;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public Long getMembers() {
        return members;
    }

    public void setMembers(Long members) {
        this.members = members;
    }
}