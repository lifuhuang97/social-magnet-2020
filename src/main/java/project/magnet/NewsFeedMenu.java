package project.magnet;

import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import project.utilities.*;

public class NewsFeedMenu {

    /**
     * Read user option for news feed menu
     * @param currentUser userProfile object current user
     * @return string to indicate when to return to main
     */
    public static String readOptions(UserProfile currentUser){
        String returnWhere;
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
                    return "";
                case "T":
                    if (num > 0) {
                        returnWhere = displayThread(currentUser, num);
                        if (returnWhere.equals("main")) {
                            return "main";
                        } else {
                            break;
                        }
                    } else {
                        System.out.println("Please indicate the specific Thread ID that you would like to view!");
                    }
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != "M");
        return "";
    }

    /**
     * Displays news feed menu
     * @param currentUser userProfile object current user
     */
    public static void display(UserProfile currentUser){

        NewsFeedCtrl ctrl = new NewsFeedCtrl(currentUser);

        LinkedHashMap <Post, ArrayList<Comment>> newsfeed = ctrl.retrieveNewsFeed();

        System.out.println(); 
        System.out.println("== Social Magnet :: News Feed ==");

        if (newsfeed.size() == 0) {
            System.out.println("No threads to view!");
            System.out.println();
        }

        int outsideCounter = 1;

        for (Post post : newsfeed.keySet()) {
            int insiderCounter = 1;
            ArrayList<Comment> comments = newsfeed.get(post);
            String username = ctrl.retrieveUsername(post);
            System.out.println(outsideCounter + " " + username + ": " + post.getContent());

            // display likes and dislikes 
            int numOfLikes = ctrl.retrieveNumofLikes(post.getPostId());
            int numOfDislikes = ctrl.retrieveNumOfDislikes(post.getPostId());

            System.out.println("[ " + numOfLikes + ((numOfLikes > 1) ? " likes" : " like") + ", " + numOfDislikes + ((numOfDislikes > 1) ? " dislikes" : " dislike") + " ]");

            String format = "%2s%2$s.%3$s %4$s: %5$s\n";

            for (int i = 0; i < comments.size(); i++) {
                String comment_username = ctrl.retrieveCommentUsername(comments.get(i));

                System.out.format(format, " ", outsideCounter, insiderCounter, comment_username, comments.get(i).getContent());

                insiderCounter++;
            }

            outsideCounter++;
            System.out.println();
        }

        System.out.print("[M]ain | [T]hread > ");

    }

    /**
     * Display the thread for a specific user
     * @param currentUser userProfile object current user
     * @param num thread number of interest
     */
    public static String displayThread(UserProfile currentUser, int num){
        NewsFeedCtrl ctrl = new NewsFeedCtrl(currentUser);

        LinkedHashMap <Post, ArrayList<Comment>> newsfeed = ctrl.retrieveNewsFeed();

        if (newsfeed.size() == 0) {
            System.out.println("No threads to view!");
        } else if (num > newsfeed.size()) {
            System.out.println("The thread that you have specified does not exist.");
        } else {
            HashMap<Post, ArrayList<Comment>> thread = PostUtility.retrieveThread(newsfeed, num);
            return ViewThreadMenu.readOptions(currentUser, thread, num);
        }

        return "";

    }

}