package com.jauxim.grandapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ActivityModel {

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("price")
    @Expose
    private Long price;

    @SerializedName("userId")
    @Expose
    private static String userId;

    @SerializedName("rating")
    @Expose
    private Long rating;

    @SerializedName("lat")
    @Expose
    private Double latitude;

    @SerializedName("long")
    @Expose
    private Double longitude;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("type")
    @Expose
    private Long type;

    @SerializedName("capacity")
    @Expose
    private Long capacity;

    @SerializedName("images")
    @Expose
    private List<String> images;

    @SerializedName("timestampStart")
    @Expose
    private Long timestampStart;

    @SerializedName("timestampEnd")
    @Expose
    private Long timestampEnd;

    private List<String> imagesBase64;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public static String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
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

    public List<String> getImagesUrl() {
        return images;
    }

    public void setImagesUrl(List<String> images) {
        this.images = images;
    }

    public List<String> getImagesBase64() {
        return imagesBase64;
    }

    public void setImagesBase64(List<String> imagesBase64) {
        this.imagesBase64 = imagesBase64;
    }

    public Long getTimestampStart() {
        return timestampStart;
    }

    public void setTimestampStart(Long timestampStart) {
        this.timestampStart = timestampStart;
    }

    public Long getTimestampEnd() {
        return timestampEnd;
    }

    public void setTimestampEnd(Long timestampEnd) {
        this.timestampEnd = timestampEnd;
    }
}