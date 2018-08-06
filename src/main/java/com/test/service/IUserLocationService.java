package com.test.service;

import com.github.pagehelper.PageInfo;
import com.test.IBaseService;
import com.test.entity.UserLocation;

public interface IUserLocationService extends IBaseService<UserLocation,String> {

    @Override
    int add(UserLocation obj);

    @Override
    PageInfo<UserLocation> findPage(int pageNo, int pageSize, UserLocation obj);

    @Override
    UserLocation selectByPrimaryKey(String sid);
}
