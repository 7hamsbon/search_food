package com.ham.service;

import com.ham.entity.User;
import com.ham.service.strategy.BaseUserStrategy;
import com.ham.vo.OpResult;
import com.ham.vo.UserVO;

import java.util.List;

/**
 * Created by hamsbon on 2017/2/15.
 */
public interface UserService {

    /**
     * 注册
     */
    OpResult<String> register(User user) throws Exception;

    /**
     * 登录
     */
    OpResult<User> loginIn(String username, String password) throws Exception;

    /**
     * 修改个人信息
     */
    OpResult<String> updateInfo(User user);

    /**
     *  根据id集合获取用户信息
     */
    OpResult<List<UserVO>> getUserInfo(BaseUserStrategy strategy,Object param);

    OpResult<User> getUserByUsername(String username);

    /**
     * 修改用户有效性,由 EntityConst 的常量决定是否有效
     */
    OpResult<String> updateUserVaild(Long id,int valid);

    /**
     * 获取相同粉丝
     */
    OpResult<List<UserVO>> getSameFans(Long userId1,Long userId2);

    /**
     * 获取相同关注者
     */
    OpResult<List<UserVO>> getSameFollowers(Long userId1,Long userId2);
}
