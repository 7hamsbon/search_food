package com.ham.service.impl;

import com.ham.vo.CommentVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by hamsbon on 2017/3/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceImplTest {

    @Autowired
    private CommentServiceImpl commentService;

    @Test
    public void getCommentByBlogId() throws Exception {
        List<CommentVO> l = commentService.getCommentByBlogId(1L).getData();
        for(CommentVO commentVO :l){
            System.out.println(commentVO);
        }
    }

}