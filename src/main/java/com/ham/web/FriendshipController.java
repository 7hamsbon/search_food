package com.ham.web;

import com.ham.entity.User;
import com.ham.service.FriendshipService;
import com.ham.vo.FriendshipVO;
import com.ham.vo.OpResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hamsbon on 2017/3/24.
 */
@RestController
@RequestMapping("/friend")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    @PostMapping("/add")
    @Description("关注")
    public OpResult<FriendshipVO> addFriendship(FriendshipVO friendship){
//        User user = (User)session.getAttribute("user");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long fans = user==null?null:user.getId();
        friendship.setFans(fans);
        return friendshipService.insert(friendship);
    }

    @PostMapping("/cancel")
    @Description("取消关注")
    @RequiresUser
    public OpResult<String> cancelFriendship(Long follower){
//        User user = (User)session.getAttribute("user");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long fans = user==null?null:user.getId();
        return friendshipService.delete(follower,fans);
    }


}
