package com.ham.service.strategy;

import com.ham.dao.UserMapper;
import com.ham.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Created by hamsbon on 2017/3/3.
 * 获取个人信息的策略
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
public class PersonalUserStrategy extends BaseUserStrategy {

    @Autowired
    private UserMapper userMapper;

    @Override
    protected List<UserVO> buildUserVo(Object param) {
        List<UserVO> l = null;
        if(param instanceof Long){
            Long idLong = (Long) param;
            UserVO user = userMapper.selectByPrimaryKey(idLong);
            l = user == null ? null : Collections.singletonList(user);
        }
        return l;
    }
}
