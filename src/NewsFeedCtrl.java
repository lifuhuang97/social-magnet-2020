import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class NewsFeedCtrl {
    private UserProfile currentUser;

    public NewsFeedCtrl(UserProfile currentUser) {
        this.currentUser = currentUser;
    }

    public LinkedHashMap <Post, ArrayList<Comment>> retrieveNewsFeed() {
        int currentUserId = currentUser.getUserId();
        LinkedHashMap <Post, ArrayList<Comment>> newsfeed = new LinkedHashMap<>();

        // Get a list of the current user's friends
        ArrayList<UserProfile> friends = FriendsDAO.getFriendsByUserId(currentUserId);
        
        // Populate all of their ids (along with the current user's id)
        ArrayList<Integer> listOfIdsToFindPostBy = new ArrayList<>();
        listOfIdsToFindPostBy.add(currentUserId);
        for (UserProfile friend : friends) {
            listOfIdsToFindPostBy.add(friend.getUserId());
        }

        newsfeed = PostUtility.retrievePostsByUserIds(listOfIdsToFindPostBy);

        return newsfeed;
    }    

    public HashMap<Post, ArrayList<Comment>> retrieveThread(LinkedHashMap <Post, ArrayList<Comment>> newsfeed, int num) {
        HashMap<Post, ArrayList<Comment>> thread = new HashMap<>();

        int counter = 1;
        for (Post post : newsfeed.keySet()) {
            if (counter == num) {
                thread.put(post, newsfeed.get(post));
            }
            counter += 1;
        }

        return thread;
    }
}