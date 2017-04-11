package com.ham.service;

import com.ham.service.strategy.BaseBlogStrategy;
import com.ham.vo.BlogVO;
import com.ham.vo.OpResult;

import java.util.List;

/**
 * Created by hamsbon on 2017/2/20.
 */
public interface BlogService {

    /**
     *  发布博文
     */
    OpResult<BlogVO> publish(BlogVO blog);

    /**
     * 删除博文
     */
    OpResult<String> delete(Long id);

    /**
     * 根据策略和用户的id集获得博客集
     */
    OpResult<List<BlogVO>> getBlogsByUserIds(BaseBlogStrategy baseBlogStrategy, List<Long> ids);

    /**
     * 模糊搜索
     */
    OpResult<List<BlogVO>> fuzzySearchBlogByKeyword(String keyword);

}
