package com.ham.entity;

import java.util.Date;

public class Comment {
    private Long id;

    private Long userId;

    private Long blogId;

    private String content;

    private Date ctime;

    private Long replayId;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", blogId=" + blogId +
                ", content='" + content + '\'' +
                ", ctime=" + ctime +
                ", replayId=" + replayId +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Long getReplayId() {
        return replayId;
    }

    public void setReplayId(Long replayId) {
        this.replayId = replayId;
    }
}