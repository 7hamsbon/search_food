package com.ham.dao;

import com.ham.entity.BlogExample;
import com.ham.vo.BlogVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hamsbon on 2017/2/28.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogMapperTest {

    @Autowired
    BlogMapper blogMapper;
    @Test
    public void selectByExampleWithVo() throws Exception {
        BlogExample example = new BlogExample();
        example.setOrderByClause("like_count desc");
        BlogExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdIn(Arrays.asList(1L,2L,3L));
        List<BlogVO> l = blogMapper.selectByExampleWithVo(example);
        for(BlogVO b:l){
            System.out.println(b);
        }
    }

    @Test
    public void selectByPrimaryKey(){
        System.out.println(blogMapper.selectByPrimaryKey(1L));
    }


    @Test
    public void testSearchByKeyword(){
        List<BlogVO> blogs = blogMapper.searchByKeyword("广州");
        for(BlogVO blog :blogs){
            System.out.println(blog);
        }
    }
}