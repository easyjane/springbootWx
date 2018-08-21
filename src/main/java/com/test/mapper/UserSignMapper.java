package com.test.mapper;

import com.test.entity.UserSign;
import com.test.entity.UserSignExample;
import java.util.List;

public interface UserSignMapper {
    int countByExample(UserSignExample example);

    int deleteByPrimaryKey(String usid);

    int insert(UserSign record);

    int insertSelective(UserSign record);

    List<UserSign> selectByExample(UserSignExample example);

    UserSign selectByPrimaryKey(String usid);

    int updateByPrimaryKeySelective(UserSign record);

    int updateByPrimaryKey(UserSign record);

    UserSign login(UserSign userSign);
}