package com.example.onetwofour.Model;

public class BaiNghe {
    private String ten;
    private byte[] hinh;
    private String link;
    private String script;

    public BaiNghe(String ten, byte[] hinh, String link, String script) {
        this.ten = ten;
        this.hinh = hinh;
        this.link = link;
        this.script = script;
    }

    public BaiNghe() {
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
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
}
