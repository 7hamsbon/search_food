package com.ham.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

/**
 * Created by hamsbon on 2017/2/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtilsTest {
    @Test
    public void getJedis() throws Exception {
        Jedis jedis = RedisUtils.getJedis();
        if(jedis == null){
            System.out.println("Jedis cannot get");
        }else{
            System.out.println(jedis.get("hello"));
        }
    }


}