package com.muser.biz;

import com.muser.entity.mUser;

import java.util.List;

public interface mUserBiz {
//    王晏
    public List<mUser> findAllSinger();
    public List<mUser> findPopSingerPage(int index,int size);
    public int singerNum();
    public List<mUser> searchByName(String uname);

//    董丽荣
public mUser login(String tel, String upwd);
    public mUser findByTel(String tel);
    public void regist(mUser user);
    public List<mUser> findAll();
    public List<mUser> findPage(int flag,int index,int size);
    public int countByFlag(int flag);
    public void del(int u_id);
    public mUser findById(int u_id);
    public void update(mUser user);
    public void addUser(mUser user);
    public void examine(int u_id,int flag);
    public void freeze(int u_id,int flag);
    public List<mUser> findByFlag(int flag);
    public List<mUser> findSearchPage(int flag,int index,int size,String search);
    public int countBySearch(int flag,String search);
    public int countFans(int u_id);
    public int countFollows(int u_id);
    public List<mUser> findFans(int u_id);
    public List<mUser> findFollows(int u_id);
    public mUser checkFollow(int nowid,int checkid);
    public void edit(mUser user);
    public void follow(int u_id,int fid);
    public void noFollow(int u_id,int fid);
}
