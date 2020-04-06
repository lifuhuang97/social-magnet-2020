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

        // NewsFeedCtrl ctrl = new NewsFeedCtrl();

        // LinkedHashMap <Post, ArrayList<Comment>> results = ctrl.retrieveNewsFeed(9);

        // for (Post p : results.keySet()) {
        //     ArrayList<Comment> comments = results.get(p);
        //     System.out.println(p.getContent());
        //     for (Comment comment : comments) {
        //         System.out.println(comment.getContent());
        //     }
        // }

        // String format = "%9s\n";
        // System.out.format(format, "hello");

        // Scanner sc = new Scanner(System.in);
        // System.out.print("> ");
        // String s = sc.nextLine();

        // if (s.matches("^T[1-5]$")) {
        //     System.out.println("yes");
        // } else {
        //     System.out.println("no");
        // }

        String s = "Until now, @evelyn, @adeline and @marilyn's laptops have bitten the dust. See, OOP is tough on laptops as well!@layfoo";

        String findStr = "@";
        int lastIndex = 0;
        ArrayList<Integer> results = new ArrayList<Integer>();

        while(lastIndex != -1) {

            lastIndex = s.indexOf(findStr,lastIndex);

            if(lastIndex != -1){
                results.add(lastIndex);
                lastIndex += 1;
            }
        }

        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<Integer> end_indexes = new ArrayList<>();

        for (Integer result : results) {
            int index = result + 1;
            String username = "";
            while (index < s.length() && (Character.isLetter(s.charAt(index)) || Character.isDigit(s.charAt(index)))) {
                username += s.charAt(index);
                index++;
            }
            end_indexes.add(index);
            usernames.add(username);
            username = "";
        }

        System.out.println(usernames);
        System.out.println(s.length());
        System.out.println(end_indexes);

        for (String username : usernames) {
            String newS = s.replace("@"+username, username);
            s = newS;
        }
        System.out.println(s);

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