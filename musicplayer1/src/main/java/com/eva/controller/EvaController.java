package com.eva.controller;

import com.eva.biz.EvaBiz;
import com.eva.entity.Eva;
import com.mplink.entity.Music;
import com.muser.entity.mUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;

@Controller
@RequestMapping("eva")
@CrossOrigin(origins = "http://localhost:8081",maxAge = 3600)
public class EvaController {
    @Autowired
    private EvaBiz evaBiz;

    @RequestMapping(value = "eva")
    @ResponseBody
    public String eva(int disid,String disname,int mid,String content){
        System.out.println("评论者id:"+disid);
        Eva e=new Eva();
        Music m=new Music();
        mUser u=new mUser();
        u.setU_id(disid);
        u.setUname(disname);
        System.out.println(u);
        m.setMid(mid);
        e.setContent(content);
        e.setMusic(m);
        e.setSpeaker(u);
        evaBiz.eva(e);
        return "评论成功";
    }
    @RequestMapping(value = "reva")
    @ResponseBody
    public String reva(int eid,int rdisid,String rdisname,String rcontent){
        Eva e=new Eva();
        e.setEid(eid);
        e.setRdisid(rdisid);
        e.setRdisname(rdisname);
        e.setRcontent(rcontent);
        evaBiz.reva(e);
        return "回复成功";
    }
}
