package com.test.mapper;

import com.test.entity.UserText;
import com.test.entity.UserTextExample;
import java.util.List;

public interface UserTextMapper {
    int countByExample(UserTextExample example);

    int deleteByPrimaryKey(String utid);

    int insert(UserText record);

    int insertSelective(UserText record);

    List<UserText> selectByExample(UserTextExample example);

    UserText selectByPrimaryKey(String utid);

    int updateByPrimaryKeySelective(UserText record);

    int updateByPrimaryKey(UserText record);
}