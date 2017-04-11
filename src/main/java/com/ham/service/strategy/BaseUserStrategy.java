package com.ham.service.strategy;

import com.ham.dao.BlogMapper;
import com.ham.dao.FriendshipMapper;
import com.ham.dao.LikeitMapper;
import com.ham.entity.BlogExample;
import com.ham.entity.FriendshipExample;
import com.ham.entity.LikeitExample;
import com.ham.util.CacheUtils;
import com.ham.util.RedisUtils;
import com.ham.vo.UserVO;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by hamsbon on 2017/3/3.
 * 用户基础策略
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
public abstract class BaseUserStrategy {

    @Autowired
    protected LikeitMapper likeitMapper;
    @Autowired
    protected BlogMapper blogMapper;
    @Autowired
    protected FriendshipMapper friendshipMapper;

    protected abstract List<UserVO>  buildUserVo(Object param);

    /**
     * 根据id获得装配好的用户
     */
    public List<UserVO> getCompleteUsers(Object param){
        List<UserVO> result = buildUserVo(param);
        if(result!=null){
            loadBlogNum(result);
            loadFansNum(result);
            loadFollowerNum(result);
            loadLikeNum(result);
        }
        return result;
    }

    /**
     * 装配用户点赞数
     */
    protected void loadLikeNum(List<UserVO> target){
        for(UserVO user:target){
            Long id = user.getId();
            Long personalLikeNum;
            String key = CacheUtils.getKey(CacheUtils.PREFIX_USER,id,CacheUtils.SUBFIX_LIKE_NUM);

            String cacheString = RedisUtils.get(key);
            if(StringUtils.isNullOrEmpty(cacheString)){
                List<Long> personalBlogIds = blogMapper.selectPersonalBlogIds(id);
                if(personalBlogIds != null && personalBlogIds.size() > 0){
                    LikeitExample example = new LikeitExample();
                    LikeitExample.Criteria criteria = example.createCriteria();
                    criteria.andBlogIdIn(personalBlogIds);
                    personalLikeNum = (long)likeitMapper.countByExample(example);
                    user.setLikeNum(personalLikeNum);
                }else{
                    personalLikeNum = 0L;
                }
                RedisUtils.set(key,String.valueOf(personalLikeNum));
            }else{
                personalLikeNum = Long.parseLong(cacheString);
            }
            user.setLikeNum(personalLikeNum);
        }
    }

    /**
     * 装配用户博客数
     */
    protected void loadBlogNum(List<UserVO> target){
        for(UserVO user:target){
            Long id = user.getId();
            Long personalBlogNum;
            String key = CacheUtils.getKey(CacheUtils.PREFIX_USER,id,CacheUtils.SUBFIX_USER_BLOG_NUM);
            String cacheValue = RedisUtils.get(key);
            if(StringUtils.isNullOrEmpty(cacheValue)){
                BlogExample example = new BlogExample();
                BlogExample.Criteria criteria = example.createCriteria();
                criteria.andUserIdEqualTo(id);
                personalBlogNum = (long)blogMapper.countByExample(example);
                RedisUtils.set(key,String.valueOf(personalBlogNum));
            }else{
                personalBlogNum = Long.parseLong(cacheValue);
            }
            user.setBlogNum(personalBlogNum);
        }
    }

    /**
     * 装配用户粉丝数
     */
    protected void loadFansNum(List<UserVO> target){
        for(UserVO user:target){
            Long id = user.getId();
            Long personalFansNum;
            String key = CacheUtils.getKey(CacheUtils.PREFIX_USER,id,CacheUtils.SUBFIX_USER_FANS_NUM);
            String cacheValue = RedisUtils.get(key);
            if(StringUtils.isNullOrEmpty(cacheValue)){
                FriendshipExample example = new FriendshipExample();
                FriendshipExample.Criteria criteria = example.createCriteria();
                criteria.andFollowerEqualTo(id);
               personalFansNum = (long)friendshipMapper.countByExample(example);
                RedisUtils.set(key,String.valueOf(personalFansNum));
            }else{
                personalFansNum = Long.parseLong(cacheValue);
            }
            user.setFansNum(personalFansNum);
        }
    }

    /**
     * 装配关注者数
     */
    protected void loadFollowerNum(List<UserVO> target){
        for(UserVO user:target){
            Long id = user.getId();
            Long personalFollowerNum;
            String key = CacheUtils.getKey(CacheUtils.PREFIX_USER,id,CacheUtils.SUBFIX_USER_FOLLOWER_NUM);
            String cacheValue = RedisUtils.get(key);
            if(StringUtils.isNullOrEmpty(cacheValue)){
                FriendshipExample example = new FriendshipExample();
                FriendshipExample.Criteria criteria = example.createCriteria();
                criteria.andFansEqualTo(id);
                personalFollowerNum = (long)friendshipMapper.countByExample(example);
                RedisUtils.set(key,String.valueOf(personalFollowerNum));
            }else{
                personalFollowerNum = Long.parseLong(cacheValue);
            }
            user.setFollowerNum(personalFollowerNum);
        }
    }
}
