package com.cyclephoto.biz;

import com.cyclephoto.entity.CyclePhoto;

import java.util.List;

public interface CyclephotoBiz {
    public void add(CyclePhoto cycle);
    public void delete(int id);
    public List<CyclePhoto> findAll();
    public CyclePhoto findById(int id);
    public void update(CyclePhoto cycle);
}
