package project.magnet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import project.utilities.*;
import project.farm.Gift;

public class WallCtrl {

    private UserProfile currentUser;

    /**
     * Constructs a WallCtrl objet
     * @param currentUser userProfile object current user
     */
    public WallCtrl(UserProfile currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Retrieve ordinal value based on number
     * @param i number to find its ordinal by
     * @return ordinal value
     */
    public String ordinal(int i) {
        String[] suffixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + suffixes[i % 10];
        }
    }

    /**
     * Retrieve a user's wealth ranking amongst friends
     * @return wealth ranking amongst friends
     */
    public String WealthRanking() {

        // currentUser's gold 
        int currentUserGold = currentUser.getGold();

        // get all friendsid including currentUser
        ArrayList<UserProfile> friends = FriendsDAO.getFriendsByUserId(currentUser.getUserId());

        // ids to compare with 
        ArrayList<Integer> userIds = new ArrayList<>();
        userIds.add(currentUser.getUserId());

        for (UserProfile friend : friends) {
            userIds.add(friend.getUserId());
        }

        ArrayList<Integer> golds = new ArrayList<>();

        // get everyones gold and store in linkedhashmap
        for (int userId : userIds) {
            golds.add(UserProfileDAO.getUserProfileByUserId(userId).getGold());
        }

        // remove duplicates 
        LinkedHashSet<Integer> hashSet = new LinkedHashSet<>(golds);
        ArrayList<Integer> listWithoutDuplicates = new ArrayList<>(hashSet);
        Collections.sort(listWithoutDuplicates);
        Collections.reverse(listWithoutDuplicates);

        int ranking = listWithoutDuplicates.indexOf(currentUserGold) + 1;

        String tobeprintedRanking = ordinal(ranking);

        if (tobeprintedRanking.equals("1st") || tobeprintedRanking.equals("0st")) {
            return "the";
        } else {
            return tobeprintedRanking;
        }
    }

    /**
     * Retrieve wall in the form of Post and Comment objects (ordered by date)
     * @return LinkedHashMap of Post and Comment objects
     */
    public LinkedHashMap <Post, ArrayList<Comment>>  retrieveWall() {
        LinkedHashMap <Post, ArrayList<Comment>> wall = new LinkedHashMap<>();
        int currentUserId = currentUser.getUserId();

        ArrayList<Integer> listOfIdsToFindPostBy = new ArrayList<>();
        listOfIdsToFindPostBy.add(currentUserId);

        wall = PostUtility.retrievePostsByUserIds(listOfIdsToFindPostBy);

        return wall;
    }

    /**
     * Post on wall
     * @param postContent content to post
     * @param wallPostedTo the userId of the wall that the post is to be posted on
     */
    public String post(String postContent, UserProfile wallPostedTo, int version) {
        // get a list of friends
        ArrayList<UserProfile> friends = FriendsDAO.getFriendsByUserId(currentUser.getUserId());

        // include a list of friends of the wall that you are posting to 
        ArrayList<UserProfile> wallFriends = FriendsDAO.getFriendsByUserId(wallPostedTo.getUserId());

        ArrayList<UserProfile> commonFriends = new ArrayList<>();

        for (UserProfile wallFriend : wallFriends) {
            boolean to_add = false;
            for (UserProfile friend : friends) {
                if (wallFriend.getUsername().equals(friend.getUsername())) {
                    to_add = true;
                }
            }
            
            if (to_add) {
                commonFriends.add(wallFriend);
            }
        }

        // find tags
        String findStr = "@";
        int lastIndex = 0;
        ArrayList<Integer> start_indexes = new ArrayList<Integer>();

        while(lastIndex != -1) {

            lastIndex = postContent.indexOf(findStr,lastIndex);

            if(lastIndex != -1){
                start_indexes.add(lastIndex);
                lastIndex += 1;
            }
        }

        ArrayList<String> usernames = new ArrayList<>();

        for (Integer result : start_indexes) {
            int index = result + 1;
            String username = "";
            while (index < postContent.length() && (Character.isLetter(postContent.charAt(index)) || Character.isDigit(postContent.charAt(index)))) {
                username += postContent.charAt(index);
                index++;
            }
            usernames.add(username);
            username = "";
        }

        ArrayList<Integer> userIds = new ArrayList<>();

        for (String username : usernames) {
            boolean found = false;
            for (UserProfile commonFriend : commonFriends) {
                if (commonFriend.getUsername().equals(username)) {
                    found = true;
                    userIds.add(commonFriend.getUserId());
                }
            }

            if (found) {
                String newPostContent = postContent.replaceFirst("@"+username, username);
                postContent = newPostContent;
            }
        }

        // add post
        int postId = PostDAO.addPost(postContent, new SMDate().toString());

        // add posttags
        for (int userId : userIds) {
            PostTagDAO.addPostTag(postId, userId);
        }

        // add user_wall
        UserWallDAO.addUserWallPost(wallPostedTo.getUserId(), postId);

        // add user_post
        UserPostDAO.addUserPost(currentUser.getUserId(), postId);

        if (version == 1) {
            return "Message posted successfully!";
        } else {
            return "" + postId;
        }
        
    }

