package com.ham.service.strategy;

import com.ham.dao.BlogMapper;
import com.ham.entity.BlogExample;
import com.ham.vo.BlogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hamsbon on 2017/2/28.
 * 根据赞数排序获得博客集
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
public class LikeCountOrderBlogStrategy extends BaseBlogStrategy {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<BlogVO> getBlogsByIds(List<Long> ids) {
        List<BlogVO> result = null;
        if(ids != null && ids.size() > 0){
            BlogExample example = new BlogExample();
            example.setOrderByClause("like_count desc");
            BlogExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdIn(ids);
            result = blogMapper.selectByExampleWithVo(example);
            loadLikeNum(loadCommentCount(result));
        }
        return result;
    }
}
