package com.ham.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by hamsbon on 2017/2/20.
 */
@Component
public class PathUtils {

    private static final Logger logger = LoggerFactory.getLogger(PathUtils.class);

    private static final String ROOT = "upload_dir";

    public static String getStaticResourcePath(String base){
//        String result = (new File("src"+File.separator
//                +"main"+File.separator+"resources"+File.separator+"static"+File.separator+base)).getAbsolutePath();

        String result = (new File(ROOT + File.separator + base)).getAbsolutePath();

        logger.debug("path--->>>"+result);

        return result;
    }
}
