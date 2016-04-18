package com.yi.dllo.bean;

import android.graphics.Bitmap;

/**
 * Created by dllo on 16/1/13.
 */
public class SonInfo {
    private String name, number;
    private Bitmap hand_iv;

    public SonInfo(String name, String number, Bitmap hand_iv) {
        this.name = name;
        this.number = number;
        this.hand_iv = hand_iv;
    }

    public Bitmap getHand_iv() {
        return hand_iv;
    }

    public void setHand_iv(Bitmap hand_iv) {
        this.hand_iv = hand_iv;
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
}
