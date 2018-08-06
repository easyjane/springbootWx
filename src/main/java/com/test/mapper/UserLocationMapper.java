package com.test.mapper;

import com.test.entity.UserLocation;
import com.test.entity.UserLocationExample;
import java.util.List;

public interface UserLocationMapper {
    int countByExample(UserLocationExample example);

    int deleteByPrimaryKey(String ulid);

    int insert(UserLocation record);

    int insertSelective(UserLocation record);

    List<UserLocation> selectByExample(UserLocationExample example);

    UserLocation selectByPrimaryKey(String ulid);

    int updateByPrimaryKeySelective(UserLocation record);

    int updateByPrimaryKey(UserLocation record);
}