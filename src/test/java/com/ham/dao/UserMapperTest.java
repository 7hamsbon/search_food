package com.ham.dao;

import com.ham.vo.UserVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by hamsbon on 2017/3/1.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper mapper;

    @Test
    public void selectInfoByPrimaryKey() throws Exception {
        UserVO userVO = mapper.selectByPrimaryKey(2L);
        System.out.println(userVO);
    }

}