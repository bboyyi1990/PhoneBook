package com.yi.dllo.bean;

/**
 * Created by dllo on 16/1/8.
 */
public class LogListView {
    private String name, number, type, time;


    public LogListView() {
    }

    public LogListView(String name, String number, String type, String time) {
        this.name = name;
        this.number = number;
        this.type = type;

        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
