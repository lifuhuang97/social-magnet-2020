import java.util.*;
import java.time.*;
public class Test {
    public static void main(String[] args) {
        // ArrayList<UserProfile> friends = FriendsDAO.getFriendsByUserId(11);

        // for (UserProfile friend : friends) {
        //     System.out.println(friend.getFullName());
        //     System.out.println(friend.getUserId());
        // }

        // System.out.println(friends);

        // ArrayList<SMDate> dates = new ArrayList<>();

        // dates.add(new SMDate("01/01/2020 15:00"));
        // dates.add(new SMDate("02/01/2020 15:00"));
        // dates.add(new SMDate("02/01/2020 16:00"));

        // SMDate max = dates.get(0);
        // for (SMDate date : dates) {
        //     if (date.after(max)) {
        //         max = date;
        //     }
        // }

        // System.out.println(max); 

        // for (SMDate date : dates) {
        //     System.out.println("Hello");
        // }

        NewsFeedCtrl ctrl = new NewsFeedCtrl();

        LinkedHashMap <Post, ArrayList<Comment>> results = ctrl.retrieveNewsFeed(9);

        for (Post p : results.keySet()) {
            ArrayList<Comment> comments = results.get(p);
            System.out.println(p.getContent());
            for (Comment comment : comments) {
                System.out.println(comment.getContent());
            }
        }

    }

    // public static SMDate getMaxDate(ArrayList<SMDate> dates) {
    //     SMDate max = dates.get(0);
    //     for (SMDate date : dates) {
    //         if (date.after(max)) {
    //             max = date;
    //         }
    //     }
    //     return max;
    // }
}