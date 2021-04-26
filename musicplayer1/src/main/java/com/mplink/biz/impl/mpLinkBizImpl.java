package com.mplink.biz.impl;

import com.eva.entity.Eva;
import com.mplink.biz.mpLinkBiz;
import com.mplink.entity.Music;
import com.mplink.mapper.mpLinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("mplinkBiz")
public class mpLinkBizImpl implements mpLinkBiz {
    @Autowired
    private mpLinkMapper mplinkMapper;

    @Override
    public List<Music> findAllMusic(int index,int size) {
        Map<String,Object> map=new HashMap<>();
        map.put("index",index);
        map.put("size",size);
        List<Music> list=mplinkMapper.findAllMusic(map);
        return list;
    }

    @Override
    public int sum() {
        return mplinkMapper.sum();
    }

    @Override
    public Music findMusicById(int mid) {
        System.out.println("查到一个音乐");
        return mplinkMapper.findMusicById(mid);
    }

    @Override
    public List<Eva> findEva(int mid,int index,int size) {
        Map<String,Object> map=new HashMap<>();
        map.put("mid",mid);
        map.put("index",index);
        map.put("size",size);
        System.out.println("size:"+size);
        System.out.println("index:"+index);
        return mplinkMapper.findEva(map);
    }

    @Override
    public int findEvaNum(int mid) {
        return mplinkMapper.findEvaNum(mid);
    }

    @Override
    public int isStar(int mid, int pid) {
        return mplinkMapper.isStar(mid, pid);
    }

    @Override
    public void star(int mid, int pid) {
        mplinkMapper.star(mid, pid);
    }

    @Override
    public void starNumUp(int mid) {
        mplinkMapper.starNumUp(mid);
    }

    @Override
    public void starNumDone(int mid) {
        mplinkMapper.starNumDone(mid);
    }

    @Override
    public void noStar(int mid, int pid) {
        mplinkMapper.noStar(mid, pid);
    }


    @Override
    public int findPid(int u_id) {
        System.out.println("得到u_id"+u_id);
        int pid=mplinkMapper.findPid(u_id);
        System.out.println("得到pid"+pid);
        return pid;
    }

    @Override
    public int isLike(int u_id, int mid) {
        return mplinkMapper.isLike(u_id, mid);
    }

    @Override
    public void like(int u_id, int mid) {
        mplinkMapper.like(u_id, mid);
    }

    @Override
    public void likeNumUp(int mid) {
        mplinkMapper.likeNumUp(mid);
    }

    @Override
    public void likeNumDone(int mid) {
mplinkMapper.likeNumDone(mid);
    }

    @Override
    public void noLike(int u_id, int mid) {
        mplinkMapper.noLike(u_id, mid);

    }

    @Override
    public void deleteMusic(int mid) {
        mplinkMapper.deleteMusic(mid);
    }

    @Override
    public List<Music> searchMusic(String search,int index,int size) {
        Map<String,Object> map=new HashMap<>();
        map.put("search",search);
        map.put("index",index);
        map.put("size",size);
        return mplinkMapper.searchMusic(map);
    }

    @Override
    public int searchAll(String search) {
        return mplinkMapper.searchAll(search);
    }

    @Override
    public void updateMusic(Music m) {
        mplinkMapper.updateMusic(m);
    }

    @Override
    public int isRecent(int u_id, int mid) {
        return mplinkMapper.isRecent(u_id, mid);
    }

    @Override
    public void addRecent(int u_id, int mid) {
        mplinkMapper.addRecent(u_id, mid);
    }

    @Override
    public void playNumUp(int mid) {
        mplinkMapper.playNumUp(mid);
    }

//    王晏

    @Override
    public void add(Music music) {
        mplinkMapper.add(music);
    }

    @Override
    public List<Music> findPage(int index, int size) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("index",index);
        map.put("size",size);
        return mplinkMapper.findPage(map);
    }

    @Override
    public List<Music> findOrderLike(int index, int size) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("index",index);
        map.put("size",size);
        return mplinkMapper.findOrderLike(map);
    }

    @Override
    public List<Music> findOrderStore(int index, int size) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("index",index);
        map.put("size",size);
        return mplinkMapper.findOrderStore(map);
    }

    @Override
    public List<Music> findOrderPlay(int index, int size) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("index",index);
        map.put("size",size);
        return mplinkMapper.findOrderPlay(map);
    }

    @Override
    public List<Music> findOrderTime(int index, int size) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("index",index);
        map.put("size",size);
        return mplinkMapper.findOrderTime(map);
    }

    @Override
    public List<Music> findMvStore(int index, int size) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("index",index);
        map.put("size",size);
        return mplinkMapper.findMvStore(map);
    }

    @Override
    public List<Music> findSearchPage(int index, int size,int t_id, String search) {
        Map<String,Object> map=new HashMap<>();
        map.put("index",index);
        map.put("size",size);
        map.put("t_id",t_id);
        map.put("search",search);
        return mplinkMapper.findSearchPage(map);
    }

    @Override
    public int mCount() {
        return mplinkMapper.mCount();
    }

    @Override
    public int mvCount() {
        return mplinkMapper.mvCount();
    }

    @Override
    public int countBySearch(int t_id,String search) {
        Map<String,Object> map=new HashMap<>();
        map.put("search",search);
        map.put("t_id",t_id);
        return mplinkMapper.countBySearch(map);
    }

    /*董丽荣*/
    @Override
    public List<Music> findRecent(int u_id) {
        return mplinkMapper.findRecent(u_id);
    }

    @Override
    public List<Music> findPlaylist(int u_id) {
        return mplinkMapper.findPlaylist(u_id);
    }

    @Override
    public List<Music> findLikelist(int u_id) {
        return mplinkMapper.findLikelist(u_id);
    }
}
