package com.ham.service;

import com.ham.vo.CommentVO;
import com.ham.vo.OpResult;

import java.util.List;

/**
 * Created by hamsbon on 2017/3/4.
 */
public interface CommentService {

    /**
     * 发表评论
     */
    OpResult<CommentVO> comment(CommentVO comment);

    /**
     * 根据博客id获得所有评论
     */
    OpResult<List<CommentVO>> getCommentByBlogId(Long id);
}
