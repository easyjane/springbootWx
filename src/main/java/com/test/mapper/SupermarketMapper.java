package com.test.mapper;

import com.test.entity.Supermarket;
import com.test.entity.SupermarketExample;
import java.util.List;

public interface SupermarketMapper {
    int countByExample(SupermarketExample example);

    int deleteByPrimaryKey(String sid);

    int insert(Supermarket record);

    int insertSelective(Supermarket record);

    List<Supermarket> selectByExample(SupermarketExample example);

    Supermarket selectByPrimaryKey(String sid);

    int updateByPrimaryKeySelective(Supermarket record);

    int updateByPrimaryKey(Supermarket record);
}