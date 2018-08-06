package com.test.service;

import com.github.pagehelper.PageInfo;
import com.test.IBaseService;
import com.test.entity.UserFocus;

public interface IUserFocusService extends IBaseService<UserFocus,String> {

    @Override
    int add(UserFocus obj);

    @Override
    PageInfo<UserFocus> findPage(int pageNo, int pageSize, UserFocus obj);

    @Override
    UserFocus selectByPrimaryKey(String sid);
}
