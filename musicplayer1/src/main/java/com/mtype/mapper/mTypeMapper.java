package com.mtype.mapper;

import com.mtype.entity.mType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface mTypeMapper {
    public void add(mType type);
    public List<mType> findAll();
    public mType findById(int id);
    public int findByName(String tname);
    public void update(mType type);
}
