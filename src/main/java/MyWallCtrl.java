// package main.java;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Collections;
import java.util.Iterator;

public class MyWallCtrl {

    private UserProfile currentUser;

    public MyWallCtrl(UserProfile currentUser) {
        this.currentUser = currentUser;
    }

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

        if (tobeprintedRanking.equals("1st")) {
            return "the";
        } else {
            return tobeprintedRanking;
        }
    }

    public LinkedHashMap <Post, ArrayList<Comment>>  retrieveWall() {
        LinkedHashMap <Post, ArrayList<Comment>> wall = new LinkedHashMap<>();
        int currentUserId = currentUser.getUserId();

        ArrayList<Integer> listOfIdsToFindPostBy = new ArrayList<>();
        listOfIdsToFindPostBy.add(currentUserId);

        wall = PostUtility.retrievePostsByUserIds(listOfIdsToFindPostBy);

        return wall;
    }

    public void post(String postContent, UserProfile wallPostedTo) {
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

        System.out.println("Message posted successfully!");
    }

    // ====================================================
    public boolean isFriends(UserProfile currentUser, UserProfile otherUser) {
        return FriendsDAO.isFriends(currentUser.getUserId(),otherUser.getUserId());
    }

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

    public ArrayList<UserProfile> getFriendsList() {
        return FriendsDAO.getFriendsByUserId(currentUser.getUserId());
    }

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