// package main.java;

import java.util.*;

public class FriendRequestsDAO {
    public static ArrayList<UserProfile> getFriendRequestsByUserId (int userId) {
        ArrayList<UserProfile> friends = new ArrayList<>();

        String stmt = "SELECT REQUESTERID FROM FRIEND_REQUESTS WHERE REQUESTEDID = '" + userId + "';";

        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);

        if (results.size() == 0) {
            return friends;
        } else {
            for (ArrayList<String> result : results) {
                int friendId = Integer.parseInt(result.get(0));
                UserProfile friend = UserProfileDAO.getUserProfileByUserId(friendId);
                friends.add(friend);
            }
        }

        return friends;
    }

    // use when rejecting and part one of accepting request
    public static void removeRequest (int requesterId, int requestedId) {

        String stmt = "DELETE FROM FRIEND_REQUESTS WHERE REQUESTERID = '" + requesterId + "' AND REQUESTEDID = '" + requestedId + "';";

        DataUtility.queryUpdate(stmt);

    }

    public static void addRequest (int requesterId, int requestedId) {

        String stmt = "INSERT INTO FRIEND_REQUESTS (`requesterID`, `requestedID`) VALUES ('" + requesterId + "', '" + requestedId + "');";

        DataUtility.queryUpdate(stmt);

    }

}