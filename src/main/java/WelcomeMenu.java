// package main.java;

import java.util.*;
public class WelcomeMenu {

     /**
     * Displays welcome menu to user
     */
    public static void display() {
        System.out.println("== Social Magnet :: Welcome ==");
        System.out.println("Good morning, anonymous!");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Please enter your choice: ");
    }

     /**
     * Execute user's choice
     */
    public static void execute() {
        Scanner sc = new Scanner(System.in);
        String choice;
        do {
            display();

            choice = sc.nextLine();

            switch (choice) {
                case "1" :
                    register();
                    break;
                case "2" :
                    login();
                    break;
                case "3" :
                    System.out.println("bye bye");
                    System.exit(0); 
                default :
                    System.out.println("Enter a choice between 1 to 3");
                    System.out.println();
            }
        } while (choice != "3");
        sc.close();
    }

     /**
     * Scan's user input for registration and calls register function from WelcomeMenuCtrl
     */
    public static void register() {
        System.out.println();
        Scanner sc = new Scanner(System.in);

        System.out.println("== Social Magnet :: Registration ==");
        System.out.print("Enter your username > ");
        String username = sc.nextLine();

        System.out.print("Enter your Full name> ");
        String fullName = sc.nextLine();

        System.out.print("Enter your password > ");
        String password = sc.nextLine();

        System.out.print("Confirm your password > ");
        String confirmedPassword = sc.nextLine();

        try {
            WelcomeMenuCtrl.register(username, fullName, password, confirmedPassword);
            System.out.println(username + " your account is successfully created!");
        } catch (RegisterException e) {
            System.out.println(e.getMessage());
        }

        System.out.println();
    }

     /**
     * Logs user in using WelcomeMenuCtrl login function 
     */
    public static void login() {
        System.out.println();
        Scanner sc = new Scanner(System.in);

        System.out.println("== Social Magnet :: Login ==");
        System.out.print("Enter your username > ");

        String username = sc.nextLine();
        System.out.print("Enter your password > ");

        String password = sc.nextLine();
        
        UserProfile retrievedUser = WelcomeMenuCtrl.login(username, password);

        if (retrievedUser == null) {
            System.out.println("Incorrect Username/Password");
        } else {
            System.out.println();
            ProfileMenu.readOptions(retrievedUser);
        }

        System.out.println();

    }
}