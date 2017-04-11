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
 * Created by hamsbon on 2017/3/1.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TimeOrderBlogStrategyTest {

    @Autowired
    TimeOrderBlogStrategy timeOrderBlogStrategy;

    @Test
    public void getBlogsByIds() throws Exception {
        List<BlogVO> blogVOS =  timeOrderBlogStrategy.getBlogsByIds(Arrays.asList(1L,2L,3L,4L,5L,6L,7L,8L));
        for(BlogVO blogVO:blogVOS){
            System.out.println("like--->>>" + blogVO.getLikeCount()+"...comment--->>>"+blogVO.getCommentCount()+",time--->>>"+blogVO.getCtime()+",username--->>"+blogVO.getUserName()+",user_head--->>"+blogVO.getUserHeadPath());
        }
    }

}