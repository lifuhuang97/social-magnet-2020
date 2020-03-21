import java.util.*;

public class PostDateUtility {
    public static ArrayList<Post> getTopNPost(ArrayList<Post> posts, int n) {
        ArrayList<Post> topNPosts = new ArrayList<>();

        while (!(posts.isEmpty()) && n != 0) {
            Post max = posts.get(0);
            for (Post post : posts) {
                if (post.getDateTime().after(max.getDateTime())) {
                    max = post;
                }
            }
            topNPosts.add(max);
            posts.remove(max);
            n--;
        }

        return topNPosts;
    }

    //TODO: Do the same for comments
    
}