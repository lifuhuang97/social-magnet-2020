/**
 * FarmMenu
 */

import java.util.*;

public class FarmMenu {

    public static void displayFarmHeader(String type, UserProfile user){ // + add User object as arg

        String subMenu = null;

        if(!type.equals("main")){
            if(type.equals("A")){
                subMenu = "";
            }
            else if(type.equals("case1")){
                subMenu = ":: My Farmland ";
            }
            else if(type.equals("case2")){
                subMenu = ":: My Store ";
            }
            else if(type.equals("case3")){
                subMenu = ":: My Inventory ";
            }
            else if(type.equals("case4")){
                subMenu = ":: Visit Friend ";
            }else if(type.equals("case5")){
                subMenu = ":: Send a Gift ";
            }
        }else{
            subMenu = "";
        }
        // display menu according to type
        System.out.println();
        System.out.println("== Social Magnet :: City Farmers " + subMenu + "==");
        
        System.out.println("Welcome, " + user.getFullName() + "!");// + username
  
        String format = "%-13s%s%n";
        String userTitle = "Title: " + user.getRank().getRankName();
        String userGold = "Gold: " + user.getGold();
        System.out.println(userTitle + "\t" + userGold);

        System.out.println();
    }

    public static void readOption(UserProfile user){

        Scanner sc = new Scanner(System.in);
        String choice;

        do{
            displayFarmHeader("main", user);
            System.out.println("1. My Farmland");
            System.out.println("2. My Store");
            System.out.println("3. My Inventory");
            System.out.println("4. Visit Friend");
            System.out.println("5. Send Gift");
            System.out.print("[M]ain | Enter your choice > ");
            choice = sc.nextLine();


            switch(choice){
                case "1":
                    accessMyFarm(user);
                    break;
                case "2":
                // accessStore();
                    break;
                case "3":
                // accessMyInventory();
                    break;
                case "4":
                    // accessFriendList();
                    break;
                case "5":
                    // sendGift();
                    break;
                case "M":
                    // returnToMagnet();
                    break;
                default:
                    System.out.println("You did not enter a valid option.");
            }
        }while (choice != "M");
    }

    public static void accessMyFarm(UserProfile currentUser){

        FarmCtrl ctrl = new FarmCtrl();

        displayFarmHeader("case1", currentUser);

        ArrayList<Plot> myPlots = ctrl.getMyPlots(currentUser);

        
        // SMDate need a CalcGrowthPercentage for crop (compare cropPlantTime vs GrowTime)
        // cropPlantTime is calculated by comparing plantTime against current time
        // convert to minutes

        // Get number of plots I own
        
        System.out.println("1. ");

        System.out.println("You have " + " plots of land.");
        
    }

    // public void accessStore(){
    //     break;
    // }

    // public void accessMyInventory(){
    //     break;
    // }

    // public void accessFriendList(){
    //     break;
    // }

    // public void sendGift(){
    //     break;
    // }

    // public void returnToMagnet(){
    //     // Calls social magnet menu screen
    //     break;
    // }

}