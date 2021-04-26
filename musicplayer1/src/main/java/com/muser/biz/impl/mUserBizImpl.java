package com.muser.biz.impl;

import com.muser.biz.mUserBiz;
import com.muser.entity.mUser;
import com.muser.mapper.mUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("muserBiz")
public class mUserBizImpl implements mUserBiz {
    @Autowired
    private mUserMapper muserMapper;
//王晏
    @Override
    public List<mUser> findAllSinger() {
        return muserMapper.findAllSinger();
    }

    @Override
    public List<mUser> findPopSingerPage(int index,int size) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("index",index);
        map.put("size",size);
        return muserMapper.findPopSingerPage(map);
    }

    @Override
    public int singerNum() {
        return muserMapper.singerNum();
    }

    @Override
    public List<mUser> searchByName(String uname) {
        return muserMapper.searchByName(uname);
    }


    //    董丽荣
    @Override
    public mUser login(String tel, String upwd) {
        return muserMapper.login(tel, upwd);
    }

    @Override
    public mUser findByTel(String tel) {
        return muserMapper.findByTel(tel);
    }

    @Override
    public void regist(mUser user) {
        muserMapper.regist(user);
    }

    @Override
    public List<mUser> findAll() {
        return muserMapper.findAll();
    }

    @Override
    public List<mUser> findPage(int flag, int index, int size) {
        Map<String,Object> map=new HashMap<>();
        map.put("flag",flag);
        map.put("index",index);
        map.put("size",size);
        return muserMapper.findPage(map);
    }

    @Override
    public int countByFlag(int flag) {
        return muserMapper.countByFlag(flag);
    }

    @Override
    public void del(int u_id) {
        muserMapper.del(u_id);
    }

    @Override
    public mUser findById(int u_id) {
        return muserMapper.findById(u_id);
    }

    @Override
    public void update(mUser user) {
        muserMapper.update(user);
    }

    @Override
    public void addUser(mUser user) {
        muserMapper.addUser(user);
    }

    @Override
    public void examine(int u_id, int flag) {
        Map<String,Object> map=new HashMap<>();
        map.put("u_id",u_id);
        map.put("flag",flag);
        muserMapper.examine(map);
    }

    @Override
    public void freeze(int u_id, int flag) {
        Map<String,Object> map=new HashMap<>();
        map.put("u_id",u_id);
        map.put("flag",flag);
        muserMapper.freeze(map);
    }

    @Override
    public List<mUser> findByFlag(int flag) {
        return muserMapper.findByFlag(flag);
    }

    @Override
    public List<mUser> findSearchPage(int flag, int index, int size, String search) {
        Map<String,Object> map=new HashMap<>();
        map.put("flag",flag);
        map.put("index",index);
        map.put("size",size);
        map.put("search",search);
        return muserMapper.findSearchPage(map);
    }

    @Override
    public int countBySearch(int flag, String search) {
        Map<String,Object> map=new HashMap<>();
        map.put("flag",flag);
        map.put("search",search);
        return muserMapper.countBySearch(map);
    }

    @Override
    public int countFans(int u_id) {
        return muserMapper.countFans(u_id);
    }

    @Override
    public int countFollows(int u_id) {
        return muserMapper.countFollows(u_id);
    }

    @Override
    public List<mUser> findFans(int u_id) {
        return muserMapper.findFans(u_id);
    }

    @Override
    public List<mUser> findFollows(int u_id) {
        return muserMapper.findFollows(u_id);
    }

    @Override
    public mUser checkFollow(int nowid, int checkid) {
        Map<String,Object> map=new HashMap<>();
        map.put("nowid",nowid);
        map.put("checkid",checkid);
        return muserMapper.checkFollow(map);
    }

    @Override
    public void edit(mUser user) {
        muserMapper.edit(user);
    }

    @Override
    public void follow(int u_id, int fid) {
        muserMapper.follow(u_id,fid);
    }

    @Override
    public void noFollow(int u_id, int fid) {
        muserMapper.noFollow(u_id,fid);
    }
}
