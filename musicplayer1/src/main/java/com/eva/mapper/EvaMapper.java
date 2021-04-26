package com.eva.mapper;

import com.eva.entity.Eva;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EvaMapper {
    public void eva(Eva e);
    public void reva(Eva e);
}
