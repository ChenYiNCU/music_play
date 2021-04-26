package com.madmin.mapper;

import com.madmin.entity.mAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface mAdminMapper {
    public mAdmin login(@Param("aname") String aname,@Param("apwd") String apwd);
    public mAdmin findByName(String aname);
}
