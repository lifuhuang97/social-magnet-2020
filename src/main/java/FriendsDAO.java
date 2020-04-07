// package main.java;
import java.util.*;

public class FriendsDAO {
    public static ArrayList<UserProfile> getFriendsByUserId (int userId) {
        ArrayList<UserProfile> friends = new ArrayList<>();

        String stmt = "SELECT FRIENDID FROM FRIENDS WHERE USERID = '" + userId + "';";

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

    public static boolean isFriends(int userId, int friendId) {
        String stmt = "SELECT FRIENDID FROM FRIENDS WHERE USERID = '" + userId + "'AND FRIENDID = '" + friendId + "';";

        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);

        if (results.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public static void unfriend (int userId, int friendId) {

        String stmt = "DELETE FROM FRIENDS WHERE USERID = '" + userId + "' AND FRIENDID = '" + friendId + "';";

        DataUtility.queryUpdate(stmt);

        // since relationships are bidirectional 
        stmt = "DELETE FROM FRIENDS WHERE USERID = '" + friendId + "' AND FRIENDID = '" + userId + "';";

        DataUtility.queryUpdate(stmt);

    }

    public static void addFriend (int userId, int friendId) {
        
        String stmt = "INSERT INTO FRIENDS (`userID`, `friendID`) VALUES ('" + userId + "', '" + friendId + "');";

        DataUtility.queryUpdate(stmt);
    }
}