import java.util.*;

public class SocialMagnetMenu {
    // private CheckScoreCtrl ctrl;

    // public CheckScoreMenu(CheckScoreCtrl ctrl) {
    //     this.ctrl = ctrl;
    // }

    public void display() {
        System.out.println("== Social Magnet :: Welcome ==");
        System.out.println("Good morning, anonymous!");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Please enter your choice: ");
    }

    public void register() {

        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("== Social Magnet :: Registration ==");
        System.out.print("Enter you Username > ");
        String username = sc.nextLine();
        System.out.print("Enter you Full Name > ");
        String fullname = sc.nextLine();
        System.out.print("Enter your Password > ");
        String password = sc.nextLine();

        try {
            verify(username, fullname, password);
            System.out.println(username + ", your account is succcessfully created!");
            System.out.println();
        } catch (Exception e) { // TODO: Write exception class
            // TODO: Catch errors pertaining to registering (check alphanumeric, unique username etc.)
            System.out.println("Account could not be created!");
            System.out.println();
        }


    }

    public void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("== Social Magnet :: Login ==");
        System.out.print("Enter you Username > ");
        String username = sc.nextLine();
        System.out.print("Enter your Password > ");
        String password = sc.nextLine();

        try { 
            authenticate(username, password);
            display_menu(username); // TODO: Change to query the id instead
        } catch (Exception e) { // TODO: Write exception class
            // TODO: Catch errors pertaining to logging in (invalid user)
            System.out.println("Username/Password Invalid");
            System.out.println();
        }

    }

    public void menu(String username) {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("== Social Magnet :: Main Menu ==");
        System.out.println("Welcome " + username); // TODO: Query and dsiplay fullname
        System.out.println("1. News Feed");
        System.out.println("2. My Wall");
        System.out.println("3. My Friends");
        System.out.println("4. City Farmers");
        System.out.println("4. Logout");
        System.out.print("Please enter your choice: ");
    }

    public void display_menu(String username) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            menu(username);

            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e){
                sc.next();
                choice = 6;
            }

            switch (choice) {
                case 1 :
                    // news_feed();
                    break;
                case 2 :
                    // my_wall()
                    break;
                case 3 :
                    // my_friends()
                    break;
                case 4 :
                    // city_farmers
                    login();
                    break;
                case 5 :
                    System.out.println("bye bye");
                    break;
                default :
                    System.out.println("Enter a choice between 1 to 5");
            }
        } while (choice != 5);
    }

    public void execute() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            display();

            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e){
                sc.next();
                choice = 4;
            }

            switch (choice) {
                case 1 :
                    register();
                    break;
                case 2 :
                    login();
                    break;
                case 3 :
                    System.out.println("bye bye");
                    break;
                default :
                    System.out.println("Enter a choice between 1 to 3");
            }
        } while (choice != 3);
    }

    public void verify(String username, String fullname, String password) {
        // TODO: Check whether the user should be successfully registered 
    }

    public void authenticate(String username, String password) {
        // TODO: Check whether the user exists within the database 
    }

}