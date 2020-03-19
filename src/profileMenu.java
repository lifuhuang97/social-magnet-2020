import java.util.*;

public class ProfileMenu {
    public UserProfile user;
    private ProfileCtrl ctrl;

    public ProfileMenu(ProfileCtrl ctrl, UserProfile user) {
        this.ctrl = ctrl;
        this.user = user;
    }

    public void display() {
        System.out.println("== Social Magnet :: Main Menu ==");
        System.out.println("Welcome, " + user.getFullName() + "!");
        System.out.println("1. News Feed\n2. My Wall\n3. My Friends\n4. City Farmers\n5. Logout");
        System.out.println("Enter your choice >");
    }

    public void readOption() {
        Scanner sc = new Scanner(System.in);
        int choice;
        ProfileDAO profileDAO = new ProfileDAO();
        FriendsDAO friendsDAO = new FriendsDAO();
        do {
            display();
            choice = sc.nextInt();
            switch (choice) {
                case 1 :
                    displayNewsFeed(); // display news feed
                    break;
                case 2 :
                    displayWall(); // display user's wall
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
                    // cityFarmers(); // starts city farmer display menu
                    break;
                case 5 :
                    // login(); // display login page
                    break;

                default :
                    System.out.println("Enter a choice between 1 to 5");
            }
        } while (choice != 5);
    }

    public void displayNewsFeed() {
        // String newsFeed = ctrl.retrieveNewsFeed(user);
        // System.out.println(newsFeed);
    }

    public void displayWall () {
        // String wall = ctrl.retrieveWall(user);
        // System.out.println(wall);
    }

    public ArrayList<UserProfile> retrieveFriends() {
        // ArrayList<User> friendsList = ctrl.retrieveFriends(user);
        // return friendsList;
        return new ArrayList<UserProfile>();
    }

    public void displayFriendsWall (User user) {
        String friendsWall = ctrl.retrieveWall(user);
        System.out.println(friendsWall);
    }

}