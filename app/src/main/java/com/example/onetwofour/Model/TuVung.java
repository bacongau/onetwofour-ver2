package com.example.onetwofour.Model;

public class TuVung {
    private String topic;
    private byte[] image;
    private String word;
    private String desc;

    public TuVung() {
    }

    public TuVung(String topic, byte[] image, String word, String desc) {
        this.topic = topic;
        this.image = image;
        this.word = word;
        this.desc = desc;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
