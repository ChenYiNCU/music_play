package com.mplink.controller;

import com.eva.entity.Eva;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mplink.biz.mpLinkBiz;
import com.mplink.entity.Music;
import com.mtype.entity.mType;
import com.muser.entity.mUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("mplink")
@CrossOrigin(origins = "http://localhost:8081",maxAge = 3600)
public class mpLinkController {
    @Autowired
    private mpLinkBiz mplinkBiz;



    @RequestMapping(value = "/updateMusic")
    @ResponseBody
    public String updateMusic(int mid,String mphoto,String mname,int t_id,int u_id){
        Music m=new Music();
        m.setMid(mid);
        m.setMphoto(mphoto);
        m.setMname(mname);
        mUser singer=new mUser();
        singer.setU_id(u_id);
        m.setSinger(singer);
        mType type=new mType();
        type.setT_id(t_id);
        m.setMtype(type);
        mplinkBiz.updateMusic(m);
        return "修改成功";
    }


    @RequestMapping(value = "deleteMusic")
    @ResponseBody
    public String deleteMusic(int mid){
        mplinkBiz.deleteMusic(mid);
        return "删除成功";
    }
    @RequestMapping(value = "searchMusic")
    @ResponseBody
    public List<Music> searchMusic(String search,Integer index){
        if (index==null){
            index=1;
        }
        int size=4;
        List<Music> list= mplinkBiz.searchMusic(search,index,size);
        return list;

    }
    @RequestMapping(value = "searchAll")
    @ResponseBody
    public int searchAll(String search) {
        return mplinkBiz.searchAll(search);
    }
    @RequestMapping(value = "findAllMusic")
    @ResponseBody
    public List<Music> findAllMusic(Integer index){
        if (index==null){
            index=1;
        }
        int size=4;

        return mplinkBiz.findAllMusic(index,size);
    }

    @RequestMapping(value = "sum")
    @ResponseBody
    public int sum(){
        return mplinkBiz.sum();
    }
    @RequestMapping(value = "findMusicById")
    @ResponseBody
    public Music findMusicById(int mid){
        System.out.println("得到音乐");
        return mplinkBiz.findMusicById(mid);

    }
    @RequestMapping(value = "findEvaNum")
    @ResponseBody
    public int findEvaNum(int mid){

        return mplinkBiz.findEvaNum(mid);
    }


    @RequestMapping(value = "findEva")
    @ResponseBody
    public Map<Integer,List<Eva>> findEva(int mid,Integer index){
        Map<Integer,List<Eva>> map=new HashMap<>();

        if (index==null)
        {
            index=1;
        }
        int size=4;
        System.out.println("得到当前页："+index);
        //一个eid 对应一个list
        List<Eva> list=mplinkBiz.findEva(mid,index,size);  //得到已经排好序的list
        System.out.println(list.size());
        //遍历它
        int id=list.get(0).getEid();  //得到第一个对象的评论id

        for(Eva a:list){
            List<Eva> list1;
            if(a.getEid()==id){
                list1=map.get(id);
                if(list1==null){
                    list1=new ArrayList<>();
                    list1.add(a);
                }
                else{
                    list1.add(a);
                }

            }
            else{
                list1=new ArrayList<>();
                list1.add(a);
                id=a.getEid();

            }
            map.put(a.getEid(),list1);
            System.out.println("得到map");

            System.out.println(id);
        }
        //System.out.println(map.size());
        return map;
    }

    @RequestMapping(value = "star")
    @ResponseBody
    public void star(int mid, int u_id){

        //查找歌单编号
        int pid=mplinkBiz.findPid(u_id);
        //音乐加入默认歌单
        System.out.println(u_id);
        mplinkBiz.star(mid, pid);
        mplinkBiz.starNumUp(mid);

    }
    @RequestMapping(value = "noStar")
    @ResponseBody
    public void noStar(int mid,int u_id){

        int pid=mplinkBiz.findPid(u_id);

        mplinkBiz.noStar(mid, pid);
        mplinkBiz.starNumDone(mid);
    }
    @RequestMapping(value = "like")
    @ResponseBody
    public void like(int u_id,int mid){
        System.out.println(mid);
        System.out.println(u_id);
        mplinkBiz.like(u_id, mid);
        mplinkBiz.likeNumUp(mid);

    }
    @RequestMapping(value = "noLike")
    @ResponseBody
    public void noLike(int u_id,int mid){
        mplinkBiz.noLike(u_id, mid);
        mplinkBiz.likeNumDone(mid);
    }

