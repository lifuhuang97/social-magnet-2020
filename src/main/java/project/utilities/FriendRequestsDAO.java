package project.utilities;

import java.util.*;
import project.magnet.UserProfile;

/**
 * This is the FriendRequestsDAO that connects to the FriendRequests Database 
 */

public class FriendRequestsDAO {

    /**
     * Retrieve ArrayList of UserProfile object who requested to be specified user's friend 
     * @param userId userId of the specified user 
     * @return ArrayList of UserProfile objects
     */
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

    /**
     * Removes a specific friend request 
     * @param requesterId userId of user who initiated the friend request
     * @param requestedId userId of the requested user
     */
    public static void removeRequest (int requesterId, int requestedId) {
        // use when rejecting and part one of accepting request

        String stmt = "DELETE FROM FRIEND_REQUESTS WHERE REQUESTERID = '" + requesterId + "' AND REQUESTEDID = '" + requestedId + "';";

        DataUtility.queryUpdate(stmt);

    }

    /**
     * Adds a specific friend request 
     * @param requesterId userId of user who initiated the friend request
     * @param requestedId userId of the requested user
     */
    public static void addRequest (int requesterId, int requestedId) {

        String stmt = "INSERT INTO FRIEND_REQUESTS (`requesterID`, `requestedID`) VALUES ('" + requesterId + "', '" + requestedId + "');";

        DataUtility.queryUpdate(stmt);

    }

}