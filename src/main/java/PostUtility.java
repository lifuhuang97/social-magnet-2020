// package main.java;

import java.util.*;

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
    
}