package com.estsoft.muvigram.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gangGongUi on 2016. 10. 12..
 */
public class TestRepo {

    @SerializedName("one")
    private String time;
    @SerializedName("key")
    private String date;


    @Override
    public String toString() {
        return "TestRepo{" +
                "time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
