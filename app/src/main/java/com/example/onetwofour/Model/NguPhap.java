package com.example.onetwofour.Model;

import android.graphics.Bitmap;

public class NguPhap {
    private String topicName;
    private String hinh;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public NguPhap(String topicName, String hinh) {
        this.topicName = topicName;
        this.hinh = hinh;
    }

    public NguPhap() {
    }
}
