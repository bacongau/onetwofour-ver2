package com.example.onetwofour.Model;

public class MauCau {
    private String eng;
    private String viet;

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getViet() {
        return viet;
    }

    public void setViet(String viet) {
        this.viet = viet;
    }

    public MauCau(String eng, String viet) {
        this.eng = eng;
        this.viet = viet;
    }

    public MauCau() {
    }
}
