package com.muser.mapper;

import com.muser.entity.mUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface mUserMapper {
//    王晏
    public List<mUser> findAllSinger();
    public List<mUser> findPopSingerPage(Map<String,Object> map);
    public int singerNum();
    /* 通过名字找歌手 */
    public List<mUser> searchByName(String uname);

//    董丽荣
/*登陆 手机号+密码*/
public mUser login(@Param("tel") String tel, @Param("upwd") String upwd);
    /*登陆时验证 查找手机号*/
    public mUser findByTel(String tel);
    /*注册会员*/
    public void regist(mUser user);
    /*查询所有用户*/
    public List<mUser> findAll();
    /*按用户类型 分页查询*/
    public List<mUser> findPage(Map<String,Object> map);
    /*按用户类型统计数目*/
    public int countByFlag(int flag);
    /*删除用户*/
    public void del(int u_id);
    /*根据u_id查询用户*/
    public mUser findById(int u_id);
    /*修改用户信息*/
    public void update(mUser user);
    /*添加用户信息*/
    public void addUser(mUser user);
    /*审核注册用户*/
    public void examine(Map<String,Object> map);
    /*冻结会员账号*/
    public void freeze(Map<String,Object> map);
    /*根据用户类型查询用户*/
    public List<mUser> findByFlag(int flag);
    /*按搜索框内容 查询并分页*/
    public List<mUser> findSearchPage (Map<String,Object> map);
    /*按搜索框内容 统计记录数*/
    public int countBySearch(Map<String,Object> map);

    /*个人主页相关*/
    /*查找当前登陆用户信息*/
    /*根据u_id查询用户
    public mUser findById(int u_id);*/
    /*查询登陆用户粉丝数目*/
    public int countFans(int u_id);
    /*查询登陆用户关注数目*/
    public int countFollows(int u_id);
    /*查询用户的粉丝列表*/
    public List<mUser> findFans(int u_id);
    /*查询关注用户粉丝列表*/
    public List<mUser> findFollows(int u_id);
    /*查询登陆用户是否关注某一用户*/
    public mUser checkFollow(Map<String,Object> map);
    /*编辑用户信息 除密码*/
    public void edit(mUser user);
    /*关注某人*/
    public void follow(@Param("u_id") int u_id,@Param("fid") int fid);
    /*取消关注某人*/
    public void noFollow (@Param("u_id") int u_id,@Param("fid") int fid);
}
