// package main.java;

import java.util.ArrayList;
import java.util.Collections;

public class FriendsCtrl {

    private UserProfile currentUser;

    /**
     * Constructs a FriendsCtrl objet
     * @param currentUser userProfile object current user
     */
    public FriendsCtrl(UserProfile currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Retrieve ArrayList of UserProfile object who are friends with user
     * @param userId userId of the user
     * @return ArrayList of UserProfile objects
     */
    public ArrayList<UserProfile> getFriendsList(int userId) {
        return FriendsDAO.getFriendsByUserId(userId);
    }

    /**
     * Retrieve the number of friends to display 
     * > If more than 3 friends, return 3. 
     * > If less than 3 friends, return the original number.
     * @param userId userId of the user
     * @return ArrayList of UserProfile objects
     */
    public int getNumOfFriends(int userId) {
        return (FriendsDAO.getFriendsByUserId(userId).size() > 3) ? 3 : FriendsDAO.getFriendsByUserId(userId).size();
    }

    /**
     * Populate an ArrayList of UserProfile objects who are friends or requested to be friends with user
     * @param userId userId of the user
     * @return ArrayList of UserProfile objects
     */
    public ArrayList<UserProfile> populateList(int userId) {
        ArrayList<UserProfile> friends = getFriendsList(userId);
        Collections.sort(friends);

        ArrayList<UserProfile> requesters = FriendRequestsDAO.getFriendRequestsByUserId(userId);
        Collections.sort(requesters);

        friends.addAll(requesters);
        return friends;
    }

    /**
     * Unfriend user
     * @param userId userId of the user to unfriend
     */
    public void unfriend (UserProfile friend) {
        FriendsDAO.unfriend(currentUser.getUserId(), friend.getUserId());
    }

    /**
     * Request to be friends with user
     * @param userId userId of requested user
     */
    public void request (UserProfile friend) {

        FriendRequestsDAO.addRequest(currentUser.getUserId(), friend.getUserId());

    }

    /**
     * Accepts a user request
     * @param userId userId of the user to accept
     */
    public void accept (UserProfile friend) {
        
        // delete request from friend_requests db 
        reject(friend);

        // add into friends db 
        FriendsDAO.addFriend(currentUser.getUserId(), friend.getUserId());
        FriendsDAO.addFriend(friend.getUserId(), currentUser.getUserId());
        
    }

    /**
     * Rejects a user request
     * @param userId userId of the user to reject
     */
    public void reject (UserProfile friend) {
        // delete request from friend_requests db 
        FriendRequestsDAO.removeRequest(friend.getUserId(),currentUser.getUserId());
    }

    /**
     * Retrieve UserProfile of user based on username
     * @param username username of the user
     * @return UserProfile object
     */
    public UserProfile getUser(String username) {
        return UserProfileDAO.getUserProfileByUsername(username);    
    }
}