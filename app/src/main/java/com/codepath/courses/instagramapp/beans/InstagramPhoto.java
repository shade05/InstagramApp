package com.codepath.courses.instagramapp.beans;

/**
 * Created by deepaks on 9/28/15.
 */
public class InstagramPhoto {
    public String username;
    public String caption;
    public String createdTime;
    public String imageUrl;
    public String profileUrl;
    public String comment1;
    public String user1;
    public String comment2;
    public String user2;
    public String id;
    public int imageHeight;
    public int likesCount;
    public int commentsCount;

    public String getRelativeTime() {
        return "";
    }


    @Override
    public String toString() {
        return "InstagramPhoto{" +
                "caption='" + caption + '\'' +
                ", username='" + username + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                ", comment1='" + comment1 + '\'' +
                ", user1='" + user1 + '\'' +
                ", comment2='" + comment2 + '\'' +
                ", user2='" + user2 + '\'' +
                ", id='" + id + '\'' +
                ", imageHeight=" + imageHeight +
                ", likesCount=" + likesCount +
                ", commentsCount=" + commentsCount +
                '}';
    }
}