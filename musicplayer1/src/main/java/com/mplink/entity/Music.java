package com.mplink.entity;

import com.mtype.entity.mType;
import com.muser.entity.mUser;

import java.sql.Date;

public class Music {
    private int mid;
    private String mname;
    private mUser singer=new mUser();   //歌手
    private mType mtype=new mType();     //歌曲类型
    private Date time;
    private String words;
    private mUser uploader=new mUser();   //上传用户
    private String murl;
    private String mvurl;
    private String mphoto;
    private int playnum;
    private int likenum;
    private int storenum;

    public Music() {
    }

    public Music(int mid, String mname, mUser singer, mType mtype, Date time, String words, mUser uploader, String murl, String mvurl, String mphoto, int playnum, int likenum, int storenum) {
        this.mid = mid;
        this.mname = mname;
        this.singer = singer;
        this.mtype = mtype;
        this.time = time;
        this.words = words;
        this.uploader = uploader;
        this.murl = murl;
        this.mvurl = mvurl;
        this.mphoto = mphoto;
        this.playnum = playnum;
        this.likenum = likenum;
        this.storenum = storenum;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public mUser getSinger() {
        return singer;
    }

    public void setSinger(mUser singer) {
        this.singer = singer;
    }

    public mType getMtype() {
        return mtype;
    }

    public void setMtype(mType mtype) {
        this.mtype = mtype;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public mUser getUploader() {
        return uploader;
    }

    public void setUploader(mUser uploader) {
        this.uploader = uploader;
    }

    public String getMurl() {
        return murl;
    }

    public void setMurl(String murl) {
        this.murl = murl;
    }

    public String getMvurl() {
        return mvurl;
    }

    public void setMvurl(String mvurl) {
        this.mvurl = mvurl;
    }

    public String getMphoto() {
        return mphoto;
    }

    public void setMphoto(String mphoto) {
        this.mphoto = mphoto;
    }

    public int getPlaynum() {
        return playnum;
    }

    public void setPlaynum(int playnum) {
        this.playnum = playnum;
    }

    public int getLikenum() {
        return likenum;
    }

    public void setLikenum(int likenum) {
        this.likenum = likenum;
    }

    public int getStorenum() {
        return storenum;
    }

    public void setStorenum(int storenum) {
        this.storenum = storenum;
    }
}
