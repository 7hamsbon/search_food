package com.ham.service.impl;

import com.ham.dao.CommentMapper;
import com.ham.entity.CommentExample;
import com.ham.service.CommentService;
import com.ham.service.UserService;
import com.ham.service.strategy.PersonalUserStrategy;
import com.ham.util.CacheUtils;
import com.ham.util.RedisUtils;
import com.ham.vo.CommentVO;
import com.ham.vo.OpResult;
import com.ham.vo.UserVO;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hamsbon on 2017/3/4.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonalUserStrategy personalUserStrategy;

    @Override
    public OpResult<CommentVO> comment(CommentVO comment) {
        OpResult<CommentVO> result = new OpResult<>(false,null,"发表评论失败！");
        if(comment!=null && comment.getUserId()!=null && comment.getBlogId()!=null && !StringUtils.isNullOrEmpty(comment.getContent())){
            if(commentMapper.insertSelective(comment)>0){
                result.setSuccess(true);
                result.setOpMsg("发表评论成功");
                UserVO u = userService.getUserInfo(personalUserStrategy, comment.getUserId()).getData().get(0);
                comment.setUserName(u.getUsername());
                comment.setUserPhoto(u.getPhotoUrl());
                result.setData(comment);
                //缓存中的博客评论数自增
                String key = CacheUtils.getKey(CacheUtils.PREFIX_BLOG,comment.getBlogId(),CacheUtils.SUBFIX_BLOG_COMMENT_NUM);
                RedisUtils.incr(key);
            }
        }else if(comment.getUserId()==null){
            result.setOpMsg("请先登录");
        }
        return result;
    }

    @Override
    public OpResult<List<CommentVO>> getCommentByBlogId(Long id) {
        OpResult<List<CommentVO>> result = new OpResult<>(false,null,"获取评论失败！");
        if(id!=null && id>0){
            CommentExample example = new CommentExample();
            example.setOrderByClause("ctime desc");
            CommentExample.Criteria criteria = example.createCriteria();
            criteria.andBlogIdEqualTo(id);
            List<CommentVO> comments = commentMapper.selectByExample(example);
            result.setSuccess(true);
            result.setData(comments);
            result.setOpMsg("");
        }
        return result;
    }
}
