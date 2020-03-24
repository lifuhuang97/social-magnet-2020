import java.util.*;

public class ProfileMenu {
    public UserProfile currentUser;
    public ProfileCtrl ctrl;

    public ProfileMenu(ProfileCtrl ctrl, UserProfile currentUser) {
        this.ctrl = ctrl;
        this.currentUser = currentUser;
    }

    public static void display(UserProfile currentUser) {
        System.out.println("== Social Magnet :: Main Menu ==");
        System.out.println("Welcome, " + currentUser.getFullName() + "!");
        System.out.println("1. News Feed\n2. My Wall\n3. My Friends\n4. City Farmers\n5. Logout");
        System.out.println();
        System.out.print("Enter your choice > ");
    }

    public static void readOption(ProfileCtrl ctrl, UserProfile currentUser) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            display(currentUser);
            String tempchoice = sc.nextLine();
            choice = Integer.parseInt(tempchoice);
            switch (choice) {
                case 1 :
                    displayNewsFeed(ctrl, currentUser); // display news feed
                    break;
                case 2 :
                    displayWall(ctrl, currentUser); // display user's wall
                    break;
                case 3 :
                    //ArrayList<User> friendList = retrieveFriends(); // display friend's wall
                    int count = 1;
                    // for (User user : friendList) {
                    //     System.out.println("" + count + ". " + user.getName());
                    // }
                    // int friendChoice = sc.nextInt();
                    // displayFriendsWall(friendList.get(friendChoice-1));
                    // break;
                case 4 :
                    FarmMenu.readOption(currentUser);
                    break;
                case 5 :
                    currentUser = null;
                    WelcomeMenu.execute();
                    System.out.println();
                    return;

                default :
                    System.out.println("Enter a choice between 1 to 5");
            }
        } while (choice != 5);

        sc.close();
    }

    public static void displayNewsFeed(ProfileCtrl ctrl, UserProfile currentUser) {
        NewsFeedMenu.printNewsFeed(currentUser);
    }

    public static void displayWall (ProfileCtrl ctrl, UserProfile currentUser) {
        MyWallMenu wallMenu = new MyWallMenu(currentUser);
        wallMenu.printWall();
        // wallMenu.getPosts();
        // System.out.println(wall);
    }

    public static ArrayList<UserProfile> retrieveFriends(ProfileCtrl ctrl, UserProfile currentUser) {
        // ArrayList<User> friendsList = ctrl.retrieveFriends(user);
        // return friendsList;
        return new ArrayList<UserProfile>();
    }

    public static void displayFriendsWall (ProfileCtrl ctrl, UserProfile currentUser) {
        String friendsWall = ctrl.retrieveWall(currentUser);
        System.out.println(friendsWall);
    }

}