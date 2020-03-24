import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class NewsFeedMenu {

    public static void readOptions(UserProfile currentUser){

        String choice = null;
        Scanner sc = new Scanner(System.in);
        
        do {
            display(currentUser);
            choice = sc.nextLine();

            switch (choice) {
                case "M":
                    System.out.println();
                    return;
                case "T":
                    System.out.println();
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while(choice != "M");
    }

    public static void display(UserProfile currentUser){

        NewsFeedCtrl ctrl = new NewsFeedCtrl();

        LinkedHashMap <Post, ArrayList<Comment>> newsfeed = ctrl.retrieveNewsFeed(currentUser.getUserId());

        System.out.println(); 
        System.out.println("== Social Magnet :: News Feed ==");

        int outsideCounter = 1;

        for (Post post : newsfeed.keySet()) {
            int insiderCounter = 1;
            ArrayList<Comment> comments = newsfeed.get(post);
            String username = UserProfileDAO.getUserProfileByUserId(UserPostDAO.getUserIdByPostId(post.getPostId())).getUsername();
            System.out.println(outsideCounter + " " + username + ": " + post.getContent());

            String format = "%2s%2$s.%3$s %4$s: %5$s\n";

            for (int i = 0; i < comments.size(); i++) {
                String comment_username = UserProfileDAO.getUserProfileByUserId(UserCommentDAO.getUserIdByCommentId(comments.get(i).getCommentId())).getUsername();

                System.out.format(format, " ", outsideCounter, insiderCounter, comment_username, comments.get(i).getContent());

                insiderCounter++;
            }

            outsideCounter++;
            System.out.println();
        }

        System.out.println();
        System.out.print("[M]ain | [T]hread > ");

    }

    public void displayThread(ArrayList<Post> posts){

    }

}