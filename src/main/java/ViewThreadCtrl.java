package main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

     /**
     * Checks if user has permission to kill thread and calls killThread/killPost accordingly if he/she does
     * @param thread thread user wants to dislike
     * @param userId userId of user who wants to kill the thread
     */
    public void canKill(HashMap<Post, ArrayList<Comment>> thread, int userId) {
        // Obtain post object from thread
        Map<Post, ArrayList<Comment>> map = thread;
        Map.Entry<Post, ArrayList<Comment>> entry = map.entrySet().iterator().next();
        Post post = entry.getKey();
        int postId = post.getPostId();

        boolean tagged = false;
        for (int tagid : PostTagDAO.getTagIdsByPostId(postId)) {
            if (tagid == userId) {
                tagged = true;
            }
        }

        if (UserWallDAO.getUserIdByPostId(postId) == userId || UserPostDAO.getUserIdByPostId(postId) == userId) {
            killThread(thread, userId);
        } else if (tagged) {
            killWall(thread, userId);
        } else {
            throw new KillThreadException("You have no rights to delete this thread!");
        }
    }

     /**
     * Removes post from user's wall
     * @param thread thread user wants to remove
     * @param userId userId of user who wants to remove thread
     */
    public void killWall(HashMap<Post, ArrayList<Comment>> thread, int userId) {
        // Obtain post object from thread
        Map<Post, ArrayList<Comment>> map = thread;
        Map.Entry<Post, ArrayList<Comment>> entry = map.entrySet().iterator().next();
        Post post = entry.getKey();
        int postId = post.getPostId();

        PostTagDAO.deletePostTagByTagId(postId, userId);
    }

    /**
     * Kills thread
     * @param thread thread user wants to remove
     * @param userId userId of user who wants to remove thread
     */
    public void killThread(HashMap<Post, ArrayList<Comment>> thread, int userId) {
        // Obtain post object from thread
        Map<Post, ArrayList<Comment>> map = thread;
        Map.Entry<Post, ArrayList<Comment>> entry = map.entrySet().iterator().next();
        Post post = entry.getKey();
        int postId = post.getPostId();

        // get all commentids by postid
        ArrayList<Integer> commentIds = PostCommentDAO.getCommentIdsByPostId(postId);

        // remove from postcomment
        PostCommentDAO.deletePostComment(postId);

        // remove from usercomment based on commentids
        // remove from comment based on commentids 
        for (int commentId : commentIds) {
            UserCommentDAO.deleteUserComment(commentId);
            CommentDAO.deleteComment(commentId);
        }

        // remove from posttags
        PostTagDAO.deletePostTag(postId);

        // remove from postdislikes
        PostDislikeDAO.deletePostDislike(postId);

        // remove from postlikes
        PostLikeDAO.deletePostLike(postId);

        // remove userwall
        UserWallDAO.deleteUserWallPost(postId);

        // remove userpost
        UserPostDAO.deleteUserPost(postId);

        // remove post
        PostDAO.deletePost(postId);
    }
}