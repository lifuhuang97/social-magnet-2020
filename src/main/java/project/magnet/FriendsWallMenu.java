package project.magnet;

import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import project.utilities.*;
import project.farm.Rank;

public class FriendsWallMenu {

    /**
     * process user's choice for friend's wall menu
     * @param currentUser UserProfile object of current user
     * @param viewedUser UserProfile object of user being viewed
     * @return string to indicate when to return to main
     */
    public static String readOptions(UserProfile currentUser, UserProfile viewedUser){

        String choice = null;
        String returnWhere;
        Scanner sc = new Scanner(System.in);
        WallCtrl ctrl = new WallCtrl(viewedUser);
        
        do {
            display(currentUser, viewedUser);
            choice = sc.nextLine();
            int num = 0;

            if (choice.matches("^T[1-5]$")) {
                num = Integer.parseInt(choice.substring(1));
                choice = "T";
            }

            switch(choice){
                case "M":
                    System.out.println();
                    return "main";
                case "T":
                    if (ctrl.isFriends(currentUser, viewedUser)) {
                        if (num > 0) {
                            returnWhere = displayThread(currentUser, viewedUser, num);
                            if (returnWhere.equals("main")) {
                                return "main";
                            } else {
                                break;
                            }
                        } else {
                            System.out.println("Please indicate the specific Thread ID that you would like to view!");
                        }
                    } else {
                        System.out.println("Invalid action");
                    }
                    break;
                case "P":
                    if (ctrl.isFriends(currentUser, viewedUser)) {
                        post(currentUser, viewedUser);
                    } else {
                        System.out.println("Invalid action");
                    }
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }

        } while(choice != "M");

        sc.close();
        return "";
    }

    /**
     * Displays friends wall menu
     * @param currentUser UserProfile object of current user
     * @param viewedUser UserProfile object of user being viewed
     */
    public static void display(UserProfile currentUser, UserProfile viewedUser){

        WallCtrl ctrl = new WallCtrl(viewedUser);

        System.out.println();
        System.out.println("== Social Magnet :: " + viewedUser.getUsername() + "'s Wall ==");
        System.out.println("Welcome, " + currentUser.getFullName());
        System.out.println();
        displayPersonalInfo(viewedUser); // prints name, full name, rank, wealth ranking
        System.out.println();

        // check if they are friends before displaying their wall 
        boolean isFriends = ctrl.isFriends(currentUser, viewedUser);

        if (isFriends) {
            LinkedHashMap <Post, ArrayList<Comment>> wall = ctrl.retrieveWall();

            if (wall.size() == 0) {
                System.out.println("No threads to view!");
                System.out.println();
            }

            PostUtility.display(wall, 1);

            ArrayList<UserProfile> commonList = ctrl.getCommonFriends(currentUser);
            Collections.sort(commonList);

            int numOfCommon = commonList.size();

            ArrayList<UserProfile> friendList = ctrl.getFriendsList();
            
            ArrayList<UserProfile> uniqueFriendList = ctrl.getUnqiueFriends(friendList, commonList);

            Collections.sort(uniqueFriendList);

            commonList.addAll(uniqueFriendList);

            commonList = ctrl.removeCurrentUser(commonList, currentUser);

            int counter = 1;
            for (int i = 0; i < commonList.size(); i++) {
                if (i < numOfCommon) {
                    System.out.println(counter + ". " + commonList.get(i).getFullName() + " (Common Friend)");
                } else {
                    System.out.println(counter + ". " + commonList.get(i).getFullName());
                }
            }

            System.out.println();

            System.out.print("[M]ain | [T]hread | [P]ost > ");
        } else {
            System.out.print("[M]ain > ");
        }

    }

    /**
     * Displays the personal info section of friend's wall menu
     * @param viewedUser UserProfile object of user being viewed
     */
    public static void displayPersonalInfo(UserProfile viewedUser){

        WallCtrl ctrl = new WallCtrl(viewedUser);

        String username = viewedUser.getUsername();
        String fullname = viewedUser.getFullName();
        Rank rank = viewedUser.getRank();
        String rankDesc = rank.getRankName();

        System.out.println("About " + username);
        System.out.println("Full Name: " + fullname);

        String wealthRank = ctrl.WealthRanking();
        System.out.print(rankDesc + " Farmer, ");
        System.out.print(wealthRank + " richest");
        System.out.println();
    }

    /**
     * Post on friends wall 
     * @param currentUser UserProfile object of current user
     * @param friendProfile UserProfile object of user who's wall is to be posted on
     */
    public static void post(UserProfile currentUser, UserProfile friendProfile) {
        WallCtrl ctrl = new WallCtrl(currentUser);
        Scanner sc = new Scanner(System.in);
        System.out.print("Post a message > ");
        String postContent = sc.nextLine();

        Pattern p = Pattern.compile("[^a-z0-9 !.,@&:()$]", Pattern.CASE_INSENSITIVE);    
        Matcher m = p.matcher(postContent);
        boolean checkPostContent = m.find();

        if (checkPostContent) {
            System.out.println("Please ensure that your input only contains alphanumerics, whitespaces and the following special characters: !.,@&:()");
            System.out.println();
        } else {
            int count = 0;
            for (int i = 0; i < postContent.length(); i++) {
                count++;
            }

            if (count > 300) {
                System.out.println("Your message was too long! ;)");
            } else {
                System.out.println(ctrl.post(postContent, friendProfile, 1));;
            }
        }
    }

    public static String displayThread(UserProfile currentUser, UserProfile userToView, int num){
        WallCtrl ctrl = new WallCtrl(userToView);

        LinkedHashMap <Post, ArrayList<Comment>> wall = ctrl.retrieveWall();

        if (wall.size() == 0) {
            System.out.println("No threads to view!");
        } else if (num > wall.size()) {
            System.out.println("The thread that you have specified does not exist.");
        } else {
            HashMap<Post, ArrayList<Comment>> thread = PostUtility.retrieveThread(wall, num);
            return ViewThreadMenu.readOptions(currentUser, thread, num);
        }
        
        return "";

    }
}