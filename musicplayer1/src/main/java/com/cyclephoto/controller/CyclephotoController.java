package com.cyclephoto.controller;

import com.cyclephoto.biz.CyclephotoBiz;
import com.cyclephoto.entity.CyclePhoto;
import com.mtype.entity.mType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("cyclephoto")
@CrossOrigin(origins = "http://localhost:8081",maxAge = 3600)
public class CyclephotoController {
    @Autowired
    private CyclephotoBiz cyclephotoBiz;

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(int cpid){
        cyclephotoBiz.delete(cpid);
        return "删除成功";
    }

    @RequestMapping(value = "/findAll")
    @ResponseBody
    public List<CyclePhoto> findAll(){
        List list = cyclephotoBiz.findAll();
        return list;
    }

    @RequestMapping(value = "/findById")
    @ResponseBody
    public CyclePhoto findById(int cpid){
        return cyclephotoBiz.findById(cpid);
    }


    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(Integer cpid,String cpurl){
        CyclePhoto cyclePhoto=new CyclePhoto();
        cyclePhoto.setCpurl(cpurl);
        if (cpid==null){
            cpid=1;
            cyclephotoBiz.add(cyclePhoto);
        }else{
            cyclePhoto.setCpid(cpid);
            cyclephotoBiz.update(cyclePhoto);
        }
        return "成功";
    }
}
