import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

//TODO: DONT PRINT IN CTRL

public class MyWallCtrl {

    private UserProfile user;
    private int userId;

    public MyWallCtrl(UserProfile user){
        this.user = user;
        this.userId = user.getUserId();
    }



    public ArrayList<Post> getTopFivePosts(int id){

        ArrayList<Integer> results = new ArrayList<Integer>();
        ArrayList<Post> relevantPosts = new ArrayList<Post>();

        String getAllOfMyPosts = "SELECT postID FROM user_post where userID = " + id;
        String getAllOfTagPosts = "SELECT postID FROM post_tags where tagID = " + id;

        ArrayList<ArrayList<String>> allMyPosts = DataUtility.querySelect(getAllOfMyPosts);
        ArrayList<ArrayList<String>> allTaggedPosts = DataUtility.querySelect(getAllOfTagPosts);

        // to merge all posts retrieved
        for(ArrayList<String> taggedPost : allTaggedPosts){
            allMyPosts.add(taggedPost);
        }

        Map<Integer, SMDate> post_with_date = new HashMap<Integer,SMDate>();

        

        return relevantPosts;

    }

}