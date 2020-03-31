import java.util.*;

public class PostUtility {
    public static LinkedHashMap <Post, ArrayList<Comment>> retrievePostsByUserIds(ArrayList<Integer> listOfIdsToFindPostBy) {
        // FIXME: The first UserID is to be used to find posts that tagged that ID --  might generate duplicates 

        LinkedHashMap <Post, ArrayList<Comment>> to_return = new LinkedHashMap<>();

        // Retrieve arraylists of postids by userids
        ArrayList<Integer> postIds = new ArrayList<>();
        for (Integer idToFindPostBy : listOfIdsToFindPostBy) {
            for(Integer postId : UserPostDAO.getPostIdsByUserId(idToFindPostBy)) {
                postIds.add(postId);
            }
        }

        // Insert here 

        // Retrieve an arraylist of all the posts
        ArrayList<Post> posts = PostDAO.retrievePostsByUserIds(postIds);

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

            for (Integer commentId : commentIds) {
                comments.add(CommentDAO.retrieveCommentById(Integer.valueOf(commentId)));
            }

            to_return.put(topFivePost, comments);
        }

        return to_return;
    }

    public static void displayPosts(Map<Post, ArrayList<Comment>> posts, int outsideCounter){   

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
    
}