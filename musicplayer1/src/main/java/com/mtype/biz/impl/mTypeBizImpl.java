package com.mtype.biz.impl;

import com.mtype.biz.mTypeBiz;
import com.mtype.entity.mType;
import com.mtype.mapper.mTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mtypeBiz")
public class mTypeBizImpl implements mTypeBiz {
    @Autowired
    private mTypeMapper mtypeMapper;

    @Override
    public void add(mType type) {
        mtypeMapper.add(type);
    }

    @Override
    public List<mType> findAll() { return mtypeMapper.findAll(); }

    @Override
    public mType findById(int id) {
        return mtypeMapper.findById(id);
    }

    @Override
    public int findByName(String tname) { return mtypeMapper.findByName(tname); }

    @Override
    public void update(mType type) {
        mtypeMapper.update(type);
    }
}
