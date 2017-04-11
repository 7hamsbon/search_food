package com.ham.service.strategy;

import com.ham.dao.BlogMapper;
import com.ham.entity.BlogExample;
import com.ham.vo.BlogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hamsbon on 2017/2/28.
 * 根据时间排序降序策略获得博客集
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
public class TimeOrderBlogStrategy extends BaseBlogStrategy {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<BlogVO> getBlogsByIds(List<Long> ids) {
        List<BlogVO> result = null;
        if(ids != null && ids.size() > 0){
            BlogExample example = new BlogExample();
            example.setOrderByClause("ctime desc");
            BlogExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdIn(ids);
            result = blogMapper.selectByExampleIntoVo(example);
            loadLikeNum(result);
            loadCommentCount(result);
        }
        return result;
    }

//    @Override
//    public List<BlogVO> getBlogsByKeyword(String keyword) {
//        List<BlogVO> result = null;
//        if(!StringUtils.isNullOrEmpty(keyword)){
//            result = blogMapper.searchByKeywordWithFullText(keyword);
//            loadLikeNum(result);
//            loadCommentCount(result);
//        }
//        return result;
//    }


}
