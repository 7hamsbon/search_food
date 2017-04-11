package com.ham.util;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hamsbon on 2017/2/18.
 */
public class TimeUtils {

    public static final int STORE_DIR = 1;

    public static final int VISIT_DIR = 0;

    public static String getNowDir(int dirType){

        char separator = (dirType==STORE_DIR ? File.separatorChar : '/');

        StringBuilder sb = new StringBuilder(separator+"");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        sb.append(String.valueOf(calendar.get(Calendar.YEAR)))
                .append(separator)
                .append(String.valueOf(calendar.get(Calendar.MONTH)+1))
                .append(separator)
                .append(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)))
                .append(separator);
        return sb.toString();
    }
}
