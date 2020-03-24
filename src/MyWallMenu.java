import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class MyWallMenu {

    public static void readOptions(UserProfile currentUser){

        String choice = null;
        Scanner sc = new Scanner(System.in);
        
        do{
            display(currentUser);
        
            try{
                choice = sc.nextLine();
            }catch(InputMismatchException e){
                choice = "T";
            }catch(NoSuchElementException e){
                choice = "T";
            }

            switch(choice){
                case "M":
                    System.out.println();
                    return;
                case "T":
                    break;
                case "A":
                    break;
                case "P":
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }

        }while(choice != "M");

        sc.close();
    }

    public static void display(UserProfile currentUser){

        MyWallCtrl ctrl = new MyWallCtrl(currentUser);

        System.out.println();
        System.out.println("== Social Magnet :: My Wall ==");
        displayPersonalInfo(currentUser); // prints name, full name, rank, wealth ranking
        System.out.println();
        System.out.print("[M]ain | [T]hread | [A]ccept Gift | [P]ost > ");

        ArrayList<Post> newPosts = ctrl.getTopFivePosts(currentUser.getUserId());

    }

    public static void displayPersonalInfo(UserProfile user){

        String username = user.getUsername();
        String fullname = user.getFullName();
        Rank rank = user.getRank();
        String rankDesc = rank.getRankName();

        System.out.println("About " + username);
        System.out.println("Full Name: " + fullname);

        String wealthRank = UserProfileDAO.wealthRankPrint(user);
        System.out.print(rankDesc + " Farmer, ");
        System.out.print(wealthRank + " richest");
        System.out.println();
    }

    //TODO: PRINT ALL POSTS (TAKE FROM FRANCINE CODE)
}