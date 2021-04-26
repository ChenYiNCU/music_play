package com.eva.entity;

import com.muser.entity.mUser;
import com.mplink.entity.Music;

import java.sql.Date;

public class Eva {
    private Music music=new Music();
    private int eid;
    private Date etime;
    private String content;
    private mUser speaker=new mUser();
    private int rdisid;
    private Date rtime;
    private String rdisname;
    private String rcontent;
    private String rphoto;

    public Date getRtime() {
        return rtime;
    }

    public void setRtime(Date rtime) {
        this.rtime = rtime;
    }

    public int getRdisid() {
        return rdisid;
    }

    public void setRdisid(int rdisid) {
        this.rdisid = rdisid;
    }

    public String getRdisname() {
        return rdisname;
    }

    public void setRdisname(String rdisname) {
        this.rdisname = rdisname;
    }

    public String getRcontent() {
        return rcontent;
    }

    public void setRcontent(String rcontent) {
        this.rcontent = rcontent;
    }



    public Eva() {
    }

    public Eva(int eid, Date etime, String content, mUser speaker, int rdisid, Date rtime, String rdisname, String rcontent, Music music, String rphoto) {
        this.eid = eid;
        this.etime = etime;
        this.content = content;
        this.speaker = speaker;
        this.rdisid = rdisid;
        this.rtime = rtime;
        this.rdisname = rdisname;
        this.rcontent = rcontent;
        this.music = music;
        this.rphoto = rphoto;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public Date getEtime() {
        return etime;
    }

    public void setEtime(Date etime) {
        this.etime = etime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public mUser getSpeaker() {
        return speaker;
    }

    public void setSpeaker(mUser speaker) {
        this.speaker = speaker;
    }



    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public String getRphoto() {
        return rphoto;
    }

    public void setRphoto(String rphoto) {
        this.rphoto = rphoto;
    }
}
