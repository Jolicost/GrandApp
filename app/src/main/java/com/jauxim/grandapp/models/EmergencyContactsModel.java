package com.jauxim.grandapp.models;

import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class EmergencyContactsModel {

    private List<PairStringModel> emergencyContactList;

    public List<PairStringModel> getEmergencyContactList() {
        return emergencyContactList;
    }

    public void setEmergencyContactList(List<PairStringModel> emergencyContactList) {
        this.emergencyContactList = emergencyContactList;
    }
}