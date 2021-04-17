package com.example.onetwofour.Model;

public class Deck {
    private int id;
    private byte[] imgword;
    private byte[] imgavatar;
    private byte[] imgfacedown;
    private int status;

    public Deck(int id, byte[] imgword, byte[] imgavatar, byte[] imgfacedown, int status) {
        this.id = id;
        this.imgword = imgword;
        this.imgavatar = imgavatar;
        this.imgfacedown = imgfacedown;
        this.status = status;
    }

    public Deck() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImgword() {
        return imgword;
    }

    public void setImgword(byte[] imgword) {
        this.imgword = imgword;
    }

    public byte[] getImgavatar() {
        return imgavatar;
    }

    public void setImgavatar(byte[] imgavatar) {
        this.imgavatar = imgavatar;
    }

    public byte[] getImgfacedown() {
        return imgfacedown;
    }

    public void setImgfacedown(byte[] imgfacedown) {
        this.imgfacedown = imgfacedown;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
