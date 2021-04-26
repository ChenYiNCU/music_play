package com.mtype.controller;

import com.mtype.biz.mTypeBiz;
import com.mtype.entity.mType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("mtype")
@CrossOrigin(origins = "http://localhost:8081",maxAge = 3600)
public class mTypeController {
    @Autowired
    private mTypeBiz mtypeBiz;

    @RequestMapping(value = "/add")
    @ResponseBody
    public String add(String tname){
        mType type=new mType();
        type.setTname(tname);
        mtypeBiz.add(type);
        return "添加成功";
    }

    @RequestMapping(value = "/findAll")
    @ResponseBody
    public List<mType> findAll(){
        List list = mtypeBiz.findAll();
        return list;
    }

    @RequestMapping(value = "/findById")
    @ResponseBody
    public mType findById(int t_id){
        return mtypeBiz.findById(t_id);
    }

    @RequestMapping(value = "/findByName")
    @ResponseBody
    public Boolean findByName(String tname){
        int count=mtypeBiz.findByName(tname);
        if(count!=0){
            return true;//存在
        }else {
            return false;
        }
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public String update(int t_id,String tname){
        mType type=new mType(t_id,tname);
        mtypeBiz.update(type);
        return "修改成功";
    }
}
