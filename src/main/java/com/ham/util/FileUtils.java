package com.ham.util;

import com.mysql.jdbc.StringUtils;

/**
 * Created by hamsbon on 2017/2/18.
 */
public class FileUtils {

    public static String getSubfix(String filename){
        int index;
        if(StringUtils.isNullOrEmpty(filename) || (index = filename.lastIndexOf(".")) == -1){
            return null;
        }
        return filename.substring(index);
    }
}
