package com.ham.web;

import com.ham.entity.User;
import com.ham.service.CommentService;
import com.ham.vo.CommentVO;
import com.ham.vo.OpResult;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hamsbon on 2017/3/9.
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/publish")
    @Description("发表评论")
//    @RequiresPermissions("comment:publish")
    public OpResult<CommentVO> publish(CommentVO comment){
//        User u = (User)session.getAttribute("user");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        comment.setUserId(user==null?null:user.getId());
        return commentService.comment(comment);
    }

    @GetMapping("/{blogId}")
    public OpResult<List<CommentVO>> getComments(@PathVariable("blogId")Long blogId){
        return commentService.getCommentByBlogId(blogId);
    }

}
