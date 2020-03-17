import java.util.*;

public class FriendsDAO {
    public UserDAO userDAO;

    public FriendsDAO () {

    }

    public ArrayList<User> getFriends (int userID) {
        // ArrayList<User> result = new ArrayList<>();
        // // ResultSet rs = DataUtility.QuerySelect("SELECT friendID from FRIENDS WHERE USERID = "+ userID);

        // while (rs.next()) {
        //     int friendID = rs.getInt("friendID");
        //     User friend = userDAO.retrieveByID(friendID);
        //     result.add(friend);
        // }

        // return result;
    }
}