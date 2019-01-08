package com.jauxim.grandapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RateModel {

    @SerializedName("rating")
    @Expose
    private float rate;

    public RateModel(float rate) {
        this.rate = rate;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
