import java.util.*;

public class ProfileCtrl {
    // private UserDAO UserDAO;
    private FriendsDAO FriendsDAO;

    public ProfileCtrl() {
        // UserDAO = new UserDAO();
    }

    public String retrieveNewsFeed (UserProfile user) {
        int count = 1;
        String result = "== Social Magnet :: News Feed ==\n";
        // Integer[] posts = PostDAO.retrievePosts(user.getUserID());
        // for (Integer post : posts) {
        //     String content = post.getContent();
        //     int likes = post.getLikes();
        //     int dislikes = post.getDislikes();

        //     result += "" + count + " " + post + "\n" + 
        //     "[ " + likes + "," + dislikes + " ]\n";
        // }
        // return result;
        return null;
    }

    public String retrieveWall (UserProfile user) {
        String result = "== Social Magnet :: My Wall ==";
        result += PostDAO.retrieveWall(user.getUserId());
        return result;
    }

    public ArrayList<UserProfile> retrieveFriends (UserProfile user) {
        // ArrayList<User> result = FriendsDAO.getFriends(user.getUserID());
        // return result;
        return new ArrayList<UserProfile>();
    }
}
