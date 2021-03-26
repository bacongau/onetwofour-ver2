package com.example.onetwofour.Model;

public class MauCau {
    private String topic;
    private String engSub;
    private String vietSub;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getEngSub() {
        return engSub;
    }

    public void setEngSub(String engSub) {
        this.engSub = engSub;
    }

    public String getVietSub() {
        return vietSub;
    }

    public void setVietSub(String vietSub) {
        this.vietSub = vietSub;
    }

    public MauCau(String topic, String engSub, String vietSub) {
        this.topic = topic;
        this.engSub = engSub;
        this.vietSub = vietSub;
    }

    public MauCau() {
    }
}
