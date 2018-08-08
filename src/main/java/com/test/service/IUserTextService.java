package com.test.service;

import com.github.pagehelper.PageInfo;
import com.test.IBaseService;
import com.test.entity.UserText;

public interface IUserTextService extends IBaseService<UserText,String> {

    @Override
    int add(UserText obj);

    @Override
    PageInfo<UserText> findPage(int pageNo, int pageSize, UserText obj);

    @Override
    UserText selectByPrimaryKey(String sid);
}
