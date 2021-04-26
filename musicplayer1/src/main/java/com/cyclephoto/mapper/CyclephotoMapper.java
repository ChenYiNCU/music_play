package com.cyclephoto.mapper;

import com.cyclephoto.entity.CyclePhoto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CyclephotoMapper {
    public void add(CyclePhoto cycle);
    public void delete(int id);
    public List<CyclePhoto> findAll();
    public CyclePhoto findById(int id);
    public void update(CyclePhoto cycle);
}
