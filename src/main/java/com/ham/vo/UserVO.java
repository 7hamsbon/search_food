package com.ham.vo;

import com.ham.entity.User;

import java.util.Date;

/**
 * Created by hamsbon on 2017/2/25.
 */
public class UserVO extends User {

    private Long fansNum;

    private Long followerNum;

    private Long likeNum;

    private Long blogNum;

    private Date friendTime;

    @Override
    public String toString() {
        return "UserVO{" +
                super.toString() +
                "fansNum=" + fansNum +
                ", followerNum=" + followerNum +
                ", likeNum=" + likeNum +
                ", blogNum=" + blogNum +
                ", friendTime=" + friendTime +
                '}';
    }

    public Long getFansNum() {
        return fansNum;
    }

    public void setFansNum(Long fansNum) {
        this.fansNum = fansNum;
    }

    public Long getFollowerNum() {
        return followerNum;
    }

    public void setFollowerNum(Long followerNum) {
        this.followerNum = followerNum;
    }

    public Long getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Long likeNum) {
        this.likeNum = likeNum;
    }

    public Long getBlogNum() {
        return blogNum;
    }

    public void setBlogNum(Long blogNum) {
        this.blogNum = blogNum;
    }

    public Date getFriendTime() {
        return friendTime;
    }

    public void setFriendTime(Date friendTime) {
        this.friendTime = friendTime;
    }
}
