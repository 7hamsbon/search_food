package com.ham.service.impl;

import com.ham.dao.BlogMapper;
import com.ham.dao.LikeitMapper;
import com.ham.entity.Likeit;
import com.ham.entity.LikeitExample;
import com.ham.service.LikeService;
import com.ham.util.CacheUtils;
import com.ham.util.RedisUtils;
import com.ham.vo.BlogVO;
import com.ham.vo.OpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hamsbon on 2017/3/3.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeitMapper likeitMapper;

    @Autowired
    private BlogMapper blogMapper;


    @Override
    public OpResult<String> like(Likeit likeit) {
        OpResult<String> result = new OpResult<>(false,null,"点赞失败！");
        if(likeit!=null && likeit.getBlogId()!=null && likeit.getUserId()!=null){
            if(likeitMapper.insertSelective(likeit) > 0){
                result.setSuccess(true);
                result.setOpMsg("点赞成功");

                //博客点赞数以及用户点赞数自增
                String blogLikeCountKey = CacheUtils.getKey(CacheUtils.PREFIX_BLOG,likeit.getBlogId(),CacheUtils.SUBFIX_LIKE_NUM);
                RedisUtils.incr(blogLikeCountKey);
                Long userId = blogMapper.selectByPrimaryKey(likeit.getBlogId()).getUserId();
                String userLikeCountKey =CacheUtils.getKey(CacheUtils.PREFIX_USER,userId,CacheUtils.SUBFIX_LIKE_NUM);
                RedisUtils.incr(userLikeCountKey);
            }else{
                result.setOpMsg("已点过赞");
            }
        }else if( likeit.getUserId()==null){
            result.setOpMsg("请先登录");
        }
        return result;
    }

    @Override
    public OpResult<String> cancel(Likeit likeit) {
        OpResult<String> result = new OpResult<>(false,null,"取消失败");
        if(likeit!=null){

            Long userId = likeit.getUserId();
            Long blogId = likeit.getBlogId();
            if(userId!=null && blogId!= null){
                LikeitExample example = new LikeitExample();
                LikeitExample.Criteria criteria = example.createCriteria();
                criteria.andBlogIdEqualTo(blogId);
                criteria.andUserIdEqualTo(userId);
                if(likeitMapper.deleteByExample(example)>0){
                   result.setSuccess(true);
                   result.setOpMsg("取消成功");

                   //博客点赞数-1
                    String blogLikeNumKey = CacheUtils.getKey(CacheUtils.PREFIX_BLOG , blogId ,CacheUtils.SUBFIX_LIKE_NUM);
                    RedisUtils.decr(blogLikeNumKey);
                    //用户获得点赞数-1
                    String userLikeNumKey = CacheUtils.getKey(CacheUtils.PREFIX_USER , blogMapper.selectByPrimaryKey(blogId).getUserId() ,CacheUtils.SUBFIX_LIKE_NUM);
                    RedisUtils.decr(userLikeNumKey);
                }
            }
        }
        return result;
    }

    @Override
    public List<BlogVO> loadIsLike(List<BlogVO> target, Long userId) {
        if(userId!=null){
            for(BlogVO blog:target){
                Long blogId = blog.getId();
                LikeitExample example = new LikeitExample();
                LikeitExample.Criteria criteria = example.createCriteria();
                criteria.andBlogIdEqualTo(blogId);
                criteria.andUserIdEqualTo(userId);
                if(likeitMapper.countByExample(example)>0){
                    blog.setLiked(true);
                }else{
                    blog.setLiked(false);
                }
            }
        }
        return target;
    }
}
