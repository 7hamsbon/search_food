package com.ham.service.impl;

import com.ham.dao.CollectMapper;
import com.ham.entity.CollectExample;
import com.ham.service.CollectService;
import com.ham.vo.BlogVO;
import com.ham.vo.CollectVO;
import com.ham.vo.OpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hamsbon on 2017/3/8.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Override
    public OpResult<CollectVO> collect(CollectVO collect) {
        OpResult<CollectVO> result = new OpResult<>(false,null,"收藏失败！");
        if(collect!=null && collect.getBlogId() !=null && collect.getUserId() !=null){
            if(collectMapper.insertSelective(collect)>0){
                result.setSuccess(true);
                result.setData(collect);
                result.setOpMsg("收藏成功！");
            }
        }else if(collect.getUserId()==null){
            result.setOpMsg("请先登录");
        }
        return result;
    }

    @Override
    public OpResult<Long> cancelCollect(Long userId, Long blogId) {
        OpResult<Long> result = new OpResult<>(false,null,"取消收藏失败！");
        if(userId!=null && blogId!=null){
            CollectExample example = new CollectExample();
            CollectExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdEqualTo(userId);
            criteria.andBlogIdEqualTo(blogId);
            if(collectMapper.deleteByExample(example)>0){
                result.setSuccess(true);
                result.setData(blogId);
                result.setOpMsg("取消收藏成功！");
            }
        }else if(userId==null){
            result.setOpMsg("请先登录");
        }
        return result;
    }

    @Override
    public List<BlogVO> loadIsCollect(List<BlogVO> targetList, Long userId) {
        if(userId!=null){
            for(BlogVO blog:targetList){
                Long blogId = blog.getId();
                CollectExample example = new CollectExample();
                CollectExample.Criteria criteria = example.createCriteria();
                criteria.andBlogIdEqualTo(blogId);
                criteria.andUserIdEqualTo(userId);
                if(collectMapper.countByExample(example)>0){
                    blog.setCollected(true);
                }else{
                    blog.setCollected(false);
                }
            }
        }
        return targetList;
    }


}
