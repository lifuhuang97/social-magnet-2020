// package main.java;

import java.util.ArrayList;
import java.util.Scanner;

public class FriendsMenu {

    public static void display(UserProfile currentUser) {
 
        System.out.println();
        System.out.println("== Social Magnet :: My Friends ==");
 
        FriendsCtrl ctrl = new FriendsCtrl(currentUser);
 
        ArrayList<UserProfile> populatedList = ctrl.populateList(currentUser.getUserId());
 
        int numOfFriends = ctrl.getNumOfFriends(currentUser.getUserId());
 
        int counter = 1;
        System.out.println("My Friends: ");
        for (int i = 0; i < numOfFriends; i++) {
            System.out.println(counter + ". " + populatedList.get(i).getUsername());
            counter++;
        }
 
        System.out.println();
 
        System.out.println("My Requests:");
        for (int i = numOfFriends; i < populatedList.size(); i++) {
            System.out.println(counter + ". " + populatedList.get(i).getUsername());
            counter++;
        }
 
        System.out.println();
 
        System.out.print("[M]ain | [U]nfriend | re[Q]uest | [A]ccept | [R]eject | [V]iew > ");
    }
 
    public static void readOptions(UserProfile currentUser) {
 
        String choice = null;
        Scanner sc = new Scanner(System.in);
 
        FriendsCtrl ctrl = new FriendsCtrl(currentUser);
 
        do {
            display(currentUser);
            choice = sc.nextLine();
            int num = 0;
 
            // reloads these values 
            int to_check = ctrl.getNumOfFriends(currentUser.getUserId());
            ArrayList<UserProfile> populatedList = ctrl.populateList(currentUser.getUserId());
 
            if (choice.matches("^U[1-6]$")) {
                num = Integer.parseInt(choice.substring(1));
                choice = "U";
            } else if (choice.matches("^Q[1-6]$")) {
                num = Integer.parseInt(choice.substring(1));
                choice = "Q";
            } else if (choice.matches("^A[1-6]$")) {
                num = Integer.parseInt(choice.substring(1));
                choice = "A";
            } else if (choice.matches("^R[1-6]$")) {
                num = Integer.parseInt(choice.substring(1));
                choice = "R";
            } 
 
            switch (choice) {
                case "M" :
                    System.out.println();
                    return;
 
                case "U" :
                    if (num > 0 && num < (to_check + 1)) {
                        unfriend(currentUser, num, populatedList);
                    } else {
                        System.out.println("Invalid action!");
                    }
                    break;
 
                case "Q" :
                    request(currentUser, populatedList);
                    break;
 
                case "A" :
                    if (num > 0 && num >= to_check) {
                        accept(currentUser, num, populatedList);
                    } else {
                        System.out.println("Invalid action!");
                    }
                    break;
 
                case "R" :
                    if (num > 0 && num >= to_check) {
                        reject(currentUser, num, populatedList);
                    } else {
                        System.out.println("Invalid action!");
                    }
                    break;
 
                case "V" :
                    view(currentUser);
                    break;
 
                default :
                    System.out.println("Please enter one of the choices above");
            }
             
        } while (choice != "M");
    }
 
     public static void unfriend(UserProfile currentUser, int num, ArrayList<UserProfile> populatedList) {

        FriendsCtrl ctrl = new FriendsCtrl(currentUser);

        UserProfile friendToUnfriend = populatedList.get(num - 1);

        ctrl.unfriend(friendToUnfriend);

        System.out.println("You have unfriended " + friendToUnfriend.getUsername());
 
     }
 
     public static void request(UserProfile currentUser,ArrayList<UserProfile> populatedList) {
        FriendsCtrl ctrl = new FriendsCtrl(currentUser);

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the username > ");
        String username = sc.nextLine();
        UserProfile friendProfile = UserProfileDAO.getUserProfileByUsername(username);

        ArrayList<UserProfile> friendsList = ctrl.getFriendsList(currentUser.getUserId());

        // check if he is requesting from someone that he already is friends with 
        boolean condition = false;
        for (UserProfile friend : friendsList) {
            if (friend.getUsername().equals(friendProfile.getUsername())) {
                condition = true;
            }
        }
 
            if (condition) {
                System.out.println("You are already friends with " + friendProfile.getUsername() + "!");
            } else {
                ctrl.request(friendProfile);
                System.out.println("A friend request is sent to " + friendProfile.getUsername());
            }

        }
 
        public static void accept(UserProfile currentUser, int num, ArrayList<UserProfile> populatedList) {
            FriendsCtrl ctrl = new FriendsCtrl(currentUser);

            UserProfile friendToAccept = populatedList.get(num - 1);

            ctrl.accept(friendToAccept);

            System.out.println(friendToAccept.getUsername() + " is now your friend.");
        }
 
        public static void reject(UserProfile currentUser, int num, ArrayList<UserProfile> populatedList) {
            FriendsCtrl ctrl = new FriendsCtrl(currentUser);
            
            UserProfile requestToReject = populatedList.get(num - 1);

            ctrl.reject(requestToReject);

            System.out.println("You have rejected a friend request from " + requestToReject.getUsername());
        }

        public static void view(UserProfile currentUser) {
            FriendsCtrl ctrl = new FriendsCtrl(currentUser);

            Scanner sc = new Scanner(System.in);
            System.out.print("Who's wall would you like to view? (Enter their username) > ");

            String username = sc.nextLine();
            UserProfile retrievedUser = ctrl.getUser(username);
            
            if (retrievedUser == null) {
                System.out.println("No such user!");
            } else {
                FriendsWallMenu.readOptions(currentUser, retrievedUser);
        }
    }
}