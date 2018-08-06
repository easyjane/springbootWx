package com.test.mapper;

import com.test.entity.Viptxt;
import com.test.entity.ViptxtExample;
import java.util.List;

public interface ViptxtMapper {
    int countByExample(ViptxtExample example);

    int deleteByPrimaryKey(String vtid);

    int insert(Viptxt record);

    int insertSelective(Viptxt record);

    List<Viptxt> selectByExample(ViptxtExample example);

    Viptxt selectByPrimaryKey(String vtid);

    int updateByPrimaryKeySelective(Viptxt record);

    int updateByPrimaryKey(Viptxt record);
}