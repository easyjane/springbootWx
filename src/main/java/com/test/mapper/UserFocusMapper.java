package com.test.mapper;

import com.test.entity.UserFocus;
import com.test.entity.UserFocusExample;
import java.util.List;

public interface UserFocusMapper {
    int countByExample(UserFocusExample example);

    int deleteByPrimaryKey(String ufid);

    int insert(UserFocus record);

    int insertSelective(UserFocus record);

    List<UserFocus> selectByExample(UserFocusExample example);

    UserFocus selectByPrimaryKey(String ufid);

    int updateByPrimaryKeySelective(UserFocus record);

    int updateByPrimaryKey(UserFocus record);
}