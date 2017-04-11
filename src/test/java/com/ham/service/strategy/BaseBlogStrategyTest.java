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
 * Created by hamsbon on 2017/3/8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseBlogStrategyTest {
    @Autowired
    private CollectBlogStategy stategy;

    @Test
    public void testCollectStategy(){
        List<BlogVO> blogs = stategy.getBlogsByIds(Collections.singletonList(1L));
        for(BlogVO blog:blogs){
            System.out.println(blog);
        }
    }

}