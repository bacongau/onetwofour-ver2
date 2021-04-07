package com.example.onetwofour.Model;

public class TuVung {
    private String desc;
    private String linkhinh;
    private String word;

    public TuVung() {
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLinkhinh() {
        return linkhinh;
    }

    public void setLinkhinh(String linkhinh) {
        this.linkhinh = linkhinh;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public TuVung(String desc, String linkhinh, String word) {
        this.desc = desc;
        this.linkhinh = linkhinh;
        this.word = word;
    }
}
