// package main.java;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MyWallMenu {

    public static void readOptions(UserProfile currentUser){

        String choice = null;
        Scanner sc = new Scanner(System.in);
        
        do{
            display(currentUser);
            choice = sc.nextLine();
            int num = 0;

            if (choice.matches("^T[1-5]$")) {
                num = Integer.parseInt(choice.substring(1));
                choice = "T";
            }

            switch(choice){
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
                case "A":
                    break;
                case "P":
                    post(currentUser);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }

        } while(choice != "M");

        sc.close();
    }

    public static void display(UserProfile currentUser){

        MyWallCtrl ctrl = new MyWallCtrl(currentUser);

        System.out.println();
        System.out.println("== Social Magnet :: My Wall ==");
        displayPersonalInfo(currentUser); // prints name, full name, rank, wealth ranking
        System.out.println();

        LinkedHashMap <Post, ArrayList<Comment>> wall = ctrl.retrieveWall();

        if (wall.size() == 0) {
            System.out.println("No threads to view!");
            System.out.println();
        }

        PostUtility.display(wall, 1);

        System.out.print("[M]ain | [T]hread | [A]ccept Gift | [P]ost > ");

    }

    public static void displayPersonalInfo(UserProfile currentUser){

        MyWallCtrl ctrl = new MyWallCtrl(currentUser);

        String username = currentUser.getUsername();
        String fullname = currentUser.getFullName();
        Rank rank = currentUser.getRank();
        String rankDesc = rank.getRankName();

        System.out.println("About " + username);
        System.out.println("Full Name: " + fullname);

        String wealthRank = ctrl.WealthRanking();
        System.out.print(rankDesc + " Farmer, ");
        System.out.print(wealthRank + " richest");
        System.out.println();
    }

    public static void displayThread(UserProfile currentUser, int num){
        MyWallCtrl ctrl = new MyWallCtrl(currentUser);

        LinkedHashMap <Post, ArrayList<Comment>> wall = ctrl.retrieveWall();

        if (wall.size() == 0) {
            System.out.println("No threads to view!");
        } else if (num > wall.size()) {
            System.out.println("The thread that you have specified does not exist.");
        } else {
            HashMap<Post, ArrayList<Comment>> thread = PostUtility.retrieveThread(wall, num);
            ViewThreadMenu.readOptions(currentUser, thread, num);
        }

    }

    public static void post(UserProfile currentUser) {
        MyWallCtrl ctrl = new MyWallCtrl(currentUser);
        Scanner sc = new Scanner(System.in);
        System.out.print("Post a message > ");
        String postContent = sc.nextLine();

        int count = 0;
        for (int i = 0; i < postContent.length(); i++) {
            count++;
        }

        if (count > 300) {
            System.out.println("Your message was too long! ;)");
        } else {
            ctrl.post(postContent, currentUser);
        }
    }

    
}