package com.ham.service;

import com.ham.vo.FriendshipVO;
import com.ham.vo.OpResult;

import java.util.List;

/**
 * Created by hamsbon on 2017/2/21.
 */
public interface FriendshipService {

    /**
     * 添加关注关系
     */
    OpResult<FriendshipVO> insert(FriendshipVO friendship);

    /**
     * 取消关注
     */
    OpResult<String> delete(Long follower,Long fans);

    /**
     * 获得用户 id 为 fansId 的所有关注者
     */
    List<Long> getFollowers(Long fansId);

    /**
     * 获得用户 id 为 followerId 的所有粉丝
     */
    List<Long> getFans(Long followerId);

    /**
     * 获取共同粉丝数
     */
    OpResult<Long> getSameFansCount(Long userId1,Long userId2);

    /**
     * 获取共同关注数
     */
    OpResult<Long> getSameFollowersCount(Long userId1,Long userId2);

    /**
     * 用户1是否关注用户2
     */
    OpResult<Boolean> isFollow(Long userId1,Long userId2);

}
