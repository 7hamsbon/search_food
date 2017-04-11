package com.ham.entity;

import java.util.Date;

public class Friendship {
    private Long id;

    private Long fans;

    private Long follower;

    private Date friendTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFans() {
        return fans;
    }

    public void setFans(Long fans) {
        this.fans = fans;
    }

    public Long getFollower() {
        return follower;
    }

    public void setFollower(Long follower) {
        this.follower = follower;
    }

    public Date getFriendTime() {
        return friendTime;
    }

    public void setFriendTime(Date friendTime) {
        this.friendTime = friendTime;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id=" + id +
                ", fans=" + fans +
                ", follower=" + follower +
                ", friendTime=" + friendTime +
                '}';
    }
}