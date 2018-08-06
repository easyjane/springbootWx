package com.test.service;

import com.github.pagehelper.PageInfo;
import com.test.entity.Supermarket;

import java.util.List;

public interface ISuperMarketService {

    int addSuperMarket(Supermarket smk);

    PageInfo<Supermarket> findPage(int pageNo, int pageSize,Supermarket smk);

    Supermarket selectByPrimaryKey(String sid);
}
