package com.ham.service.strategy;

import com.ham.dao.BlogMapper;
import com.ham.vo.BlogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hamsbon on 2017/3/8.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
public class CollectBlogStategy extends BaseBlogStrategy {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<BlogVO> getBlogsByIds(List<Long> ids) {
        List<BlogVO> blogs = null;
        if(ids!=null && ids.size()>0){
            blogs = blogMapper.selectCollectBlogByUserId(ids.get(0));
        }
        return loadLikeNum(loadCommentCount(blogs));
    }
}
