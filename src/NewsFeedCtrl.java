import java.util.*;

public class NewsFeedCtrl {
    public LinkedHashMap <Post, ArrayList<Comment>> retrieveNewsFeed(int currentUserId) {
        LinkedHashMap <Post, ArrayList<Comment>> newsfeed = new LinkedHashMap<>();

        // Get a list of the current user's friends
        ArrayList<UserProfile> friends = FriendsDAO.getFriendsByUserId(currentUserId);
        
        // Populate all of their ids (along with the current user's id)
        ArrayList<Integer> listOfIdsToFindPostBy = new ArrayList<>();
        listOfIdsToFindPostBy.add(currentUserId);
        for (UserProfile friend : friends) {
            listOfIdsToFindPostBy.add(friend.getUserId());
        }

        // Retrieve arraylists of postids by userids
        ArrayList<Integer> postIds = new ArrayList<>();
        for (Integer idToFindPostBy : listOfIdsToFindPostBy) {
            for(Integer postId : UserPostDAO.getPostIdsByUserId(idToFindPostBy)) {
                postIds.add(postId);
            }
        }

        // Retrieve an arraylist of all the posts
        ArrayList<Post> posts = PostDAO.retrievePostsByUserIds(postIds);

        // Find the top (latest) 5 posts 
        ArrayList<Post> topFivePosts = PostDateUtility.getTopNPost(posts, 5);

        for (Post topFivePost : topFivePosts) {
            // Get all the comment ids associated with post
            ArrayList<Integer> commentIds = PostCommentDAO.getCommentIdsByPostId(topFivePost.getPostId());

            ArrayList<Comment> comments = new ArrayList<>();

            for (Integer commentId : commentIds) {
                comments.add(CommentDAO.retrieveCommentById(Integer.valueOf(commentId)));
            }

            newsfeed.put(topFivePost, comments);
        }

        return newsfeed;
    }    
}