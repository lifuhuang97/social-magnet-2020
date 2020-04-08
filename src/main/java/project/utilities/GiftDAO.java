package project.utilities;

import project.farm.Gift;
import project.magnet.UserProfile;
import project.magnet.Post;

import java.util.ArrayList;
public class GiftDAO {
    /**
     * Retrieve Gift objects for a specific user
     * @param userId userId of the user to check for gifts 
     * @return ArrayList containing Gift objects 
     */
    public static ArrayList<Gift> retrieveGiftsByUserId (int userId) {
        ArrayList<Gift> gifts = new ArrayList<>();

        String stmt = "SELECT * FROM GIFT WHERE FRIENDID = '" + userId + "';";

        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);

        if (results.size() > 0) {
            for (ArrayList<String> result : results) {
                int friendId = Integer.parseInt(result.get(1));
                int postId = Integer.parseInt(result.get(2));
                int cropId = Integer.parseInt(result.get(3));
            
                Gift gift = new Gift(userId, friendId, postId, cropId);
                gifts.add(gift);
            }   
            
        } 

        return gifts;
    }

    /**
     * Remove gift record
     * @param postId postId associated with gift
     * @param friendId friendId associated with gift
     */
    public static void deleteUserPostByFriendIdAndPostId(int friendId, int postId) {
        String stmt = "DELETE FROM GIFT WHERE (`friendID` = '" + friendId + "') AND (`postID` = '" + postId + "');";

        DataUtility.queryUpdate(stmt);
    }

    /**
     * Retrieve post object associated gift record
     * @param postId postId associated with gift
     * @return gift object assciated with postId
     */
    public static Gift retrieveGiftFromPostId(int postId) {
        String stmt = "SELECT * FROM GIFT WHERE POSTID = '" + postId + "';";
        Gift gift = null;
        
        ArrayList<ArrayList<String>> results = DataUtility.querySelect(stmt);

        if (results.size() > 0) {
            int userId = Integer.parseInt(results.get(0).get(0));
            int friendId = Integer.parseInt(results.get(0).get(1));
            int cropId = Integer.parseInt(results.get(0).get(3));
            gift = new Gift(userId, friendId, postId, cropId);
        } 

        return gift;

    }

    /**
     * Check if a gift has been successfully sent
     * @param sender UserProfile object of the sender
     * @param friend UserProfile object of the receiver
     */
    public static boolean checkIfSentGift(UserProfile sender, UserProfile friend){

        int friendId = friend.getUserId();

        String stmt = "SELECT * FROM GIFT WHERE FRIENDID = " + friendId + " AND USERID = " + sender.getUserId();

        ArrayList<ArrayList<String>> result = DataUtility.querySelect(stmt);


        if(result.isEmpty()){
            return false;
        }else{
            String postId = result.get(0).get(2);
            Post post = PostDAO.retrievePostByPostId(Integer.parseInt(postId));
            SMDate sentDateTime = post.getDateTime();
            return SMDate.checkIfSentWithinOneDay(sentDateTime);
        }

    }

    /**
     * Create a new gift 
     * @param userId userId of the sender
     * @param friend userId of the receiver
     * @param postId postId of the generated post 
     * @param cropId cropId sent as a gift
     */
    public static void createNewGift(int userId, int friendId, String postId, int cropId){

        String stmt = "INSERT INTO GIFT (USERID, FRIENDID, POSTID, CROPID) VALUES (" + 
                       userId + ", " + friendId + ", " + postId + ", " + cropId + ")";

        DataUtility.queryUpdate(stmt);

    }

}