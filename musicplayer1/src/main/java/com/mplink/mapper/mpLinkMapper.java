package com.mplink.mapper;

import com.eva.entity.Eva;
import com.mplink.entity.Music;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface mpLinkMapper {
//    陈益
    public List<Music> findAllMusic(Map<String,Object> map);
    public int sum();
    public Music findMusicById(int mid);
    public List<Eva>  findEva(Map<String,Object> map);
    public int findEvaNum(int mid);  //查找评论数
    //收藏
    public int isStar(@Param("mid") int mid,@Param("pid") int pid);
    public void star(@Param("mid") int mid,@Param("pid") int pid);   //传歌id和歌单id给mplink表
    public void starNumUp(int mid);
    public void starNumDone(int mid);    //歌曲收藏数-1
    public void noStar(@Param("mid") int mid,@Param("pid") int pid);
    //喜欢
    public int findPid(int u_id);
    public int isLike(@Param("u_id") int u_id,@Param("mid") int mid);
    public void like(@Param("u_id") int u_id,@Param("mid") int mid);
    public void likeNumUp(int mid);
    public void likeNumDone(int mid); //歌曲喜爱数-1
    public void noLike(@Param("u_id") int u_id,@Param("mid") int mid);
    public void deleteMusic(int mid);    //删除歌
    public  List<Music> searchMusic(Map<String,Object> map);
    public int searchAll(String search);
    public void updateMusic(Music m);
    public int isRecent(@Param("u_id") int u_id,@Param("mid") int mid);     //查询该记录是否已经存在
    public void addRecent(@Param("u_id") int u_id,@Param("mid") int mid);     //向最近播放表加歌
    public void playNumUp(int mid);
//    王晏
    public void add(Music music);
    public List<Music> findPage(Map<String,Object> map);
    public List<Music> findOrderLike(Map<String,Object> map);
    public List<Music> findOrderStore(Map<String,Object> map);
    public List<Music> findOrderPlay(Map<String,Object> map);
    public List<Music> findOrderTime(Map<String,Object> map);
    public List<Music> findMvStore(Map<String,Object> map);
    public List<Music> findSearchPage(Map<String,Object> map);
    public int mCount();
    public int mvCount();
    public int countBySearch(Map<String,Object> map);
/*董丽荣*/
/*查询最近播放*/
public List<Music> findRecent(int u_id);
    /*查询我的收藏*/
    public List<Music> findPlaylist(int u_id);
    /*查询我的喜欢*/
    public List<Music> findLikelist(int u_id);
}
