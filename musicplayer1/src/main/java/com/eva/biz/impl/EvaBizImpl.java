package com.eva.biz.impl;

import com.eva.biz.EvaBiz;
import com.eva.entity.Eva;
import com.eva.mapper.EvaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("evaBiz")
public class EvaBizImpl implements EvaBiz {
    @Autowired
    private EvaMapper evaMapper;

    @Override
    public void eva(Eva e) {
        evaMapper.eva(e);
    }

    @Override
    public void reva(Eva e) {
        evaMapper.reva(e);
    }
}
