package com.ham.web;

import com.ham.entity.Likeit;
import com.ham.entity.User;
import com.ham.service.LikeService;
import com.ham.vo.OpResult;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hamsbon on 2017/3/3.
 */
@Controller
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/{blogId}")
    @ResponseBody
    public OpResult<String> like(@PathVariable("blogId")Long blogId){
//        User user = (User) session.getAttribute("user");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Likeit likeit = new Likeit();
        likeit.setBlogId(blogId);
        likeit.setUserId(user==null?null:user.getId());
        return likeService.like(likeit);
    }

    @PostMapping("/cancel/{blogId}")
    @ResponseBody
    public OpResult<String> cancel(@PathVariable("blogId")Long blogId){
//        User user = (User) session.getAttribute("user");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Likeit likeit = new Likeit();
        likeit.setBlogId(blogId);
        likeit.setUserId(user.getId());
        return likeService.cancel(likeit);
    }

}
