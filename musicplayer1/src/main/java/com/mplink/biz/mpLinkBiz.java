package com.mplink.biz;

import com.eva.entity.Eva;
import com.mplink.entity.Music;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface mpLinkBiz {
//    陈益
    public List<Music> findAllMusic(int index,int size);
    public int sum();
    public Music findMusicById(int mid);
    public List<Eva> findEva(int mid,int index,int size);
    public int findEvaNum(int mid);
    public int isStar(int mid,int pid);
    public void star(int mid,int pid);
    public void starNumUp(int mid);      //歌曲收藏数+1
    public void starNumDone(int mid);    //歌曲收藏数-1
    public void noStar(int mid,int pid);
    public int findPid(int u_id);
    public int isLike(int u_id,int mid);
    public void like(int u_id,int mid);
    public void likeNumUp(int mid);  //歌曲喜爱数+1
    public void likeNumDone(int mid); //歌曲喜爱数-1
    public void noLike(int u_id,int mid);
    public void deleteMusic(int mid);    //删除歌
    public  List<Music> searchMusic(String search,int index,int size);
    public int searchAll(String search);   //查找有几条
    public void updateMusic(Music m);
    public int isRecent(int u_id,int mid);     //查询该记录是否已经存在
    public void addRecent(int u_id,int mid);     //向最近播放表加歌
    public void playNumUp(int mid);
//    王晏
public void add(Music music);
    public List<Music> findPage(int index,int size);
    public List<Music> findOrderLike(int index,int size);
    public List<Music> findOrderStore(int index,int size);
    public List<Music> findOrderPlay(int index,int size);
    public List<Music> findOrderTime(int index,int size);
    public List<Music> findMvStore(int index,int size);
    public List<Music> findSearchPage(int index,int size,int t_id,String search);
    public int mCount();
    public int mvCount();
    public int countBySearch(int t_id,String search);

/*董丽荣*/
public List<Music> findRecent(int u_id);
    public List<Music> findPlaylist(int u_id);
    public List<Music> findLikelist(int u_id);
}
