package com.ham.service.impl;

import com.ham.dao.FriendshipMapper;
import com.ham.entity.FriendshipExample;
import com.ham.service.FriendshipService;
import com.ham.util.CacheUtils;
import com.ham.util.RedisUtils;
import com.ham.vo.FriendshipVO;
import com.ham.vo.OpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hamsbon on 2017/2/21.
 */
@Service
public class FriendshipServiceImpl implements FriendshipService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private FriendshipMapper friendshipMapper;

    @Override
    public OpResult<FriendshipVO> insert(FriendshipVO friendship) {
        OpResult<FriendshipVO> result = new OpResult<>(false,null,"关注失败");
        if(friendship != null && friendship.getFans() != null && friendship.getFans() > 0
                && friendship.getFollower() != null && friendship.getFollower() > 0){
            if(friendshipMapper.insertSelective(friendship) > 0){
                result.setSuccess(true);
                result.setOpMsg("关注成功");
                result.setData(friendship);

                //缓存中相应的粉丝数以及关注人数自增
                String fansCountKey = CacheUtils.getKey(CacheUtils.PREFIX_USER,friendship.getFollower(),CacheUtils.SUBFIX_USER_FANS_NUM);
                RedisUtils.incr(fansCountKey);
                String followerCountKey = CacheUtils.getKey(CacheUtils.PREFIX_USER,friendship.getFans(),CacheUtils.SUBFIX_USER_FOLLOWER_NUM);
                RedisUtils.incr(followerCountKey);
            }else{
                result.setOpMsg("不能重复关注已经关注过的人");
            }
        }else if(friendship.getFans()==null){
            result.setOpMsg("请先登录");
        }
        return result;
    }

    @Override
    public OpResult<String> delete(Long follower, Long fans) {
        OpResult<String> result = new OpResult<>(false,null,"取消关注失败");
        if(follower != null && fans != null){
            FriendshipExample example = new FriendshipExample();
            FriendshipExample.Criteria criteria = example.createCriteria();
            criteria.andFansEqualTo(fans);
            criteria.andFollowerEqualTo(follower);
            if(friendshipMapper.deleteByExample(example)>0){
                result.setSuccess(true);
                result.setOpMsg("取消关注成功");

                //缓存中相应的粉丝数以及关注人数自减
                String fansCountKey = CacheUtils.getKey(CacheUtils.PREFIX_USER,follower,CacheUtils.SUBFIX_USER_FANS_NUM);
                RedisUtils.decr(fansCountKey);
                String followerCountKey = CacheUtils.getKey(CacheUtils.PREFIX_USER,fans,CacheUtils.SUBFIX_USER_FOLLOWER_NUM);
                RedisUtils.decr(followerCountKey);
            }else{
                result.setSuccess(false);
                result.setOpMsg("未关注");
            }
        }else if(fans == null){
            result.setOpMsg("请先登录");
        }
        return result;
    }

    @Override
    public List<Long> getFollowers(Long fansId) {
        List<Long> result = null;
        if(fansId != null && fansId > 0){
            result = friendshipMapper.selectFollwerByFansId(fansId);
        }
        return result;
    }

    @Override
    public List<Long> getFans(Long followerId) {
        List<Long> result = null;
        if(followerId != null && followerId > 0){
            result = friendshipMapper.selectFansByFollowerId(followerId);
        }
        return result;
    }

    @Override
    public OpResult<Long> getSameFansCount(Long userId1, Long userId2) {
        OpResult<Long > result = new OpResult<>(false,null,"查询共同粉丝数失败");
        userId1 = userId1==null?-1L:userId1;
        userId2 = userId2==null?-1L:userId2;
        Long count = friendshipMapper.countSameFans(userId1,userId2);
        if(count!=null){
            result.setSuccess(true);
            result.setData(count);
            result.setOpMsg("");
        }
        return result;
    }

    @Override
    public OpResult<Long> getSameFollowersCount(Long userId1, Long userId2) {
        OpResult<Long > result = new OpResult<>(false,null,"查询共同粉丝数失败");
        userId1 = userId1==null?-1L:userId1;
        userId2 = userId2==null?-1L:userId2;
        Long count = friendshipMapper.countSameFollowers(userId1,userId2);
        if(count!=null){
            result.setSuccess(true);
            result.setData(count);
            result.setOpMsg("");
        }
        return result;
    }

    @Override
    public OpResult<Boolean> isFollow(Long userId1, Long userId2) {
        OpResult<Boolean> result = new OpResult<>(false,null,"查询失败");
        FriendshipExample example = new FriendshipExample();
        FriendshipExample.Criteria criteria = example.createCriteria();
        criteria.andFansEqualTo(userId1);
        criteria.andFollowerEqualTo(userId2);
        if(friendshipMapper.countByExample(example) >0){
            result.setSuccess(true);
            result.setData(true);
        }else{
            result.setSuccess(true);
            result.setData(false);
        }
        result.setOpMsg("");
        return result;
    }
}
