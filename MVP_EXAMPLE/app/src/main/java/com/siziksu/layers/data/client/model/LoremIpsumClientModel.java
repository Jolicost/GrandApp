package com.siziksu.layers.data.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public final class LoremIpsumClientModel {

    @SerializedName("list")
    @Expose
    public List<String> list = new ArrayList<>();
}
