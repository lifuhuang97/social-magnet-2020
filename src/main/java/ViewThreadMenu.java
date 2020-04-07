// package main.java;

import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

public  class ViewThreadMenu {

     /**
     * Read user option for thread menu
     * @param currentUser userProfile object current user
     * @param thread thread user wants to view
     * @param num counter to begin the outer thread numbering
     */
    public static void readOptions(UserProfile currentUser, HashMap<Post, ArrayList<Comment>> thread, int num){
        String choice = null;
        Scanner sc = new Scanner(System.in);
        
        do {
            display(currentUser, thread, num);
            choice = sc.nextLine();

            switch (choice) {
                case "M":
                    System.out.println();
                    return;
                case "K":
                    kill(currentUser, thread);
                    return;
                case "R":
                    reply(currentUser, thread);
                    return;
                case "L":
                    like(currentUser, thread);
                    break;
                case "D":
                    dislike(currentUser, thread);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != "M");
    }

    /**
     * Displays menu for thread
     * @param currentUser userProfile object current user
     * @param thread thread user wants to view
     * @param num counter to begin the outer thread numbering
     */
    public static void display(UserProfile currentUser, HashMap<Post, ArrayList<Comment>> thread, int num){
        ViewThreadCtrl ctrl = new ViewThreadCtrl(currentUser);

        System.out.println(); 
        System.out.println("== Social Magnet :: View a Thread ==");

        PostUtility.display(thread, num);

        System.out.println("Who likes this post:");
        int likeCounter = 1;
        String format = "%2s%2$s. %3$s (%4$s)\n";

        for (UserProfile user : ctrl.getPostLikedBy(thread)) {
            System.out.format(format, " ", likeCounter, user.getFullName(), user.getUsername());
            likeCounter += 1;
        }

        System.out.println();
        System.out.println("Who dislikes this post:");
        int dislikeCounter = 1;

        for (UserProfile user : ctrl.getPostDislikedBy(thread)) {
            System.out.format(format, " ", dislikeCounter, user.getFullName(), user.getUsername());
            dislikeCounter += 1;
        }

        System.out.println();

        System.out.print("[M]ain | [K]ill |  [R]eply | [L]ike | [D]islike > ");
    }

    /**
     * Reply to a specific thread
     * @param currentUser userProfile object current user
     * @param thread thread user wants to view
     */
    public static void reply(UserProfile currentUser, HashMap<Post, ArrayList<Comment>> thread) {
        ViewThreadCtrl ctrl = new ViewThreadCtrl(currentUser);

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your reply > ");
        String content = sc.nextLine();

        ctrl.reply(thread, content, currentUser.getUserId());
    }

    /**
     * Like a thread
     * @param currentUser userProfile object current user
     * @param thread thread user wants to view
     */
    public static void like(UserProfile currentUser, HashMap<Post, ArrayList<Comment>> thread) {
        ViewThreadCtrl ctrl = new ViewThreadCtrl(currentUser);

        ctrl.likePost(thread, currentUser.getUserId());
    }

    /**
     * Dislike a thread
     * @param currentUser userProfile object current user
     * @param thread thread user wants to view
     */
    public static void dislike(UserProfile currentUser, HashMap<Post, ArrayList<Comment>> thread) {
        ViewThreadCtrl ctrl = new ViewThreadCtrl(currentUser);

        ctrl.dislikePost(thread, currentUser.getUserId());
    }

    /**
     * kill a thread
     * @param currentUser userProfile object current user
     * @param thread thread user wants to view
     */
    public static void kill(UserProfile currentUser, HashMap<Post, ArrayList<Comment>> thread) {
        ViewThreadCtrl ctrl = new ViewThreadCtrl(currentUser);
        try {
            ctrl.canKill(thread, currentUser.getUserId());
        } catch (KillThreadException e) {
            System.out.println(e.getMessage());
        }
        
    }
}