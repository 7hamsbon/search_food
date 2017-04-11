package com.ham.dao;

import com.ham.entity.Friendship;
import com.ham.entity.FriendshipExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendshipMapper {
    int countByExample(FriendshipExample example);

    int deleteByExample(FriendshipExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Friendship record);

    int insertSelective(Friendship record);

    List<Friendship> selectByExample(FriendshipExample example);

    Friendship selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Friendship record, @Param("example") FriendshipExample example);

    int updateByExample(@Param("record") Friendship record, @Param("example") FriendshipExample example);

    int updateByPrimaryKeySelective(Friendship record);

    int updateByPrimaryKey(Friendship record);

    /**以下为自定义方法**/
    List<Long> selectFollwerByFansId(Long fansId);

    List<Long> selectFansByFollowerId(Long followerId);

    /**
     * 获取共同粉丝数
     */
    Long countSameFans(@Param("userId1") Long userId1,@Param("userId2")Long userId2);

    /**
     * 获取共同关注数
     */
    Long countSameFollowers(@Param("userId1") Long userId1,@Param("userId2")Long userId2);

}