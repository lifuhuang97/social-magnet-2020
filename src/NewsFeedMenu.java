import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

public class NewsFeedMenu {
    private ProfileMenu pf;
    private UserProfile currentUser;
    private NewsFeedCtrl ctrl;

    public NewsFeedMenu(UserProfile user){
        this.currentUser = user;
        this.ctrl = new NewsFeedCtrl();
    }

    public void printNewsFeed(){

        String choice = null;
        Scanner sc = new Scanner(System.in);
        
        do {
            display();
            choice = sc.nextLine();

            switch (choice) {
                case "M":
                    System.out.println();
                    pf = new ProfileMenu(new ProfileCtrl(), currentUser);
                    pf.readOption();
                    return;
                case "T":
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while(choice != "M");
    }

    public void display(){

        LinkedHashMap <Post, ArrayList<Comment>> newsfeed = ctrl.retrieveNewsFeed(currentUser.getUserId());

        System.out.println();
        System.out.println("== Social Magnet :: News Feed ==");

        int outsideCounter = 1;

        for (Post post : newsfeed.keySet()) {
            int insiderCounter = 1;
            ArrayList<Comment> comments = newsfeed.get(post);
            String username = UserProfileDAO.getUserProfileByUserId(UserPostDAO.getUserIdByPostId(post.getPostId())).getUsername();
            System.out.println(outsideCounter + " " + username + ": " + post.getContent());

            String format_even = "%2s%2$s.%3$s %4$s: %5$s\n";
            String format_odd = "%9s%2$s.%3$s %4$s: %5$s\n";

            if (comments.size() % 2 == 0 && comments.size() > 0) {
                format_even = "%9s%2$s.%3$s %4$s: %5$s\n";
                format_odd = "%2s%2$s.%3$s %4$s: %5$s\n";
            }

            for (int i = 0; i < comments.size(); i++) {
                String comment_username = UserProfileDAO.getUserProfileByUserId(UserCommentDAO.getUserIdByCommentId(comments.get(i).getCommentId())).getUsername();

                if (i % 2 == 0) {
                    System.out.format(format_even, " ", outsideCounter, insiderCounter, comment_username, comments.get(i).getContent());
                } else {
                    System.out.format(format_odd, " ", outsideCounter, insiderCounter, comment_username, comments.get(i).getContent());
                }
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