    /**
     * Checks if user has gifts to claim
     * @return return true/false whether user has gifts to claim
     */
    public boolean hasGifts() {
        return (GiftDAO.retrieveGiftsByUserId(currentUser.getUserId()).size()) == 0 ? false : true;
    }

    /**
     * Accept the gifts given to the user by removing thread posts 
     * @return an ArrayList of gifts 
     */
    public ArrayList<Gift> acceptGifts() {
        ArrayList<Gift> gifts = GiftDAO.retrieveGiftsByUserId(currentUser.getUserId());

        // find all the posts generated from giving gifts
        HashMap<Post, ArrayList<Comment>> threads = new HashMap<>();

        for (Gift gift : gifts) {
            Post post = PostDAO.retrievePostByPostId(gift.getPostId());
            ArrayList<Integer> commentIds = PostCommentDAO.getCommentIdsByPostId(gift.getPostId());
            ArrayList<Comment> comments = new ArrayList<>();

            for (int commentId : commentIds) {
                comments.add(CommentDAO.retrieveCommentById(commentId));
            }

            threads.put(post, comments);
        }

        for(Map.Entry<Post, ArrayList<Comment>> thread : threads.entrySet()) {
            Post key = thread.getKey();
            ArrayList<Comment> value = thread.getValue();
            HashMap<Post, ArrayList<Comment>> retrieved = new HashMap<>();
            retrieved.put(key, value);
            PostUtility.canKill(retrieved, currentUser.getUserId());
        }

        return gifts;
    }

    // ====================================================
    /**
     * Check if two users are friends
     * @param currentUser UserProfile of first user
     * @param otherUser UserProfile of second user
     * @return friend status 
     */
    public boolean isFriends(UserProfile currentUser, UserProfile otherUser) {
        return FriendsDAO.isFriends(currentUser.getUserId(),otherUser.getUserId());
    }

    /**
     * Retrieve an ArrayList of common friends 
     * @param toCompare UserProfile of the user to compare with and find common friends with 
     * @return ArrayList of common friends
     */
    public ArrayList<UserProfile> getCommonFriends(UserProfile toCompare) {
        // display friends (common first)
        ArrayList<UserProfile> friends = FriendsDAO.getFriendsByUserId(currentUser.getUserId());

        ArrayList<UserProfile> viewedFriends = FriendsDAO.getFriendsByUserId(toCompare.getUserId());

        ArrayList<UserProfile> commonList = new ArrayList<>();

        for (UserProfile friend : friends) {
            boolean common = false;
            for (UserProfile viewedFriend : viewedFriends) {
                if (viewedFriend.getUsername().equals(friend.getUsername())) {
                    common = true;
                }
            }

            if (common) {
                commonList.add(friend);
            }
        }

        return commonList;
    }

    /**
     * Retrieve an ArrayList of UserProfile objects who are friends with currentUser
     * @return ArrayList of UserProfile friend objects
     */
    public ArrayList<UserProfile> getFriendsList() {
        return FriendsDAO.getFriendsByUserId(currentUser.getUserId());
    }

    /**
     * Retrieve ArrayList of unique friendList UserProfile Objects that does not exist within commonList 
     * @param friendList ArrayList of UserProfile
     * @param commonList ArrayList of UserProfile
     * @return ArrayList of UserProfile objects
     */
    public ArrayList<UserProfile> getUnqiueFriends(ArrayList<UserProfile> friendList, ArrayList<UserProfile> commonList) {
        ArrayList<UserProfile> uniqueFriendList = new ArrayList<>();

        for (UserProfile friend : friendList) {
            boolean to_add = true;
            for (UserProfile exist : commonList) {
                if (friend.getUsername().equals(exist.getUsername())) {
                    to_add = false;
                }
            }

            if (to_add) {
                uniqueFriendList.add(friend);
            }
        }

        return uniqueFriendList;
    }

    /**
     * Removes a specified UserProfile object from array of UserProfile objects
     * @param commonList ArrayList of UserProfile
     * @param toRemove UserProfile to remove from ArrayList 
     * @return ArrayList of UserProfile objects after removal
     */
    public ArrayList<UserProfile> removeCurrentUser(ArrayList<UserProfile> commonList, UserProfile toRemove) {
        Iterator<UserProfile> iter = commonList.iterator();

        while (iter.hasNext()) {
            UserProfile pointer = iter.next();
            if (pointer.getUsername().equals(toRemove.getUsername())) {
                iter.remove();
            }
        }

        return commonList;
    }

}