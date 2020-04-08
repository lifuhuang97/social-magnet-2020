package project.magnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import project.utilities.*;

public class ViewThreadCtrl {
    private UserProfile currentUser;

    /**
     * Constructs a ViewThreadCtrl objet
     * @param currentUser userProfile object current user
     */
    public ViewThreadCtrl(UserProfile currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Retrieve ArrayList of UserProfile object who liked the post
     * @param thread thread user liked
     * @return ArrayList of UserProfile objects
     */
    public ArrayList<UserProfile> getPostLikedBy(HashMap<Post, ArrayList<Comment>> thread) {
        ArrayList<UserProfile> users = new ArrayList<>();

        // Obtain post object from thread
        Map<Post, ArrayList<Comment>> map = thread;
        Map.Entry<Post, ArrayList<Comment>> entry = map.entrySet().iterator().next();
        Post post = entry.getKey();

        ArrayList<Integer> userIds = PostLikeDAO.getUserIdByPostId(post.getPostId());

        for (int userId : userIds) {
            users.add(UserProfileDAO.getUserProfileByUserId(userId));
        }

        return users;
    }

    /**
     * Retrieve ArrayList of UserProfile object who disliked the post
     * @param thread thread user disliked
     * @return ArrayList of UserProfile objects
     */
    public ArrayList<UserProfile> getPostDislikedBy(HashMap<Post, ArrayList<Comment>> thread) {
        ArrayList<UserProfile> users = new ArrayList<>();

        // Obtain post object from thread
        Map<Post, ArrayList<Comment>> map = thread;
        Map.Entry<Post, ArrayList<Comment>> entry = map.entrySet().iterator().next();
        Post post = entry.getKey();

        ArrayList<Integer> userIds = PostDislikeDAO.getUserIdByPostId(post.getPostId());

        for (int userId : userIds) {
            users.add(UserProfileDAO.getUserProfileByUserId(userId));
        }

        return users;
    }

    /**
     * User likes a post
     * @param thread thread user wants to like
     * @param userId userId of user who wants to like
     */
    public void likePost(HashMap<Post, ArrayList<Comment>> thread, int userId) {

        // Obtain post object from thread
        Map<Post, ArrayList<Comment>> map = thread;
        Map.Entry<Post, ArrayList<Comment>> entry = map.entrySet().iterator().next();
        Post post = entry.getKey();

        // check if like before 
        if (!PostLikeDAO.hasLiked(post.getPostId(), userId)) {
            PostDislikeDAO.undislikePostByPostIdAndUserId(post.getPostId(), userId); // remove records if disliked since exclusive
            PostLikeDAO.likePostByPostIdAndUserId(post.getPostId(), userId);
        }
    }

    /**
     * User dislikes a post
     * @param thread thread user wants to dislike
     * @param userId userId of user who wants to dislike
     */
    public void dislikePost(HashMap<Post, ArrayList<Comment>> thread, int userId) {
        // Obtain post object from thread
        Map<Post, ArrayList<Comment>> map = thread;
        Map.Entry<Post, ArrayList<Comment>> entry = map.entrySet().iterator().next();
        Post post = entry.getKey();

        // check if like before 
        if (!PostDislikeDAO.hasDisliked(post.getPostId(), userId)) {
            PostLikeDAO.unlikePostByPostIdAndUserId(post.getPostId(), userId); // remove records if disliked since exclusive
            PostDislikeDAO.dislikePostByPostIdAndUserId(post.getPostId(), userId);
        }

    }

     /**
     * User replies to a post
     * @param thread thread user wants to reply to
     * @param userId userId of user who wants to reply
     */
    public void reply(HashMap<Post, ArrayList<Comment>> thread, String content, int userId) {
        // Obtain post object from thread
        Map<Post, ArrayList<Comment>> map = thread;
        Map.Entry<Post, ArrayList<Comment>> entry = map.entrySet().iterator().next();
        Post post = entry.getKey();

        // insert new comment 
        int commentId = CommentDAO.addComment(content, new SMDate().toString());

        // insert new usercomment
        UserCommentDAO.addUserComment(userId, commentId);

        // insert postcomment 
        PostCommentDAO.addPostComment(post.getPostId(), commentId);

    }

}