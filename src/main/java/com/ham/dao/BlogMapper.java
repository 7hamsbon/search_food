package com.ham.dao;

import com.ham.entity.Blog;
import com.ham.entity.BlogExample;
import com.ham.vo.BlogVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogMapper {
    int countByExample(BlogExample example);

    int deleteByExample(BlogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Blog record);

    int insertSelective(Blog record);

    List<Blog> selectByExample(BlogExample example);

    Blog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Blog record, @Param("example") BlogExample example);

    int updateByExample(@Param("record") Blog record, @Param("example") BlogExample example);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKey(Blog record);

    /*以下自定义*/

    List<BlogVO> selectByExampleWithVo(BlogExample example);

    List<BlogVO> selectByExampleIntoVo(BlogExample example);

    List<Long> selectPersonalBlogIds(Long id);

    List<BlogVO> searchByKeyword(@Param("keyword")String keyword);

    List<BlogVO> searchByKeywordAndLike(String keyword);

    List<BlogVO> fuzzySearch(String keyword);

    List<BlogVO> selectCollectBlogByUserId(Long userId);
}