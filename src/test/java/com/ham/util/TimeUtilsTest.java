package com.ham.util;

import org.junit.Test;

/**
 * Created by hamsbon on 2017/2/18.
 */
public class TimeUtilsTest {
    @Test
    public void getNowDir() throws Exception {
        System.out.println(TimeUtils.getNowDir(TimeUtils.STORE_DIR));
        System.out.println(TimeUtils.getNowDir(TimeUtils.VISIT_DIR));
    }

}