package com.ham.util;

import org.junit.Test;

/**
 * Created by hamsbon on 2017/2/18.
 */
public class FileUtilsTest {
    @Test
    public void getSubfix() throws Exception {
        System.out.println(FileUtils.getSubfix("a.txt"));
    }

    @Test
    public void testIndex(){
        System.out.println("result--->>" + "abc".lastIndexOf("."));
    }

}