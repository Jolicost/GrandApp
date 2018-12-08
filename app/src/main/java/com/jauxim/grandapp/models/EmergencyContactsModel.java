package com.jauxim.grandapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class EmergencyContactsModel {

    @SerializedName("alias")
    @Expose
    public String alias;

    @SerializedName("phone")
    @Expose
    public String phone;

    public EmergencyContactsModel(String alias, String phone){
        this.alias = alias;
        this.phone = phone;
    }
}