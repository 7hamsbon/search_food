package com.ham.util;

import com.ham.vo.BlogVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hamsbon on 2017/3/25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ListTest {

    @Test
    public void testListAddNull(){
        List<BlogVO> l = new ArrayList<>();
        l.add(null);
        l.add(null);
        l.add(null);
        l.add(null);
        l.add(null);
        l.add(null);
        System.out.println("size -->> "+l.size());
        System.out.println(l);
    }
}
