import java.util.*;

public class ProfileMenu {

    public static void display(UserProfile currentUser) {
        System.out.println("== Social Magnet :: Main Menu ==");
        System.out.println("Welcome, " + currentUser.getFullName() + "!");
        System.out.println("1. News Feed");
        System.out.println("2. My Wall");
        System.out.println("3. My Friends");
        System.out.println("4. City Farmers");
        System.out.println("5. Logout");
        System.out.println();
        System.out.print("Enter your choice > ");
    }

    public static void readOptions(UserProfile currentUser) {
        Scanner sc = new Scanner(System.in);
        String choice;

        do {
            display(currentUser);
            choice = sc.nextLine();

            switch (choice) {
                case "1" :
                    displayNewsFeed(currentUser); // display news feed
                    break;

                case "2" :
                    displayWall(currentUser); // display user's wall
                    break;

                case "3" :
                    displayFriendsPage(currentUser);
                    break;

                case "4" :
                    FarmMenu.readOptions(currentUser); // starts city farmer display menu
                    break;

                case "5" :
                    System.out.println();
                    return;

                default :
                    System.out.println("Enter a choice between 1 to 5");
            }
        } while (choice != "5");

        sc.close();
    }

    public static void displayNewsFeed(UserProfile currentUser) {
        NewsFeedMenu.readOptions(currentUser);
    }

    public static void displayWall (UserProfile currentUser) {
        MyWallMenu.readOptions(currentUser);
    }

    public static void displayFriendsPage (UserProfile currentUser) {
        FriendsMenu.readOptions(currentUser);
    }

}