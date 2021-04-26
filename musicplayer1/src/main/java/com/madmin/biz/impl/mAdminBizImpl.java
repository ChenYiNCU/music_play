package com.madmin.biz.impl;

import com.madmin.biz.mAdminBiz;
import com.madmin.entity.mAdmin;
import com.madmin.mapper.mAdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("madminBiz")
public class mAdminBizImpl implements mAdminBiz {
    @Autowired
    private mAdminMapper mAdminMapper;

    @Override
    public mAdmin login(String aname, String apwd) {
        return mAdminMapper.login(aname,apwd);
    }

    @Override
    public mAdmin findByName(String aname) {
        return mAdminMapper.findByName(aname);
    }
}
