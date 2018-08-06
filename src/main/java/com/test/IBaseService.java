package com.test;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;

public interface IBaseService <T, ID extends Serializable>{

    int add(T obj);

    PageInfo<T> findPage(int pageNo, int pageSize, T obj);

    T selectByPrimaryKey(String sid);

}
