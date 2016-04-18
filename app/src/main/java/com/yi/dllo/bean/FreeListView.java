package com.yi.dllo.bean;

import android.graphics.Bitmap;

/**
 * Created by dllo on 16/1/9.
 */
public class FreeListView {
    private String name, number;
    private Bitmap bitmap;

    public FreeListView(String name, String number, Bitmap bitmap) {
        this.name = name;
        this.number = number;
        this.bitmap = bitmap;
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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