    @RequestMapping(value = "isLike_isStar")
    @ResponseBody
    public Map<String,Object> isLike_isStar(int u_id,int mid){
        System.out.println(u_id);
        System.out.println(mid);
        int pid=mplinkBiz.findPid(u_id);
        Map<String,Object> map=new HashMap<>();
        int islike=mplinkBiz.isLike(u_id, mid);
        if (islike==1){   //有一条记录
            map.put("isLike",true);
        }else{
            map.put("isLike",false);
        }

        int isstar= mplinkBiz.isStar(mid,pid);
        if (isstar==1){
            map.put("isStar",true);
        }
        else{
            map.put("isStar",false);
        }
        return map;
    }
    @RequestMapping(value = "addRecent")
    @ResponseBody
    public void  addRecent(int u_id,int mid){
        int isrecent= mplinkBiz.isRecent(u_id, mid);
        if (isrecent==0){   //没有记录就添加
            mplinkBiz.addRecent(u_id, mid);
        }else{

        }
    }
    @RequestMapping(value = "playNumUp")
    @ResponseBody
    public void playNumUp(int mid){
        mplinkBiz.playNumUp(mid);
    }



    /*王晏*/
    @RequestMapping(value = "/add")
    @ResponseBody
    public String add(String mname,int sid,int u_id,int tid,String words,String mphoto,String murl,String mvurl){
        Music music=new Music();
        music.setMname(mname);
        mUser singer=new mUser();
        singer.setU_id(sid);
        music.setSinger(singer);
        mUser uploader=new mUser();
        uploader.setU_id(u_id);
        music.setUploader(uploader);
        mType mtype=new mType();
        mtype.setT_id(tid);
        music.setMtype(mtype);
        music.setWords(words);
        music.setMphoto(mphoto);
        music.setMurl(murl);
        music.setMvurl(mvurl);

        mplinkBiz.add(music);
        return "添加成功";
    }

    @RequestMapping(value = "/findPage")
    @ResponseBody
    public Map<String,Object> findPage(Integer index,Integer size,Integer flag) {
        if(index==null) { index = 1; }
        if (flag==null){ flag=1; }
        if(size==null){size=12;}
        int count=0;
        List<Music> list=new ArrayList<Music>();
        //不排序
        if(flag==1){ list = mplinkBiz.findPage(index, size); }
        //喜爱降序
        else if(flag==2){ list = mplinkBiz.findOrderLike(index, size); }
        //收藏降序
        else if(flag==3){ list = mplinkBiz.findOrderStore(index, size); }
        //收藏MV降序
        else if(flag==4){ list = mplinkBiz.findMvStore(index, size); }
        //时间降序
        else if(flag==5){ list = mplinkBiz.findOrderTime(index, size); }
        //播放降序
        else{ list = mplinkBiz.findOrderPlay(index, size); }

        if(flag==4){
            count = mplinkBiz.mvCount();//mv数量
        }else{
            count = mplinkBiz.mCount();//歌曲数量
        }

        int total = count%size == 0?count/size:count/size+1;
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list",list);
        map.put("index",index);
        map.put("count",count);
        return map;
    }

    @RequestMapping(value = "/findSearchPage")
    @ResponseBody
    public Map<String,Object> findSearchPage(Integer index,Integer size,Integer t_id,String search){
        if(index==null){
            index=1;
        }
        if(t_id==null){
            t_id=-1;
        }
        int count=mplinkBiz.countBySearch(t_id,search);
        //int total=count%size==0?count/size:count/size+1;
        List<Music> list=mplinkBiz.findSearchPage(index,size,t_id,search);
        Map<String,Object> map=new HashMap<>();
        map.put("list",list);
        map.put("index",index);
        map.put("count",count);
        return map;
    }

//    董丽荣

    @RequestMapping("/findRecent")
    @ResponseBody
    public List<Music> findRecent(int u_id){
        return mplinkBiz.findRecent(u_id);
    }

    @RequestMapping("/findPlaylist")
    @ResponseBody
    public List<Music> findPlaylist(int u_id){
        //System.out.println(mplinkBiz.findPlaylist(u_id));
        return mplinkBiz.findPlaylist(u_id);
    }

    @RequestMapping("/findLikelist")
    @ResponseBody
    public List<Music> findLikelist(int u_id){
        //System.out.println(mplinkBiz.findLikelist(u_id));
        return mplinkBiz.findLikelist(u_id);
    }
}
