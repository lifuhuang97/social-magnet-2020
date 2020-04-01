import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Collections;

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

    public void post(String postContent) {
        // get a list of friends
        ArrayList<UserProfile> friends = FriendsDAO.getFriendsByUserId(currentUser.getUserId());

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

        System.out.println(usernames);

        ArrayList<Integer> userIds = new ArrayList<>();

        for (String username : usernames) {
            boolean found = false;
            for (UserProfile friend : friends) {
                if (friend.getUsername().equals(username)) {
                    found = true;
                    userIds.add(friend.getUserId());
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
        UserWallDAO.addUserWallPost(currentUser.getUserId(), postId);

        // add user_post
        UserPostDAO.addUserPost(currentUser.getUserId(), postId);
    }

}