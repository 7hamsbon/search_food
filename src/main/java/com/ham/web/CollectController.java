package com.ham.web;

import com.ham.entity.User;
import com.ham.service.CollectService;
import com.ham.vo.CollectVO;
import com.ham.vo.OpResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hamsbon on 2017/3/9.
 */
@RestController
public class CollectController {

    @Autowired
    private CollectService collectService;

    @PostMapping("/collect")
    @Description("收藏")
    @RequiresPermissions("blog:collect")
    public OpResult<CollectVO> collect(CollectVO collect){
        OpResult<CollectVO> result;
        if(collect == null || collect.getBlogId() == null){
            result = new OpResult<>(false,null,"点赞错误");
        }else{
//            User user = (User)session.getAttribute("user");
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            collect.setUserId(user==null?null:user.getId());
            result = collectService.collect(collect);
        }
        return result;
    }

    @PostMapping("/cancelCollect/{blogId}")
    @Description("取消收藏")
    @RequiresPermissions("blog:cancelCollect")
    public OpResult<Long> cancelCollect(@PathVariable("blogId")Long blogId){
//        User user = (User)session.getAttribute("user");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return collectService.cancelCollect(user==null?null:user.getId(),blogId);
    }

}
