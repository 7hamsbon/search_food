package com.ham.service.strategy;

import com.ham.dao.CollectMapper;
import com.ham.dao.CommentMapper;
import com.ham.dao.LikeitMapper;
import com.ham.util.CacheUtils;
import com.ham.util.RedisUtils;
import com.ham.vo.BlogVO;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hamsbon on 2017/2/28.
 * 博客策略类，子类继承时可以根据不同的策略对博客数据进行操作
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
public abstract class BaseBlogStrategy {

    @Autowired
    private LikeitMapper likeitMapper;

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private CommentMapper commentMapper;

    private Jedis jedis ;

    private Jedis getJedis(){
        if(jedis==null){
            jedis = RedisUtils.getJedis();
        }
        return jedis;
    }

    /**
     * 根据用户id集获得博客集
     */
    public abstract List<BlogVO> getBlogsByIds(List<Long> ids);


    /**
     * 根据博客id获得博客的点赞数
     */
    public List<BlogVO> loadLikeNum(List<BlogVO> blogList){
        List<Long> ids = new ArrayList<>(blogList.size()+1);
        ids.add(-1L);
        Map<Long,Integer> map = new HashMap<>(blogList.size());

        for(int i = 0,size = blogList.size();i<size;i++){
            BlogVO blog = blogList.get(i);
            Long id = blog.getId();
            //从缓存中查
            String key = CacheUtils.getKey(CacheUtils.PREFIX_BLOG , id ,CacheUtils.SUBFIX_LIKE_NUM);
            String cacheValue = null;
            if(getJedis() != null){
                cacheValue = getJedis().get(key);
            }
            if(StringUtils.isNullOrEmpty(cacheValue)){
                ids.add(id);
                //将id和索引联系起来，后面查出结果时有用
                map.put(id,i);
            }else{
                blog.setLikeCount(Long.parseLong(cacheValue));
            }
        }
        List<Map<String,Long>> counts = likeitMapper.selectLikeNumByBlogIds(ids);

        for(Map<String,Long> record:counts){

            Long id = record.get("id");
            Long count = record.get("like_num");

            Integer index = map.get(id);
            blogList.get(index).setLikeCount(count);

//            设置缓存
            if(getJedis()!=null){
                String key = CacheUtils.getKey(CacheUtils.PREFIX_BLOG , id ,CacheUtils.SUBFIX_LIKE_NUM);
                getJedis().set(key,String.valueOf(count));
            }
        }
        return blogList;
    }

    /**
     * 根据博客id获得博客的评论数
     */
    public List<BlogVO> loadCommentCount(List<BlogVO> blogList){
        List<Long> ids = new ArrayList<>(blogList.size()+1);
        ids.add(-1L);
        Map<Long,Integer> map = new HashMap<>(blogList.size());

        for(int i = 0,size = blogList.size();i<size;i++){
            BlogVO blog = blogList.get(i);
            Long id = blog.getId();
            //从缓存中查
            String key = CacheUtils.getKey(CacheUtils.PREFIX_BLOG , id ,CacheUtils.SUBFIX_BLOG_COMMENT_NUM);
            String cacheValue = null;
            if(getJedis() != null){
                cacheValue = getJedis().get(key);
            }
            if(StringUtils.isNullOrEmpty(cacheValue)){
                ids.add(id);
                //将id和索引联系起来，后面查出结果时有用
                map.put(id,i);
            }else{
                blog.setCommentCount(Long.parseLong(cacheValue));
            }
        }

        List<Map<String,Long>> counts = commentMapper.selectCommentNumByBlogIds(ids);

        for(Map<String,Long> record:counts){

            Long id = record.get("id");
            Long count = record.get("comment_num");

            Integer index = map.get(id);
            blogList.get(index).setCommentCount(count);

            //设置缓存
            if(getJedis()!=null){
                String key = CacheUtils.getKey(CacheUtils.PREFIX_BLOG , id ,CacheUtils.SUBFIX_BLOG_COMMENT_NUM);
                getJedis().set(key,String.valueOf(count));
            }
        }

        return blogList;
    }

}
