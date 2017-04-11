package com.ham.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hamsbon on 2016/11/17 0017.
 * 反射工具类
 */
public class ReflectUtils {

    private static final Logger logger = LoggerFactory.getLogger(ReflectUtils.class);

    /**
     * 将obj中fieldName域的值修改为val
     */
    public static void updateObj(Object oldObj,Object newObj){
        try {
            List<Field> fields = new ArrayList<>();
            Class baseClazz = oldObj.getClass();
            while(true){
                fields.addAll(Arrays.asList(baseClazz.getDeclaredFields()));
                Class superClazz = baseClazz.getSuperclass();
                if(superClazz == null || superClazz.getName().equals(Object.class.getName())){
                    break;
                }
                baseClazz = superClazz;
            }
            for(Field field:fields){
                field.setAccessible(true);
                field.set(oldObj,field.get(newObj));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }
}
