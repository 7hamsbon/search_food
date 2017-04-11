package com.ham.service.strategy;

import com.ham.vo.UserVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by hamsbon on 2017/3/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FollowerUserStrategyTest {

    @Autowired
    FollowerUserStrategy followerUserStrategy;

    @Test
    public void buildUserVo() throws Exception {
        List<UserVO> users = followerUserStrategy.getCompleteUsers(1L);
        for(UserVO user:users){
            System.out.println(user);
        }
    }

}