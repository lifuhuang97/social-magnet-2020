import java.util.*;

public class FriendsDAO {
    public static ArrayList<UserProfile> getFriendsByUserId (int userID) {
        ArrayList<UserProfile> friends = new ArrayList<>();

        String stmt = "SELECT FRIENDID FROM FRIENDS WHERE USERID = '" + userID + "';";

        ArrayList<ArrayList<String>> results = DataUtility.QuerySelect(stmt);

        if (results.size() == 1) {
            return friends;
        } else {
            results.remove(0);
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
}