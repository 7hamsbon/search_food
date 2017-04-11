package com.ham.service.impl;

import com.ham.entity.Blog;
import com.ham.service.BlogService;
import com.ham.vo.BlogVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hamsbon on 2017/2/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogServiceImplTest {

    @Autowired
    BlogService blogService;

    @Test
    public void publish() throws Exception {
        BlogVO blog = new BlogVO();
        blog.setUserId(1L);
        blog.setContent("hello");
        blog.setDescription("checkit");
        blog.setFoodName("food");
        blog.setPrice(123.3);
        System.out.println(blogService.publish(blog));
    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void getBlogByUserIds() throws Exception {
        List<Long> ids = Arrays.asList(1L,2L,3L);
        List<Blog> l = null;
//        List<Blog> l = blogService.getBlogByUserIds(ids).getData().getBlogs();
        for(Blog b:l) {
            System.out.println(b.getCtime());
        }
    }

    @Test
    public void testFuzzySearch(){
        List<BlogVO> l = blogService.fuzzySearchBlogByKeyword("南亭").getData();
        for(BlogVO blog:l){
            System.out.println(blog);
        }
    }

}