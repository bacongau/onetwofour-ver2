package com.example.onetwofour.Model;

public class MyVocab {
    private String keyword;
    private String mota;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public MyVocab(String keyword, String mota) {
        this.keyword = keyword;
        this.mota = mota;
    }

    public MyVocab() {
    }
}
