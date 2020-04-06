package main.java;

import java.util.*;

public class PostDAO {
    public static ArrayList<Post> retreivePostsByPostsId (ArrayList<Integer> ids) {
        ArrayList<Post> posts = new ArrayList<>();

        for (int id : ids) {
            Post post = null;
            
            String stmt = "SELECT * FROM POST WHERE POSTID = '" + id + "';";

            ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);

            if (results.size() > 0) {
                int postId = Integer.parseInt(results.get(0).get(0));
                String content = results.get(0).get(1);
                SMDate datetime = new SMDate(results.get(0).get(2));

                post = new Post(postId, content, datetime);
                posts.add(post);
            } 
        }

        return posts;
    }

    public static Post retrievePostByPostId(int id) {
        Post post = null;

        String stmt = "SELECT * FROM POST WHERE POSTID = '" + id + "';";

        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);

        if (results.size() == 0) {
            return post;
        } else {
            int postId = Integer.parseInt(results.get(0).get(0));
            String content = results.get(0).get(1);
            SMDate datetime = new SMDate(results.get(0).get(2));

            post = new Post(postId, content, datetime);

        }

        return post;
    }

    public static void deletePost(int postId) {
        String stmt = "DELETE FROM POST WHERE POSTID = '" + postId + "';";

        DataUtility.queryUpdate(stmt);
    }

    public static int addPost(String postContent, String datetime) {
        String stmt = "INSERT INTO POST (`content`, `datetime`) VALUES ('" + postContent + "', '" + datetime + "');";

        int postId = DataUtility.queryUpdateRetrieveID(stmt);

        return postId;
    }



}