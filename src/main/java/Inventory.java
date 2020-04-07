// package main.java;

import java.util.*;

public class Inventory {
    private int userID;
    private int friendID;
    private SMDate timesent;
    private int cropID;


    public Inventory(int userID, int friendID, SMDate timesent, int cropID) {
        this.userID = userID;
        this.friendID = friendID;
        this.timesent = timesent;
        this.cropID = cropID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getFriendID() {
        return friendID;
    }

    public void setFriendID(int friendID) {
        this.friendID = friendID;
    }

    public SMDate getTimesent() {
        return timesent;
    }

    public void setTimesent(SMDate timesent) {
        this.timesent = timesent;
    }

    public int getCropID() {
        return cropID;
    }

    public void setCropID(int cropID) {
        this.cropID = cropID;
    }
}