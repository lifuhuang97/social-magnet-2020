import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

public  class ViewThreadMenu {
    public static void readOptions(UserProfile currentUser, HashMap<Post, ArrayList<Comment>> thread, int num){
        String choice = null;
        Scanner sc = new Scanner(System.in);
        
        do {
            display(currentUser, thread, num);
            choice = sc.nextLine();

            switch (choice) {
                case "M":
                    System.out.println();
                    return;
                case "K":
                    System.out.println();
                    break;
                case "R":
                    System.out.println();
                    break;
                case "L":
                    like(thread);
                    break;
                case "D":
                    dislike(thread);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != "M");
    }

    public static void display(UserProfile currentUser, HashMap<Post, ArrayList<Comment>> thread, int num){
        ViewThreadCtrl ctrl = new ViewThreadCtrl(currentUser);

        System.out.println(); 
        System.out.println("== Social Magnet :: View a Thread ==");

        PostUtility.displayPosts(thread, num);

        System.out.println("Who likes this post:");
        int likeCounter = 1;
        String format = "%2s%2$s. %3$s (%4$s)\n";

        for (UserProfile user : ctrl.getPostLikedBy(thread)) {
            System.out.format(format, " ", likeCounter, user.getFullName(), user.getUsername());
            likeCounter += 1;
        }

        System.out.println();
        System.out.println("Who dislikes this post:");

        System.out.print("[M]ain | [K]ill |  [R]eply | [L]ike | [D]islike > ");
    }

    public static void like(HashMap<Post, ArrayList<Comment>> thread) {

    }

    public static void dislike(HashMap<Post, ArrayList<Comment>> thread) {

    }
}