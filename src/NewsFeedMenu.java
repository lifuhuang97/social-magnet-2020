import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class NewsFeedMenu {

    public static void readOptions(UserProfile currentUser){

        String choice = null;
        Scanner sc = new Scanner(System.in);
        
        do {
            display(currentUser);
            choice = sc.nextLine();
            int num = 0;

            if (choice.matches("^T[1-5]$")) {
                num = Integer.parseInt(choice.substring(1));
                choice = "T";
            }

            switch (choice) {
                case "M":
                    System.out.println();
                    return;
                case "T":
                    if (num > 0) {
                        displayThread(currentUser, num);
                    } else {
                        System.out.println("Please indicate the specific Thread ID that you would like to view!");
                    }
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != "M");
    }

    public static void display(UserProfile currentUser){

        NewsFeedCtrl ctrl = new NewsFeedCtrl(currentUser);

        LinkedHashMap <Post, ArrayList<Comment>> newsfeed = ctrl.retrieveNewsFeed();

        System.out.println(); 
        System.out.println("== Social Magnet :: News Feed ==");

        PostUtility.displayPosts(newsfeed, 1);

        System.out.print("[M]ain | [T]hread > ");

    }

    public static void displayThread(UserProfile currentUser, int num){
        NewsFeedCtrl ctrl = new NewsFeedCtrl(currentUser);

        LinkedHashMap <Post, ArrayList<Comment>> newsfeed = ctrl.retrieveNewsFeed();

        if (newsfeed.size() == 0) {
            System.out.println("No threads to view!");
        } else if (num > newsfeed.size()) {
            System.out.println("The thread that you have specified does not exist.");
        } else {
            HashMap<Post, ArrayList<Comment>> thread = ctrl.retrieveThread(newsfeed, num);
            ViewThreadMenu.readOptions(currentUser, thread, num);
        }

    }

}