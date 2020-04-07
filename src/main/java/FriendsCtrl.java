// package main.java;

import java.util.ArrayList;
import java.util.Collections;

public class FriendsCtrl {

    private UserProfile currentUser;

    public FriendsCtrl(UserProfile currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<UserProfile> getFriendsList(int userId) {
        return FriendsDAO.getFriendsByUserId(userId);
    }

    public int getNumOfFriends(int userId) {
        return (FriendsDAO.getFriendsByUserId(userId).size() > 3) ? 3 : FriendsDAO.getFriendsByUserId(userId).size();
    }

    public ArrayList<UserProfile> populateList(int userId) {
        ArrayList<UserProfile> friends = getFriendsList(userId);
        Collections.sort(friends);

        ArrayList<UserProfile> requesters = FriendRequestsDAO.getFriendRequestsByUserId(userId);
        Collections.sort(requesters);

        friends.addAll(requesters);
        return friends;
    }

    public void unfriend (UserProfile friend) {
        FriendsDAO.unfriend(currentUser.getUserId(), friend.getUserId());
    }

    public void request (UserProfile friend) {

        FriendRequestsDAO.addRequest(currentUser.getUserId(), friend.getUserId());

    }

    public void accept (UserProfile friend) {
        
        // delete request from friend_requests db 
        reject(friend);

        // add into friends db 
        FriendsDAO.addFriend(currentUser.getUserId(), friend.getUserId());
        FriendsDAO.addFriend(friend.getUserId(), currentUser.getUserId());
        
    }

    public void reject (UserProfile friend) {
        // delete request from friend_requests db 
        FriendRequestsDAO.removeRequest(friend.getUserId(),currentUser.getUserId());
    }

    public UserProfile getUser(String username) {
        return UserProfileDAO.getUserProfileByUsername(username);    
    }
}