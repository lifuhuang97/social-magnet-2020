import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewThreadCtrl {
    private UserProfile currentUser;

    public ViewThreadCtrl(UserProfile currentUser) {
        this.currentUser = currentUser;
    }

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
}