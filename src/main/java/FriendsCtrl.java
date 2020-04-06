package main.java;

import java.util.ArrayList;

public class FriendsCtrl {

    public ArrayList<UserProfile> getFriendsList (UserProfile userProfile) {
        ArrayList<UserProfile> result = new ArrayList<>();
        int userID = userProfile.getUserId();
        result = FriendsDAO.getFriendsByUserId(userID);
        return result;
    }

    public boolean unfriend (UserProfile userProfile, UserProfile friend) {
        boolean result = false;

        return result;
    }

    public void request (UserProfile userProfile, String username) {
        int userID = userProfile.getUserId();
        int friendID = UserProfileDAO.getUserProfileByUsername(username).getUserId();
        FriendsDAO.addRequest(userID, friendID);
    }

    public boolean accept (UserProfile userProfile, UserProfile friend) {
        boolean result = false;
        int userID = userProfile.getUserId();
        int friendID = friend.getUserId();
        FriendsDAO.accept(userID, friendID);
        return result;
    }
}