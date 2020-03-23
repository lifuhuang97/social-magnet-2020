import java.util.*;

public class FriendsMenu {
    public UserProfile currentUser;
    private FriendsCtrl ctrl;

    public FriendsMenu (FriendsCtrl ctrl, UserProfile currentUser) {
        this.ctrl = ctrl;
        this.currentUser = currentUser;
    }

   public static void display(FriendsCtrl ctrl, UserProfile currentUser) {
       int count = 1;

        ArrayList<UserProfile> friendsList = FriendsDAO.getFriendsByUserId(currentUser.getUserId());
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

    public static void readOption(FriendsCtrl ctrl, UserProfile currentUser) {
        display(ctrl, currentUser);

        Scanner sc = new Scanner(System.in);
        String choice;
        int friendChoice;
        ProfileCtrl profileCtrl = new ProfileCtrl();

        do {
            display(ctrl, currentUser);

            try {
                choice = sc.nextLine();
                if (choice.charAt(0) == 'A') {
                    friendChoice = Integer.parseInt(choice.substring(1));
                    choice = "A";
                }
            } catch (InputMismatchException e){
                sc.next();
                choice = "Z";
            }

            switch (choice) {
                case "M" :
                    ProfileMenu.readOption(profileCtrl, currentUser);
                    break;
                case "U" :
                    // ctrl.Unfriend()
                    break;
                case "Q" :
                    break;
                case "A" :
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
}