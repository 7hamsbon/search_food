package com.ham.service.impl;

import com.ham.entity.User;
import com.ham.service.UserService;
import com.ham.service.strategy.FuzzySearchUserStrategy;
import com.ham.service.strategy.PersonalUserStrategy;
import com.ham.vo.OpResult;
import com.ham.vo.UserVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by hamsbon on 2017/2/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    public void register() throws Exception {
        User user = new User();
        user.setUsername("zhangsan");
        user.setPassword("111111");
        OpResult<String> result = userService.register(user);
        System.out.println("result --->>>"+result);
        System.out.println("user --->>>"+user);
    }

    @Test
    public void loginIn() throws Exception {
        OpResult<User> opResult = userService.loginIn("zhangsan","111111");
        System.out.println("正常情况---》》》"+opResult);
        opResult = userService.loginIn(null,null);
        System.out.println("姓名密码都为空---》》》"+opResult);
        opResult = userService.loginIn("zhangsan","hello");
        System.out.println("姓名正确，密码错误---》》》"+opResult);
        opResult = userService.loginIn("zhangsa","111111");
        System.out.println("不存在用户---》》》"+opResult);
    }

    @Autowired
    FuzzySearchUserStrategy strategy ;

    @Test
    public void fuzzySearch(){

        List<UserVO> users = userService.getUserInfo(strategy,"尼古拉斯").getData();
        for(UserVO user:users){
            System.out.println(user);
        }
    }

    @Autowired
    PersonalUserStrategy personalUserStrategy;

    @Test
    public void test() throws IllegalAccessException, InstantiationException {
//        System.out.println(userService.getUserInfo(personalUserStrategy,19L));
        Class c = User.class;
        User user = (User) c.newInstance();
        user.setUsername("hello");
        System.out.println(user.getUsername());
        System.out.println(c.getName());
    }


    @Test
    public void testSameFans(){
        List<UserVO> l = userService.getSameFans(1L,2L).getData();
        for(UserVO u :l){
            System.out.println(u);
        }
    }

}