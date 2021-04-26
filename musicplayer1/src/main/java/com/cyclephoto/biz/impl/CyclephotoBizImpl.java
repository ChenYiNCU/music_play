package com.cyclephoto.biz.impl;

import com.cyclephoto.biz.CyclephotoBiz;
import com.cyclephoto.entity.CyclePhoto;
import com.cyclephoto.mapper.CyclephotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cyclephotoBiz")
public class CyclephotoBizImpl implements CyclephotoBiz {
    @Autowired
    private CyclephotoMapper cyclephotoMapper;


    @Override
    public void add(CyclePhoto cycle) {
        cyclephotoMapper.add(cycle);
    }

    @Override
    public void delete(int id) {
        cyclephotoMapper.delete(id);
    }

    @Override
    public List<CyclePhoto> findAll() {
        return cyclephotoMapper.findAll();
    }

    @Override
    public CyclePhoto findById(int id) {
        return cyclephotoMapper.findById(id);
    }

    @Override
    public void update(CyclePhoto cycle) {
        cyclephotoMapper.update(cycle);
    }
}
