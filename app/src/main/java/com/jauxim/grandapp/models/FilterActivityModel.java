package com.jauxim.grandapp.models;

public class FilterActivityModel {

    public @interface typeSort {
        int SORT_CREATE_DESC = 0;
        int SORT_CREATE_ASC = 1;
        int SORT_START_DESC = 2;
        int SORT_START_ASC = 3;
        int SORT_PRICE_DESC = 4;
        int SORT_PRICE_ASC = 5;
        int SORT_NAME_DESC = 6;
        int SORT_NAME_ASC = 7;
    }

    private String name;
    private Long maxPrice;
    private Long minPrice;
    private Long maxDistance;
    private Long minDistance;
    private Long startTime;
    private Long endTime;
    private int sort;
    private String category;

    public Long getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Long maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Long minPrice) {
        this.minPrice = minPrice;
    }

    public Long getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Long maxDistance) {
        this.maxDistance = maxDistance;
    }

    public Long getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(Long minDistance) {
        this.minDistance = minDistance;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
