package project.farm;

import java.util.*;

public class Gift {
    private int userId;
    private int friendId;
    private int postId;
    private int cropId;

    /**
     * Constructs a specified Gift object
     * @param userId the userId of the user sending the gift
     * @param friendId  the userId of the receiver of the gift
     * @param postId the postId generated in response to gift sent 
     * @param cropId the cropId sent as the gift
     */
    public Gift(int userId, int friendId, int postId, int cropId) {
        this.userId = userId;
        this.friendId = friendId;
        this.postId = postId;
        this.cropId = cropId;
    }

    /**
     * Gets the userId
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the userId
     * @param userId userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the friendId
     * @return the friendId
     */
    public int getFriendId() {
        return friendId;
    }

    /**
     * Sets the friendId
     * @param friendId friendId
     */
    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    /**
     * Gets the postId
     * @return the postId
     */
    public int getPostId() {
        return postId;
    }

    /**
     * Sets the postId
     * @param postId postId
     */
    public void setPostId(int postId) {
        this.postId = postId;
    }

    /**
     * Gets the cropId
     * @return the cropId
     */
    public int getCropId() {
        return cropId;
    }

    /**
     * Sets the cropId
     * @param cropId cropId
     */
    public void setCropId(int cropId) {
        this.cropId = cropId;
    }
}