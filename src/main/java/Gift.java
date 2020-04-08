// package main.java;

import java.util.*;

public class Gift {
    private int userId;
    private int friendId;
    private int postId;
    private int cropId;

    public Gift(int userId, int friendId, int postId, int cropId) {
        this.userId = userId;
        this.friendId = friendId;
        this.postId = postId;
        this.cropId = cropId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }
}