package main.java;

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

    public static void displayThread(UserProfile currentUser, int num){
        NewsFeedCtrl ctrl = new NewsFeedCtrl(currentUser);

        LinkedHashMap <Post, ArrayList<Comment>> newsfeed = ctrl.retrieveNewsFeed();

        if (newsfeed.size() == 0) {
            System.out.println("No threads to view!");
        } else if (num > newsfeed.size()) {
            System.out.println("The thread that you have specified does not exist.");
        } else {
            HashMap<Post, ArrayList<Comment>> thread = PostUtility.retrieveThread(newsfeed, num);
            ViewThreadMenu.readOptions(currentUser, thread, num);
        }

    }

}