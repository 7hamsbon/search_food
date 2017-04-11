package com.ham.vo;

import com.ham.entity.Comment;

/**
 * Created by hamsbon on 2017/3/7.
 */
public class CommentVO extends Comment {

    private String userPhoto;

    private String userName;

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "CommentVO{" +
                super.toString() +
                "userPhoto='" + userPhoto + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
