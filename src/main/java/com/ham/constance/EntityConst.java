package com.ham.constance;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hamsbon on 2017/2/16.
 */
public class EntityConst {


    /**
     * 默认背景音乐拥有者id
     */
    public static final Long DEFAULT_MUSIC_OWNER_ID = 0L;
    /**
     * 默认头像url
     */
    public static final String DEFAULT_HEAD_URL = "default_head.png";
    /**
     * 默认食物图片url
     */
    public static final String DEFAULT_FOOD_URL = "default_food.png";
    /**
     * 用户有效
     */
    public static final int USER_VALID = 1;
    /**
     * 用户无效
     */
    public static final int USER_UNVALID = 0;

    /**
     * 博客排序方式-时间
     */
    public static final int BLOG_ORDER_BY_TIME = 0;

    /**
     * 博客排序方式-赞数
     */
    public static final int BLOG_ORDER_BY_LIKE = 1;

    /**
     * 用户性别-男
     */
    public static final int USER_GENDER_MALE = 1;
    /**
     * 用户性别-女
     */
    public static final int USER_GENDER_FEMALE = 0;

    public static List<Integer> getUserValidList(){
        return Arrays.asList(USER_VALID,USER_UNVALID);
    }
}
