package com.ham.vo;

import com.ham.entity.Blog;

import java.util.Date;

/**
 * Created by hamsbon on 2017/2/21.
 */
public class BlogVO extends Blog {

    private Long likeCount;

    private Long commentCount;

    private Date collectTime;

    private String userHeadPath;

    private String userName;

    private Boolean liked;

    private Boolean collected;


    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public Boolean getCollected() {
        return collected;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    public String getUserHeadPath() {
        return userHeadPath;
    }

    public void setUserHeadPath(String userHeadPath) {
        this.userHeadPath = userHeadPath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    @Override
    public String toString() {
        return "BlogVO{" +
                super.toString() +
                ", userHeadPath=" + userHeadPath +
                ", userName=" + userName +
                ", isLike=" + liked +
                ", isCollect=" + collected +
                ", likeCount=" + likeCount +
                ", commentCount=" + commentCount +
                ", collectTime=" + collectTime+
                '}';
    }
}
