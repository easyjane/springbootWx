package com.test.service;

import com.github.pagehelper.PageInfo;
import com.test.IBaseService;
import com.test.entity.Viptxt;

public interface IVipTxtService extends IBaseService<Viptxt,String> {

    @Override
    int add(Viptxt obj);

    @Override
    PageInfo<Viptxt> findPage(int pageNo, int pageSize, Viptxt obj);

    @Override
    Viptxt selectByPrimaryKey(String sid);
}
