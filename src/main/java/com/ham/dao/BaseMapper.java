package com.ham.dao;

import com.ham.entity.BaseExample;

import java.util.List;

/**
 * Created by hamsbon on 2017/2/15.
 */
public interface BaseMapper<T> {
    int countByExample(BaseExample<T> example);

    int deleteByExample(BaseExample<T> example);

    int deleteByPrimaryKey(Long id);

    int insert(T record);

    int insertSelective(T record);

    List<T> selectByExample(BaseExample<T> example);

    T selectByPrimaryKey(Long id);

    int updateByExampleSelective(T record, BaseExample<T> example);

    int updateByExample(T record, BaseExample<T> example);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
