import java.util.*;
public class WelcomeMenu {

    public static void display() {
        System.out.println("== Social Magnet :: Welcome ==");
        System.out.println("Good morning, anonymous!");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Please enter your choice: ");
    }

    public static void execute() {
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
                    System.exit(0);
                default :
                    System.out.println("Enter a choice between 1 to 3");
            }
        } while (choice != 3);
    }

    public static void register() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your username > ");
        String username = sc.nextLine();
        System.out.print("Enter your Full name> ");
        String fullName = sc.nextLine();
        System.out.print("Enter your password > ");
        String password = sc.nextLine();
        System.out.print("Confirm your password > ");
        String confirmedPassword = sc.nextLine();

        String display_msg = WelcomeMenuCtrl.register(username, fullName, password, confirmedPassword);

        System.out.println(display_msg);
        System.out.println();
    }

    public static void login() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your username > ");
        String username = sc.nextLine();
        System.out.print("Enter your password > ");
        String password = sc.nextLine();
        UserProfile retrievedUser = WelcomeMenuCtrl.login(username, password);

        if (retrievedUser == null) {
            System.out.println("Incorrect Username/Password");
        } 
        
        System.out.println();

        ProfileCtrl pmCtrl = new ProfileCtrl();
        ProfileMenu.readOption(pmCtrl, retrievedUser);

    }
}