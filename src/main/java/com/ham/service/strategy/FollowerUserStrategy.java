package com.ham.service.strategy;

import com.ham.dao.UserMapper;
import com.ham.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hamsbon on 2017/3/3.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
public class FollowerUserStrategy extends BaseUserStrategy {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserVO> buildUserVo(Object param){
        return (param instanceof Long ? userMapper.getFollowerByFansId((Long) param):null);
    }
}
