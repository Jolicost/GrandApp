package com.jauxim.grandapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RateModel {

    @SerializedName("rating")
    @Expose
    private Long rate;

    public RateModel(Long rate) {
        this.rate = rate;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }
}
