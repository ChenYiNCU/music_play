package com.madmin.biz;

import com.madmin.entity.mAdmin;

public interface mAdminBiz {
    public mAdmin login(String aname,String apwd);
    public mAdmin findByName(String aname);
}
