package com.muser.entity;

public class mUser {
    private int u_id;
    private String uname;
    private String upwd;
    private int age;
    private String sex;
    private String tel;
    private String email;
    private String des;
    private String uphoto;
    private int flag;   //判断是否为歌手
    private int fansNum;

    public mUser() {
    }

    public mUser(int u_id, String uname, String upwd, int age, String sex, String tel, String email, String des, String uphoto, int flag, int fansNum) {
        this.u_id = u_id;
        this.uname = uname;
        this.upwd = upwd;
        this.age = age;
        this.sex = sex;
        this.tel = tel;
        this.email = email;
        this.des = des;
        this.uphoto = uphoto;
        this.flag = flag;
        this.fansNum = fansNum;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getUphoto() {
        return uphoto;
    }

    public void setUphoto(String uphoto) {
        this.uphoto = uphoto;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }
}
