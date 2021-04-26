package com.muser.controller;

import com.muser.biz.mUserBiz;
import com.muser.entity.mUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("muser")
@CrossOrigin(origins = "http://localhost:8081",maxAge = 3600)
public class mUserController {
    @Autowired
    private mUserBiz muserBiz;

//王晏
    @RequestMapping(value = "/findAllSinger")
    @ResponseBody
    public List<mUser> findAllSinger(){
        return muserBiz.findAllSinger();
    }

    @RequestMapping(value = "/findPopSingerPage")
    @ResponseBody
    public Map<String,Object> findPopSingerPage(Integer index){
        if(index==null) { index = 1; }
        int size=8;
        List<mUser> list=muserBiz.findPopSingerPage(index,size);
        int count=muserBiz.singerNum();
        int total = count%size == 0?count/size:count/size+1;
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list",list);
        map.put("index",index);
        map.put("count",count);
        return map;
    }

    @RequestMapping(value = "/searchByName")
    @ResponseBody
    public List<mUser> searchByName(String uname){
        return  muserBiz.searchByName(uname);
    }

    private  final static String rootPath = "H:/大三下/音乐网站/music/public/img/";
    @RequestMapping("/upload")
    @ResponseBody
    public String uploadFile(MultipartFile[] multipartFiles){
        File fileDir = new File(rootPath);
        if (!fileDir.exists() && !fileDir.isDirectory()) {
            fileDir.mkdirs();
        }
        try {
            System.out.println(multipartFiles.length);
            if (multipartFiles != null && multipartFiles.length > 0) {
                for(int i = 0;i<multipartFiles.length;i++){
                    try {
                        //以原来的名称命名,覆盖掉旧的，这里也可以使用UUID之类的方式命名，这里就没有处理了
                        String storagePath = rootPath+multipartFiles[i].getOriginalFilename();
//                        System.out.println("上传的文件：" + multipartFiles[i].getName() + "," + multipartFiles[i].getContentType() + "," + multipartFiles[i].getOriginalFilename()
//                                +"，保存的路径为：" + storagePath);
                        multipartFiles[i].transferTo(new File(storagePath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "文件上传成功";
    }


    private  final static String rootPath1 = "H:/大三下/音乐网站/music/public/music/";
    @RequestMapping("/upload2")
    @ResponseBody
    public String upload2File(MultipartFile[] multipartFiles,String path){

        File fileDir = new File(rootPath1+path);
        if (!fileDir.exists() && !fileDir.isDirectory()) {
            fileDir.mkdirs();
        }
        try {
            System.out.println(multipartFiles.length);
            if (multipartFiles != null && multipartFiles.length > 0) {
                for(int i = 0;i<multipartFiles.length;i++){
                    try {
                        String storagePath = rootPath1+path+multipartFiles[i].getOriginalFilename();
                        multipartFiles[i].transferTo(new File(storagePath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "文件上传成功";
    }



//    董丽荣

    @RequestMapping(value = "/login")
    @ResponseBody
    public Map<String,Object> login(String tel, String upwd,String checkCode){
        Map<String,Object> map=new HashMap<String,Object>();
        int flag=-1;      //登陆信息状态
       /* int u_flag=-1;   //登陆账号状态
        String uname=null;*/
        mUser muser1=null;
        muser1=muserBiz.findByTel(tel);   //按手机号查找是否存在
        mUser muser=null;
        if(checkCode.toLowerCase().equals("aaaa")){  //验证码正确
            if(muser1!=null){  //账号存在可以进行登陆
                muser= muserBiz.login(tel, upwd);  //登陆时，验证手机号密码
                if(muser!=null){
                    flag=3;
                   /* u_flag=muser.getFlag();    //用于判断当前账号状态
                    uname=muser.getUname();    //当前用户名称*/
                     /*if (u_flag==1 || u_flag==2){  //当前账号为歌手或普通用户，可以登陆
                        flag=3;
                    }else if(u_flag==0){   //当前账号处于审核状态，不可登陆
                        flag=4;
                    }else if(u_flag==3){    //当前账号处于封禁状态，不可登陆
                        flag=5;
                    }else{
                        flag=6;      //账号异常,不可登陆
                    }*/
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
        map.put("muser",muser);
        return map;
    }

    @RequestMapping(value = "/regist")
    @ResponseBody
    public String regist(String tel,String upwd,String uname,String email,int age,String sex,String des,String uphoto){
        mUser user=new mUser();
        user.setTel(tel);
        user.setUpwd(upwd);
        user.setUname(uname);
        user.setEmail(email);
        user.setAge(age);
        user.setSex(sex);
        user.setDes(des);
        user.setUphoto(uphoto);
        muserBiz.regist(user);
        return "success";
    }

    @RequestMapping("/findTel")
    @ResponseBody
    public String findTel(String tel){
        mUser user=muserBiz.findByTel(tel);
        if(user ==null ){ //不存在
            return "a";
        }else {  //存在返回手机号
            return user.getTel();
        }
    }

    @RequestMapping("/findSingerByTel")
    @ResponseBody
    public mUser findSingerByTel(String tel){
        return muserBiz.findByTel(tel);
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public Map<String,Object> findAll(){
        List<mUser> list=muserBiz.findAll();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("list",list);
        return map;
    }

    @RequestMapping("/findPage")
    @ResponseBody
    public Map<String,Object> findPage(Integer flag,Integer index,Integer size){
        if(index==null){
            index=1;
        }
        int count=muserBiz.countByFlag(flag);
        //int total=count%size==0?count/size:count/size+1;
        List<mUser> list=muserBiz.findPage(flag,index,size);
        Map<String,Object> map=new HashMap<>();
        map.put("list",list);
        map.put("index",index);
        //map.put("total",total);
        map.put("count",count);
        return map;
    }

    @RequestMapping("/findSearchPage")
    @ResponseBody
    public Map<String,Object> findSearchPage(Integer flag,Integer index,Integer size,String search){
        if(index==null){
            index=1;
        }
        int count=muserBiz.countBySearch(flag,search);
        //int total=count%size==0?count/size:count/size+1;
        List<mUser> list=muserBiz.findSearchPage(flag,index,size,search);
        Map<String,Object> map=new HashMap<>();
        map.put("list",list);
        map.put("index",index);
        //map.put("total",total);
        map.put("count",count);
        //System.out.println(list);
        return map;
    }

    @RequestMapping("/del")
    @ResponseBody
    public String del(int u_id){
        muserBiz.del(u_id);
        return "success";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update(int u_id,String tel,String upwd,String uname,String email,int age,String sex,String des,String uphoto){
        mUser user=new mUser();
        user.setU_id(u_id);
        user.setTel(tel);
        user.setUpwd(upwd);
        user.setUname(uname);
        user.setEmail(email);
        user.setAge(age);
        user.setSex(sex);
        user.setDes(des);
        user.setUphoto(uphoto);
        muserBiz.update(user);
        return "success";
    }

    @RequestMapping("/add")
    @ResponseBody
    public String addUser(String tel,String upwd,String uname,String email,int age,String sex,String des,int flag,String uphoto){
        /*if (!file.isEmpty()){
            String fileName=file.getOriginalFilename();
            System.out.println(fileName);
            String path=request.getServletContext().getRealPath("/face/");
            System.out.println(path);
            File path1=new File(path,fileName);
            file.transferTo(path1);
            url="img/"+fileName;
            System.out.println(url);
        }*/
        //System.out.println(tel);
        mUser user=new mUser();
        user.setTel(tel);
        user.setUpwd(upwd);
        user.setUname(uname);
        user.setEmail(email);
        user.setAge(age);
        user.setSex(sex);
        user.setDes(des);
        user.setFlag(flag);
        user.setUphoto(uphoto);
        //user.setUphoto(null);
        muserBiz.addUser(user);
        return "success";
    }

    @RequestMapping("/examine")
    @ResponseBody
    public String examine(int u_id){
        int flag=1;  //通过审核 将flag修改为1
        muserBiz.examine(u_id,flag);
        return "success";
    }

    @RequestMapping("/freeze")
    @ResponseBody
    public String freeze(int u_id){
        int flag=3;  //封锁账号 将flag修改为3
        muserBiz.freeze(u_id,flag);
        return "success";
    }

    @RequestMapping("/findByFlag")
    @ResponseBody
    public Map<String,Object> findByFlag(int flag){
        Map<String,Object> map= new HashMap<>();
        int count=muserBiz.countByFlag(flag);
        List<mUser> list=muserBiz.findByFlag(flag);
        map.put("list",list);
        map.put("count",count);
        return map;
    }

    @RequestMapping("/findById")
    @ResponseBody
    public mUser findById(int u_id){
        return muserBiz.findById(u_id);
    }

    @RequestMapping("/findFF")
    @ResponseBody
    public Map<String,Object> countNum(int u_id){
        int fans=muserBiz.countFans(u_id);
        int follows=muserBiz.countFollows(u_id);
        List<mUser> fansList=muserBiz.findFans(u_id);
        List<mUser> followsList=muserBiz.findFollows(u_id);
        Map<String,Object> map=new HashMap<>();
        map.put("fans",fans);
        map.put("follows",follows);
        map.put("fansList",fansList);
        map.put("followsList",followsList);
        return map;
    }

    @RequestMapping("/checkFollow")
    @ResponseBody
    public mUser checkFollow(int nowid,int checkid){
        return muserBiz.checkFollow(nowid,checkid);
    }

    @RequestMapping("/edit")
    @ResponseBody
    public String update(int u_id,String tel,String uname,String email,int age,String sex,String des,String uphoto){
        mUser user=new mUser();
        user.setU_id(u_id);
        user.setTel(tel);
        user.setUname(uname);
        user.setEmail(email);
        user.setAge(age);
        user.setSex(sex);
        user.setDes(des);
        user.setUphoto(uphoto);
        muserBiz.edit(user);
        return "success";
    }

    @RequestMapping("/follow")
    @ResponseBody
    public String follow(int u_id,int fid){
        muserBiz.follow(u_id, fid);
        return "success";
    }

    @RequestMapping("/noFollow")
    @ResponseBody
    public String noFollow(int u_id,int fid){
        muserBiz.noFollow(u_id, fid);
        return "success";
    }
}
