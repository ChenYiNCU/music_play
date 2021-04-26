package com.mplink.entity;

import com.muser.entity.mUser;

public class Playlist {
    private int pid;
    private mUser creater=new mUser();
    private String pname;

    public Playlist() {
    }

    public Playlist(int pid, mUser creater, String pname) {
        this.pid = pid;
        this.creater = creater;
        this.pname = pname;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public mUser getCreater() {
        return creater;
    }

    public void setCreater(mUser creater) {
        this.creater = creater;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
