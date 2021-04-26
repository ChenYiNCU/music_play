package com.cyclephoto.entity;

public class CyclePhoto {
    private int cpid;
    private String cpurl;

    public CyclePhoto() {
    }

    public CyclePhoto(int cpid, String cpurl) {
        this.cpid = cpid;
        this.cpurl = cpurl;
    }

    public int getCpid() {
        return cpid;
    }

    public void setCpid(int cpid) {
        this.cpid = cpid;
    }

    public String getCpurl() {
        return cpurl;
    }

    public void setCpurl(String cpurl) {
        this.cpurl = cpurl;
    }
}
