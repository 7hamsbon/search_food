package com.ham.dao;

import com.ham.entity.Likeit;
import com.ham.entity.LikeitExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LikeitMapper {
    int countByExample(LikeitExample example);

    int deleteByExample(LikeitExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Likeit record);

    int insertSelective(Likeit record);

    List<Likeit> selectByExample(LikeitExample example);

    Likeit selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Likeit record, @Param("example") LikeitExample example);

    int updateByExample(@Param("record") Likeit record, @Param("example") LikeitExample example);

    int updateByPrimaryKeySelective(Likeit record);

    int updateByPrimaryKey(Likeit record);


    /*以下自定义*/
    List<Map<String,Long>> selectLikeNumByBlogIds(List<Long> ids);
}