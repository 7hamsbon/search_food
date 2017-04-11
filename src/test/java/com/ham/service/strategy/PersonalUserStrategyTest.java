package com.ham.service.strategy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by hamsbon on 2017/3/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonalUserStrategyTest {

    @Autowired
    private PersonalUserStrategy personalUserStrategy;
    @Test
    public void buildUserVo() throws Exception {
        System.out.println(personalUserStrategy.getCompleteUsers(1L));
    }

}