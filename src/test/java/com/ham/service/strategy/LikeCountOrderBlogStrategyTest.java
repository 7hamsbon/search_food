package com.ham.service.strategy;

import com.ham.vo.BlogVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hamsbon on 2017/3/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LikeCountOrderBlogStrategyTest {

    @Autowired
    private LikeCountOrderBlogStrategy likeCountOrderBlogStrategy;

    @Test
    public void getBlogsByIds() throws Exception {
        List<BlogVO> l =  likeCountOrderBlogStrategy.getBlogsByIds(Arrays.asList(1L,2L,3L));
        for(BlogVO blog:l){
            System.out.println(blog);
        }
    }

}