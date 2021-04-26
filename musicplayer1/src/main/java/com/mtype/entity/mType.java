package com.mtype.entity;

public class mType {
    private int t_id;
    private String tname;

    public mType() {
    }

    public mType(int t_id, String tname) {
        this.t_id = t_id;
        this.tname = tname;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }
}
