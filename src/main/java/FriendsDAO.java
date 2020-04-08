// package main.java;
import java.util.*;

public class FriendsDAO {

    /**
     * Retrieves all UserProfile objects who are friends with user 
     * @param userId userId of user 
     * @return ArrayList of UserProfile objects 
     */
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

    /**
     * Check if user is friends with another user
     * @param userId userId of first user 
     * @param friendId userId of second user 
     * @return friendship status
     */
    public static boolean isFriends(int userId, int friendId) {
        String stmt = "SELECT FRIENDID FROM FRIENDS WHERE USERID = '" + userId + "'AND FRIENDID = '" + friendId + "';";

        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);

        if (results.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Removes a specific friend 
     * @param userId userId of user who wishes to delete the other user as a friend 
     * @param friendId userId of the other user to be deleted as a friend 
     */
    public static void unfriend (int userId, int friendId) {

        String stmt = "DELETE FROM FRIENDS WHERE USERID = '" + userId + "' AND FRIENDID = '" + friendId + "';";

        DataUtility.queryUpdate(stmt);

        // since relationships are bidirectional 
        stmt = "DELETE FROM FRIENDS WHERE USERID = '" + friendId + "' AND FRIENDID = '" + userId + "';";

        DataUtility.queryUpdate(stmt);

    }

    /**
     * Adds a specific friend 
     * @param userId userId of user who wants to add the other user
     * @param friendId userId of user to be added as a friend 
     */
    public static void addFriend (int userId, int friendId) {
        
        String stmt = "INSERT INTO FRIENDS (`userID`, `friendID`) VALUES ('" + userId + "', '" + friendId + "');";

        DataUtility.queryUpdate(stmt);

        // String bidirectionalStmt = "INSERT INTO FRIENDS (`userID`,`friendID`) VALUES ('" + friendId + "', '" + userId + "');";
        // DataUtility.queryUpdate(bidirectionalStmt);
    }
}