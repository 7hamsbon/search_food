package com.ham.dao;

import com.ham.entity.User;
import com.ham.entity.UserExample;
import com.ham.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    UserVO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


    /*以下自定义*/

    /**
     * 获得所有关注者信息
     */
    List<UserVO> getFollowerByFansId(Long id);

    /**
     * 获得所有粉丝信息
     */
    List<UserVO> getFansByFollowerId(Long id);

    /**
     * 利用全文索引模糊搜索用户
     */
    List<UserVO> searchByKeywordWithFullText(String keyword);

    /**
     * 利用Like关键字模糊搜索用户
     */
    List<UserVO> searchByKeywordWithLike(String keyword);



    /**
     * 获取相同的粉丝
     */
    List<UserVO> getSameFans(@Param("userId1")Long userId1,@Param("userId2")Long userId2);

    /**
     * 获取相同的关注者
     */
    List<UserVO> getSameFollower(@Param("userId1")Long userId1,@Param("userId2")Long userId2);


}