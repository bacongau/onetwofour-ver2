package com.example.onetwofour.Model;

public class BaiDoc {
    private String ten;
    private String hinh;
    private String eng;
    private String viet;

    public BaiDoc() {
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

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

    public BaiDoc(String ten, String hinh, String eng, String viet) {
        this.ten = ten;
        this.hinh = hinh;
        this.eng = eng;
        this.viet = viet;
    }
}
