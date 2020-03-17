
import java.util.*;

public class Gift {
    private int userID;
    private int friendID;
    private SMDate timeSent;
    private int cropID;

    public Gift(int userID, int friendID, SMDate timeSent, int cropID) {
        this.userID = userID;
        this.friendID = friendID;
        this.timeSent = timeSent;
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

    public SMDate getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(SMDate timeSent) {
        this.timeSent = timeSent;
    }

    public int getCropID() {
        return cropID;
    }

    public void setCropID(int cropID) {
        this.cropID = cropID;
    }
}