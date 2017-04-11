package com.ham.service.strategy;

import com.ham.vo.BlogVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

/**
 * Created by hamsbon on 2017/3/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectBlogStategyTest {

    @Autowired
    CollectBlogStategy collectBlogStategy;

    @Test
    public void getBlogsByIds() throws Exception {
        List<BlogVO> l = collectBlogStategy.getBlogsByIds(Collections.singletonList(1L));
        for(BlogVO b:l){
            System.out.println(b);
        }
    }

}