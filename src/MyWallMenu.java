import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class MyWallMenu {
    private ProfileMenu pf;
    private UserProfile currentUser;
    private MyWallCtrl ctrl;

    public MyWallMenu(UserProfile user){
        this.currentUser = user;
        this.ctrl = new MyWallCtrl(user);
    }


    public void printWall(){

        String choice = null;
        Scanner sc = new Scanner(System.in);
        
        do{
            display();
        
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
                    ProfileMenu.readOption(new ProfileCtrl(), currentUser);
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
    }

    public void display(){

        int currentUserId = currentUser.getUserId();

        System.out.println();
        System.out.println("== Social Magnet :: My Wall ==");
        ctrl.displayPersonalInfo(currentUserId); // prints name, full name, rank, wealth ranking
        System.out.println();
        System.out.print("[M]ain | [T]hread | [A]ccept Gift | [P]ost > ");

        ArrayList<Post> newPosts = ctrl.getPosts(currentUserId);
        displayPosts(newPosts);
    }

    public void displayPosts(ArrayList<Post> posts){

    }

}