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

    public int retrieveNumofLikes(int postId) {
        return PostLikeDAO.getUserIdByPostId(postId).size();
    }

    public int retrieveNumOfDislikes(int postId) {
        return PostDislikeDAO.getUserIdByPostId(postId).size();
    }

    public String retrieveUsername(Post post) {
        return UserProfileDAO.getUserProfileByUserId(UserPostDAO.getUserIdByPostId(post.getPostId())).getUsername();
    }

    public String retrieveCommentUsername(Comment comment) {
        return UserProfileDAO.getUserProfileByUserId(UserCommentDAO.getUserIdByCommentId(comment.getCommentId())).getUsername();
    }
}