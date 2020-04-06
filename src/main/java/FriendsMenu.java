package main.java;

import java.util.*;

public class FriendsMenu {

   public static void display(UserProfile currentUser) {
       int count = 1;

        ArrayList<UserProfile> friendsList = getFriendsList(currentUser);
        System.out.println("== Social Magnet :: My Friends ==\nWelcome, " + currentUser.getFullName() + " !\n\nMy Friends:\n");

        for (UserProfile userProfile : friendsList) {
            System.out.println("" + count + ". " + userProfile.getUsername() + "\n");
            count ++;
        }

        ArrayList<UserProfile> requestList = FriendsDAO.getRequestsByUserId(currentUser.getUserId());
        if (requestList != null && requestList.size() > 0) {
            System.out.println("\nMy Requests:\n");

            for (UserProfile userProfile : requestList) {
                System.out.println("" + count + ". " + userProfile.getUsername() + "\n");
            }
        }
        System.out.print("[M]ain | [U]nfriend | re[Q]uest | [A]ccept | [R]eject | [V]iew > ");
    }

    public static void readOptions(UserProfile currentUser) {

        String choice;
        ArrayList<UserProfile> friendsList = getFriendsList(currentUser);
        int friendChoice = 0;
     

        do {
            Scanner sc = new Scanner(System.in);
            display(currentUser);

            try {
                choice = sc.nextLine();
                // TODO: Exception handling for Integer.parseInt + Cleanup

                if (choice.charAt(0) == 'A' && choice.length() >= 2) {
                    
                    friendChoice = Integer.parseInt(choice.substring(1));
                    choice = "A";

                } else if (choice.charAt(0) == 'U' && choice.length() >= 2) {

                    friendChoice = Integer.parseInt(choice.substring(1));
                    choice = "U";

                }
            } catch (InputMismatchException e){

                sc.next();
                choice = "Z";

            }

            switch (choice) {
                case "M" :
                    System.out.println();
                    return;

                case "U" :
                    UserProfile friend = friendsList.get(friendChoice-1);
                    unfriend(currentUser, friend);
                    break;

                case "Q" :
                    System.out.print("Enter the username > ");
                    String username = sc.nextLine();
                    request(currentUser, username);
                    break;

                case "A" :
                    UserProfile friendAccept = friendsList.get(friendChoice-1);
                    accept(currentUser, friendAccept);
                    break;

                case "R" :
                    break;

                case "V" :
                    break;

                default :
                    System.out.println("Please enter one of the choices above");
            }
            
        } while (choice != "M");
    }

    public static ArrayList<UserProfile> getFriendsList(UserProfile userProfile) {
        ArrayList<UserProfile> result = new ArrayList<>();
        FriendsCtrl ctrl = new FriendsCtrl();
        result = ctrl.getFriendsList(userProfile);
        return result;

    }

    public static boolean unfriend (UserProfile userProfile, UserProfile friend) {
        boolean result = false;
        FriendsCtrl ctrl = new FriendsCtrl();
        ctrl.unfriend(userProfile, friend);
        return result;
    }

    public static void request (UserProfile userProfile, String username) {
        FriendsCtrl ctrl = new FriendsCtrl();
        ctrl.request(userProfile, username);
    }

    public static boolean accept (UserProfile userProfile, UserProfile friend) {
        boolean result = false;
        FriendsCtrl ctrl = new FriendsCtrl();
        ctrl.accept(userProfile, friend);
        return result;
    }
}