package com.example.onetwofour.Model;

public class BaiNghe {
    private String ten;
    private String hinh;
    private String link;
    private String script;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public BaiNghe(String ten, String hinh, String link, String script) {
        this.ten = ten;
        this.hinh = hinh;
        this.link = link;
        this.script = script;
    }

    public BaiNghe() {
    }
}
