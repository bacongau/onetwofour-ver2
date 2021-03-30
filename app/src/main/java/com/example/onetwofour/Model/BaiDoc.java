package com.example.onetwofour.Model;

public class BaiDoc {
    private String tenbaidoc;
    private byte[] hinh;
    private String engsub;
    private String vietsub;

    public BaiDoc() {
    }

    public BaiDoc(String tenbaidoc, byte[] hinh, String engsub, String vietsub) {
        this.tenbaidoc = tenbaidoc;
        this.hinh = hinh;
        this.engsub = engsub;
        this.vietsub = vietsub;
    }

    public String getTenbaidoc() {
        return tenbaidoc;
    }

    public void setTenbaidoc(String tenbaidoc) {
        this.tenbaidoc = tenbaidoc;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }

    public String getEngsub() {
        return engsub;
    }

    public void setEngsub(String engsub) {
        this.engsub = engsub;
    }

    public String getVietsub() {
        return vietsub;
    }

    public void setVietsub(String vietsub) {
        this.vietsub = vietsub;
    }
}
