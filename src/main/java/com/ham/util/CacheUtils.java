package com.ham.util;

import com.mysql.jdbc.StringUtils;
import redis.clients.jedis.Jedis;

/**
 * Created by hamsbon on 2017/3/1.
 */
public class CacheUtils {

    public static final String PREFIX_USER = "USER:";

    public static final String PREFIX_BLOG = "BLOG:";

    public static final String SUBFIX_LIKE_NUM = ":likeNum";

    public static final String SUBFIX_USER_FANS_NUM = ":fansNum";

    public static final String SUBFIX_USER_FOLLOWER_NUM = ":followerNum";

    public static final String SUBFIX_USER_BLOG_NUM = ":blogNum";

    public static final String SUBFIX_BLOG_COMMENT_NUM = ":commentNum";

    public static final Jedis jedis = RedisUtils.getJedis();

    public static String getKey(String prefix,Long id,String subfix){
        if(id == null || StringUtils.isNullOrEmpty(subfix) || StringUtils.isNullOrEmpty(prefix)){
            return "";
        }
        return prefix + id + subfix;
    }



}
