package com.ham.service.impl;

import com.ham.service.FriendshipService;
import com.ham.vo.FriendshipVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by hamsbon on 2017/2/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FriendshipServiceImplTest {

    @Autowired
    FriendshipService friendshipService;

    @Test
    public void insert() throws Exception {
        FriendshipVO friendship1 = new FriendshipVO();
        friendship1.setFans(1L);
        friendship1.setFollower(2L);
        FriendshipVO friendship2 = new FriendshipVO();
        friendship2.setFans(2L);
        friendship2.setFollower(3L);
        FriendshipVO friendship3 = new FriendshipVO();
        friendship3.setFans(1L);
        friendship3.setFollower(5L);

        System.out.println(friendshipService.insert(friendship1));
        System.out.println(friendshipService.insert(friendship3));
//        friendshipService.insert(friendship2);
    }

    @Test
    public void delete() throws Exception {
        System.out.println(friendshipService.delete(1L,2L));
        System.out.println(friendshipService.delete(1L,2L));
    }

    @Test
    public void getFollowers() throws Exception {
        System.out.println(friendshipService.getFollowers(1L));
        System.out.println(friendshipService.getFollowers(2L));
    }

    @Test
    public void getFans() throws Exception {
        System.out.println(friendshipService.getFans(1L));
        System.out.println(friendshipService.getFans(2L));
    }

    @Test
    public void countSameFans(){
        System.out.println(friendshipService.getSameFansCount(1L,2L));
        System.out.println(friendshipService.getSameFansCount(15L,18L));
    }

    @Test
    public void testIsFollow(){
        System.out.println(friendshipService.isFollow(1L,2L));
        System.out.println(friendshipService.isFollow(5L,18L));
    }

}