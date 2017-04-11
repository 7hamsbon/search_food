package com.ham.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by hamsbon on 2017/2/14.
 */
@Component
public class RedisUtils {

    private static JedisPool jedisPool;

    private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    /**
     * 标记缓存服务是否开启
     */
    private static boolean on = true;

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        RedisUtils.jedisPool = jedisPool;
    }

    public static Jedis getJedis(){
        Jedis jedis = null;
        try{
            if(on){
                jedis = jedisPool.getResource();
            }
        }catch (Exception e){
            on = false;
//            logger.debug(e.getMessage(),e);
            logger.error("Redis 配置错误或者服务未开启");
        }
        return jedis;
    }

    public static void returnResource(Jedis jedis){
        if(jedis!=null){
            jedis.close();
        }
    }

    public static String get(String key){
        Jedis jedis = getJedis();
        String result = ( jedis != null ? jedis.get(key) : null);
        returnResource(jedis);
        return result;
    }

    public static String set(String key,String value){
        Jedis jedis = getJedis();
        String result = (jedis != null ? jedis.set(key,value) : null);
        returnResource(jedis);
        return result;
    }

    public static Boolean exists(String key){
        Jedis jedis = getJedis();
        Boolean result = (jedis != null ? jedis.exists(key) : null);
        returnResource(jedis);
        return result;
    }

    /**
     * 自增，如果不存在返回-1
     */
    public static Long incr(String key){
        Jedis jedis = getJedis();
        Long result = (jedis != null ? (exists(key)?jedis.incr(key):-1) : null);
        returnResource(jedis);
        return result;
    }

}
