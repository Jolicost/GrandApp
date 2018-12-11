package com.jauxim.grandapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class EmergencyContactsModel {

    @SerializedName("alias")
    @Expose
    private String alias;

    @SerializedName("phone")
    @Expose
    private String phone;

    public EmergencyContactsModel(String alias, String phone){
        this.alias = alias;
        this.phone = phone;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}