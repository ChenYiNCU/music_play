package com.madmin.controller;

import com.madmin.biz.mAdminBiz;
import com.madmin.entity.mAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("madmin")
@CrossOrigin(origins = "http://localhost:8081",maxAge = 3600)
public class mAdminController {
    @Autowired
    private mAdminBiz madminBiz;

    @RequestMapping(value = "/login")
    @ResponseBody
    public Map<String,Object> login(String aname,String apwd,String checkCode){
        Map<String,Object> map=new HashMap<String,Object>();
        int flag=-1;      //登陆信息状态
        mAdmin madmin1=null;
        madmin1=madminBiz.findByName(aname);   //按账号查找是否存在
        mAdmin madmin=null;
        if(checkCode.toLowerCase().equals("aaaa")){  //验证码正确
            if(madmin1!=null){  //账号存在可以进行登陆
                madmin= madminBiz.login(aname,apwd);  //登陆时，验证手机号密码
                if(madmin!=null){
                    flag=3;
                }else{          //账户存在，密码错误
                    flag=2;
                }
            }else{   //账号不存在
                flag=1;
            }
        }else{  //验证码错误
            flag=0;
        }
        map.put("flag",flag);
        map.put("madmin",madmin);
        return map;
    }
}
