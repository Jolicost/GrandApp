package com.jauxim.grandapp;

import android.util.Pair;

public class ActivityModel {

    private String urlImage;
    private String name;
    private String description;
    private float rating;
    private String organizer;
    private Pair<Long, Long> location;

    public ActivityModel(String urlImage, String name, String description, float rating, String organizer, Pair<Long, Long> location) {
        this.urlImage = urlImage;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.organizer = organizer;
        this.location = location;
    }

    public String getUrl() {
        return urlImage;
    }

    public void setUrl(String url) {
        this.urlImage = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public Pair<Long, Long> getLocation() {
        return location;
    }

    public void setLocation(Pair<Long, Long> location) {
        this.location = location;
    }
}
