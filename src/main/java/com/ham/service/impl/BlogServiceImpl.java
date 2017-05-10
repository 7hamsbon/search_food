package com.ham.service.impl;

import com.ham.constance.EntityConst;
import com.ham.dao.BlogMapper;
import com.ham.service.BlogService;
import com.ham.service.UserService;
import com.ham.service.strategy.BaseBlogStrategy;
import com.ham.service.strategy.TimeOrderBlogStrategy;
import com.ham.util.CacheUtils;
import com.ham.util.RedisUtils;
import com.ham.vo.BlogVO;
import com.ham.vo.OpResult;
import com.mysql.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hamsbon on 2017/2/20.
 */
@Service
public class BlogServiceImpl  implements BlogService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private TimeOrderBlogStrategy strategy;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);

    @Override
    public OpResult<BlogVO> publish(BlogVO blog) {
        OpResult<BlogVO> result = new OpResult<BlogVO>(false,null,"发布失败");
        if(blog == null){
            logger.debug("博文为空");
            result.setOpMsg("博文为空");
        }else if(blog.getUserId() == null || blog.getUserId() < 0){
            logger.debug("用户不存在");
            result.setOpMsg("用户不存在");
        }else if(StringUtils.isNullOrEmpty(blog.getContent()) && StringUtils.isNullOrEmpty(blog.getDescription()) && StringUtils.isNullOrEmpty(blog.getPhotoPath())){
            result.setOpMsg("请按要求完成输入再发布");
        }else{
            if(StringUtils.isNullOrEmpty(blog.getPhotoPath())){
                blog.setPhotoPath(EntityConst.DEFAULT_FOOD_URL);
            }
            blog.setDescription(blog.getDescription().replace("\n","<br />"));
            blog.setContent(blog.getContent().replace("\n","<br />"));
            int opResult = blogMapper.insertSelective(blog);
            if(opResult>0){
                result.setSuccess(true);
                result.setOpMsg("发布博文成功");
                result.setData(blog);
                String key = CacheUtils.getKey(CacheUtils.PREFIX_USER,blog.getUserId(),CacheUtils.SUBFIX_USER_BLOG_NUM);
                RedisUtils.incr(key);
            }
        }
        return result;
    }

    @Override
    public OpResult<String> delete(Long id) {
        OpResult<String> result = new OpResult<>(false,null,"删除失败");
        try{
            Long userId = blogMapper.selectByPrimaryKey(id).getUserId();
            if(blogMapper.deleteByPrimaryKey(id)>0){
                result.setSuccess(true);
                result.setOpMsg("删除成功");
                String key = CacheUtils.getKey(CacheUtils.PREFIX_USER,userId,CacheUtils.SUBFIX_USER_BLOG_NUM);
                RedisUtils.decr(key);
            }else{
                result.setOpMsg("博客不存在");
            }

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return result;
    }

    @Override
    public OpResult<List<BlogVO>> getBlogsByUserIds(BaseBlogStrategy baseBlogStrategy, List<Long> ids) {
        OpResult<List<BlogVO>> opResult = new OpResult<>(false,"数据加载失败");
        if(baseBlogStrategy!=null && ids!=null && ids.size()>0){
            List<BlogVO> blogs = baseBlogStrategy.getBlogsByIds(ids);
            if(blogs!=null && blogs.size()>0){
                opResult.setSuccess(true);
                opResult.setData(blogs);
                opResult.setOpMsg("");
            }else{
                opResult.setOpMsg("没有更多数据了！");
            }
        }
        return opResult;
    }

    @Override
    public OpResult<List<BlogVO>> fuzzySearchBlogByKeyword(String keyword) {
        OpResult<List<BlogVO>> result = new OpResult<>(false,null,"请输入正确的查询词!");
        if(!StringUtils.isNullOrEmpty(keyword)){
            List<BlogVO> list = blogMapper.fuzzySearch(keyword);
            if(list != null && list.size()>0){

                strategy.loadCommentCount(strategy.loadLikeNum(list));
                result.setSuccess(true);
                result.setOpMsg("");
                result.setData(list);
            }else{
                result.setOpMsg("博客结果为空！");
            }
        }
        return result;
    }
}
