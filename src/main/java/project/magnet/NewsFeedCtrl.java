package project.magnet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import project.utilities.*;

public class NewsFeedCtrl {
    private UserProfile currentUser;

    /**
     * Constructs a NewsFeedCtrl objet
     * @param currentUser userProfile object current user
     */
    public NewsFeedCtrl(UserProfile currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Retrieve the news feed of the user in the form of a LinkedHashMap with the key representing the Post object and the values being an ArrayList of Comment objects 
     * @return LinkedHashMap of Post Objects as key and ArrayList of Comment objects as value 
     */
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

    /**
     * Retrieve the number of likes of the specified post
     * @param postId postId of the post 
     * @return the number of likes of specified post
     */
    public int retrieveNumofLikes(int postId) {
        return PostLikeDAO.getUserIdByPostId(postId).size();
    }

    /**
     * Retrieve the number of dislikes of the specified post
     * @param postId postId of the post 
     * @return the number of dislikes of specified post
     */
    public int retrieveNumOfDislikes(int postId) {
        return PostDislikeDAO.getUserIdByPostId(postId).size();
    }

    /**
     * Retrieve the username of the user who created the post
     * @param post Post object 
     * @return username 
     */
    public String retrieveUsername(Post post) {
        return UserProfileDAO.getUserProfileByUserId(UserPostDAO.getUserIdByPostId(post.getPostId())).getUsername();
    }

    /**
     * Retrieve the username of the user who created the comment
     * @param post Comment object 
     * @return username 
     */
    public String retrieveCommentUsername(Comment comment) {
        return UserProfileDAO.getUserProfileByUserId(UserCommentDAO.getUserIdByCommentId(comment.getCommentId())).getUsername();
    }
}