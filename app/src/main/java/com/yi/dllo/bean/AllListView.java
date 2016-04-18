package com.yi.dllo.bean;

/**
 * Created by dllo on 16/1/9.
 */
public class AllListView {
    private String number, name;
    private int handImageId;

    public AllListView() {
    }

    public AllListView(String number, String name, int handImageId) {
        this.number = number;
        this.name = name;
        this.handImageId = handImageId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHandImageId() {
        return handImageId;
    }

    public void setHandImageId(int handImageId) {
        this.handImageId = handImageId;
    }
}
