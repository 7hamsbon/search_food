package com.ham.service;

import com.ham.entity.Likeit;
import com.ham.vo.BlogVO;
import com.ham.vo.OpResult;

import java.util.List;

/**
 * Created by hamsbon on 2017/2/23.
 */
public interface LikeService {

    /**
     * 点赞
     */
    OpResult<String> like(Likeit likeit);

    /**
     * 取消点赞
     */
    OpResult<String> cancel(Likeit likeit);

    /**
     * 填充是否点赞
     */
    List<BlogVO> loadIsLike(List<BlogVO> target,Long userId);

}
