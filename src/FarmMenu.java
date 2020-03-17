/**
 * FarmMenu
 */

import java.util.Scanner;

public class FarmMenu {

    public void displayFarmHeader(String type){ // + add User object as arg

        //get UserDAO, get name, title, gold
        //String username = user.getName();
        //String rank = user.get


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
        }

        // display menu according to type
        System.out.println();
        System.out.println("== Social Magnet :: City Farmers " + subMenu + "==");
        
        // prints Welcome, Kenny LEE!
        System.out.println("Welcome, "); // + username
  
        // prints Title: Novice Gold: 800 gold
        String format = "%-13s%s%n";
        String userTitle = "Title: "; // + title
        String userGold = "Gold: "; // + goldAmt
        System.out.printf(format, userTitle, userGold);

        System.out.println();
    }

    public void displayFarmMainMenu(){

        displayFarmHeader("main");
        System.out.println("1. My Farmland");
        System.out.println("2. My Store");
        System.out.println("3. My Inventory");
        System.out.println("4. Visit Friend");
        System.out.println("5. Send Gift");
        System.out.println("[M]ain | Enter your choice > ");
    }

    public void readOption(){

        Scanner sc = new Scanner(System.in);
        String choice;

        do{
            displayFarmMainMenu();
            choice = sc.nextLine();


            switch(choice){
                case "1":
                accessMyFarm();
                    break;
                case "2":
                accessStore();
                    break;
                case "3":
                accessMyInventory();
                    break;
                case "4":
                    accessFriendList();
                    break;
                case "5":
                    sendGift();
                    break;
                case "M":
                    returnToMagnet();
                    break;
                default:
                    System.out.println("You did not enter a valid option.");
            }
        }while (choice != "M");
    }

    public void accessMyFarm(){
        displayFarmHeader("case1");

        // FarmCtrl + getMyPlots method (select * from user_plot where userid = myid)
        
        // SMDate need a CalcGrowthPercentage for crop (compare cropPlantTime vs GrowTime)
        // cropPlantTime is calculated by comparing plantTime against current time
        // convert to minutes


        // Get number of plots I own
        
        System.out.println("1. ");

        System.out.println("You have " + " plots of land.");
        break;
    }

    public void accessStore(){
        break;
    }

    public void accessMyInventory(){
        break;
    }

    public void accessFriendList(){
        break;
    }

    public void sendGift(){
        break;
    }

    public void returnToMagnet(){
        // Calls social magnet menu screen
        break;
    }

}