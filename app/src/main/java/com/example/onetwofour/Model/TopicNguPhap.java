package com.example.onetwofour.Model;

import android.graphics.Bitmap;

public class TopicNguPhap {
    private byte[] img;
    private String topicName;

    public TopicNguPhap() {
    }

    public TopicNguPhap(byte[] img, String topicName) {
        this.img = img;
        this.topicName = topicName;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
