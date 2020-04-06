package main.java;

import java.util.*;

public class FriendsDAO {
    public static ArrayList<UserProfile> getFriendsByUserId (int userID) {
        ArrayList<UserProfile> friends = new ArrayList<>();

        String stmt = "SELECT FRIENDID FROM FRIENDS WHERE USERID = '" + userID + "';";

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

    public static ArrayList<UserProfile> getRequestsByUserId (int UserID) {
        ArrayList<UserProfile> requestList = new ArrayList<>();

        return requestList;
    }

    public static boolean unfriend (int userID, int friendID) {
        boolean result = false;
        String stmt = "DELETE USERID FROM FRIENDS WHERE USERID = '" + userID + "' AND FRIENDID = '" + friendID + "';";

        // ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);
        return result;
    }

    public static void addRequest (int userID, int friendID) {
        String stmt = "INSERT INTO FRIEND_REQUESTS VALUES ('" + userID + "', '" + friendID + "' )";
    }

    public static boolean accept (int userID, int friendID) {
        boolean result = false;
        String stmt1 = "DELETE FROM FRIEND_REQUESTS WHERE USERID = " + userID + " AND FRIENDID = " + friendID ;
        String stmt2 = "INSERT INTO FRIENDS VALUES ('" + userID + "', '" + friendID + "' )";
        return result;
    }
}