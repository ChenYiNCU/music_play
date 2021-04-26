package com.mtype.biz;

import com.mtype.entity.mType;

import java.util.List;

public interface mTypeBiz {
    public void add(mType type);
    public List<mType> findAll();
    public mType findById(int id);
    public int findByName(String tname);
    public void update(mType type);
}
