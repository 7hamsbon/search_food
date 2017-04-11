package com.ham.service;

import com.ham.vo.BlogVO;
import com.ham.vo.CollectVO;
import com.ham.vo.OpResult;

import java.util.List;

/**
 * Created by hamsbon on 2017/3/8.
 * 收藏Service
 */
public interface CollectService {

    /**
     * 收藏博文
     */
    OpResult<CollectVO> collect(CollectVO collect);

    /**
     * 取消收藏
     */
    OpResult<Long> cancelCollect(Long userId,Long blogId);

    /**
     * 装配是否收藏
     */
    List<BlogVO> loadIsCollect(List<BlogVO> targetList,Long userId);
}
