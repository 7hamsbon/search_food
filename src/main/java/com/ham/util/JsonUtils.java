package com.ham.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hamsbon on 2016/10/14 0014.
 * Json工具类
 */
@Component
public class JsonUtils {
    private static final SerializeConfig config;

    static {
        config = new SerializeConfig();

        config.put(java.util.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss")); // 使用和json-lib兼容的日期输出格式
        config.put(java.sql.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss")); // 使用和json-lib兼容的日期输出格式
    }

    private static final SerializerFeature[] features = {
            SerializerFeature.WriteMapNullValue, // 输出空置字段
            SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
    };

    /**
     * 将对象转换为Json字符串
     * @return Json字符串
     */
    public static String toJSONString(Object object) {
        if(object == null){
            return null;
        }
//        return JSON.toJSONString(object,true);
        return JSON.toJSONString(object, config, features);
    }

    /**
     * 多个对象转换为Json字符串
     */
    public static String[] objs2JsonStr(Object[] objs){
        if(objs == null) {
            return null;
        }
        String[] result = new String[objs.length];
        for(int i=0;i<objs.length;i++){
            result[i] = toJSONString(objs[i]);
        }
        return result;
    }

    /**
     *  将对象转换为Json字符串(没有默认输出)
     * @return Json字符串
     */
    public static String toJSONNoFeatures(Object object) {
        if(StringUtils.isEmpty(object)){
            return null;
        }
        return JSON.toJSONString(object, config);
    }

    /**
     * Json字符串转换为JSONObject
     */
    public static Object toBean(String text) {
        if(StringUtils.isEmpty(text)){
            return null;
        }
        return JSON.parse(text);
    }

    /**
     * Json字符串转换为指定类型 bean
     */
    public static <T> T toBean(String text, Class<T> clazz) {
        if(StringUtils.isEmpty(text)){
            return null;
        }
        return JSON.parseObject(text, clazz);
    }

    /**
     * 字符串转换为数组
     */
    public static Object[] toArray(String text) {
        return toArray(text,null);
    }

    /**
     * 字符串转换为指定类型数组
     */
    public static <T> Object[] toArray(String text, Class<T> clazz) {
        if(StringUtils.isEmpty(text)){
            return null;
        }
        return JSON.parseArray(text, clazz).toArray();
    }

    /**
     * 字符串转换为指定类型List
     */
    public static <T> List<T> toList(String text, Class<T> clazz) {
        if(StringUtils.isEmpty(text)){
            return null;
        }
        return JSON.parseArray(text, clazz);
    }

    /**
     * 将javabean转化为JSONObject 或者 JSONArray
     */
    public static Object obj2JsonObject(Object keyvalue) {
        if (keyvalue == null){
            return null;
        }
        String textJson = JSON.toJSONString(keyvalue);
        return JSON.parse(textJson);
    }

    /**
     * 将string转化为JSONObject 或者 JSONArray
     */
    public static Object string2JsonObject(String text) {
        if(StringUtils.isEmpty(text)){
            return null;
        }
        return JSON.parse(text);
    }

    /**
     * 将若干个json字符串转换为指定类型的对象
     * @return 指定类型对象集合
     */
    public static <T> List<T> strs2Object(String[] strs,Class<T> clazz) {
        if(StringUtils.isEmpty(strs)){
            return null;
        }
        List<T> result = new ArrayList<T>(strs.length);
        for(String str:strs){
            result.add(toBean(str,clazz));
        }
        return result;
    }

    /**
     * json字符串转化为map
     */
    public static Map str2map(String s) {
        if(StringUtils.isEmpty(s)){
            return null;
        }
        Map m = JSONObject.parseObject(s);
        return m;
    }

    /**
     * 将map转化为string
     */
    public static String map2str(Map m) {
        if(m==null){
            return null;
        }
        String s = JSONObject.toJSONString(m);
        return s;
    }

}
