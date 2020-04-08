package project.utilities;

import java.util.*;
import project.magnet.Post;
import project.magnet.Comment;
import project.farm.Gift;
import project.exception.*;

/**
 * This is the PostUtility that contains methods that are used across multiple files for post-related actions
 */

public class PostUtility {
    /**
     * Retreive all the Post and Comments objects made by an ArrayList of userIds 
     * @param listOfIdsToFindPostBy An ArrayList containing the userIds to find posts by
     * @return A LinkHashMap of Post objects for key and ArrayList of Comment objects for value
     */
    public static LinkedHashMap <Post, ArrayList<Comment>> retrievePostsByUserIds(ArrayList<Integer> listOfIdsToFindPostBy) {

        LinkedHashMap <Post, ArrayList<Comment>> to_return = new LinkedHashMap<>();
        ArrayList<Integer> postIds = new ArrayList<>();

        // Retrieve arraylists of postids by userids (from USER_WALL db)
        for (int idToFindPostBy : listOfIdsToFindPostBy) {
            for(int postId : UserWallDAO.getPostIdsByUserId(idToFindPostBy)) {
                postIds.add(postId);
            }
        }

        // Retrieve arraylists of postids that tagged the userids (check for duplicates by pid)
        for (int idToFindPostBy : listOfIdsToFindPostBy) {
            for(int postId : PostTagDAO.getPostIdsByTagId(idToFindPostBy)) {
                boolean insert = true;
                for (int existingPostId : postIds) {
                    if (existingPostId == postId) {
                        insert = false;
                    }
                }
                if (insert) {
                    postIds.add(postId);
                }
            }
        }

        // Retrieve an arraylist of all the posts
        ArrayList<Post> posts = PostDAO.retreivePostsByPostsId(postIds);

        ArrayList<Post> topFivePosts = new ArrayList<>();

        int n = 5;
        while (!(posts.isEmpty()) && n != 0) {
            Post max = posts.get(0);
            for (Post post : posts) {
                if (post.getDateTime().after(max.getDateTime())) {
                    max = post;
                }
            }
            topFivePosts.add(max);
            posts.remove(max);
            n--;
        }

        for (Post topFivePost : topFivePosts) {
            // Get all the comment ids associated with post
            ArrayList<Integer> commentIds = PostCommentDAO.getCommentIdsByPostId(topFivePost.getPostId());

            ArrayList<Comment> comments = new ArrayList<>();
            ArrayList<Comment> arranged_comments = new ArrayList<>();

            for (Integer commentId : commentIds) {
                comments.add(CommentDAO.retrieveCommentById(Integer.valueOf(commentId)));
            }

            int m = 3;
            while (!(comments.isEmpty()) && m != 0) {
                Comment maxC = comments.get(0);
                for (Comment comment : comments) {
                    if (comment.getDateTime().after(maxC.getDateTime())) {
                        maxC = comment;
                    }
                }
                arranged_comments.add(maxC);
                comments.remove(maxC);
                m--;
            }

            to_return.put(topFivePost, arranged_comments);
        }

        return to_return;
    }
    /**
     * Display the Post and Comment objects 
     * @param posts A Map of Post objects for key and Comment objects for value
     * @param outsideCounter Identify the counter to start numbering the threads from
     */

    public static void display(Map<Post, ArrayList<Comment>> posts, int outsideCounter){   

        for (Post post : posts.keySet()) {
            int insiderCounter = 1;
            ArrayList<Comment> comments = posts.get(post);
            String username = UserProfileDAO.getUserProfileByUserId(UserPostDAO.getUserIdByPostId(post.getPostId())).getUsername();
            System.out.println(outsideCounter + " " + username + ": " + post.getContent());

            String format = "%2s%2$s.%3$s %4$s: %5$s\n";

            for (int i = 0; i < comments.size(); i++) {
                String comment_username = UserProfileDAO.getUserProfileByUserId(UserCommentDAO.getUserIdByCommentId(comments.get(i).getCommentId())).getUsername();

                System.out.format(format, " ", outsideCounter, insiderCounter, comment_username, comments.get(i).getContent());

                insiderCounter++;
            }

            outsideCounter++;
            System.out.println();
        }
    }

    /**
     * Retreive a specific thread from the LinkedHashMap of Post and Comment objects
     * @param threads LinkedHashMap of Post and Comment objects structured as threads
     * @param num thread number to retreive
     * @return the specific entry within the LinkedHashMap
     */

    public static HashMap<Post, ArrayList<Comment>> retrieveThread(LinkedHashMap <Post, ArrayList<Comment>> threads, int num) {
        HashMap<Post, ArrayList<Comment>> thread = new HashMap<>();

        int counter = 1;
        for (Post post : threads.keySet()) {
            if (counter == num) {
                thread.put(post, threads.get(post));
            }
            counter += 1;
        }

        return thread;
    }

    /**
     * Checks if user has permission to kill thread and calls killThread/killPost accordingly if he/she does
     * @param thread thread user wants to dislike
     * @param userId userId of user who wants to kill the thread
     */
    public static void canKill(HashMap<Post, ArrayList<Comment>> thread, int userId) {
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

        // if gift associated with post and the creator (sender) tries to delete it 
        Gift gift = GiftDAO.retrieveGiftFromPostId(postId);
        if (gift != null && gift.getFriendId() != userId) {
            throw new KillThreadException("You have no rights to delete this thread!");
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
    public static void killWall(HashMap<Post, ArrayList<Comment>> thread, int userId) {
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
    public static void killThread(HashMap<Post, ArrayList<Comment>> thread, int userId) {
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

        // check if this post is associated with a gift 
        Gift gift = GiftDAO.retrieveGiftFromPostId(postId);
        if (gift != null) {
            GiftDAO.deleteUserPostByFriendIdAndPostId(userId, postId);
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