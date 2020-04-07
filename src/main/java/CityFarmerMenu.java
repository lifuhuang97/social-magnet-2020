/**
 * FarmMenu
 */

// package main.java;

import java.util.*;

public class CityFarmerMenu {

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

    public static void readOptions(UserProfile user){

        String returnWhere;

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
                    returnWhere = viewFarmMenu(user);
                    if(returnWhere.equals("main")){
                        return;
                    }else{
                        break;
                    }
                case "2":
                    accessStore(user);
                    break;
                case "3":
                    accessMyInventory(user);
                    break;
                case "4":
                    returnWhere = accessFriendMenu(user);
                    if(returnWhere.equals("main")){
                        return;
                    }else if(returnWhere.equals("farm")){
                        break;
                    }else{
                        String anotherReturnWhere = accessFriendFarmMenu(returnWhere);

                        if(anotherReturnWhere.equals("main")){
                            return;
                        }else{
                            break;
                        }
                    }
                case "5":
                    // sendGift();
                    break;
                case "M":
                    System.out.println();
                    return;
                default:
                    System.out.println("You did not enter a valid option.");
            }
        }while (choice != "M");

        sc.close();
    }

    public static String viewFarmMenu(UserProfile currentUser){

        String returnWhere = CityFarmerCtrl.readFarmlandOptions(currentUser);
        return returnWhere;

    }

    public static void accessStore(UserProfile currentUser){
        displayFarmHeader("case2", currentUser);
        System.out.println();
        System.out.println("Seeds Available:");

        Scanner sc = new Scanner(System.in);
        ArrayList<Crop> crops = CityFarmerCtrl.retrieveAllCrops();

        for (int i = 0; i < crops.size(); i ++) {
            Crop item = crops.get(i);
            System.out.println("" + (i+1) + ". " + item.getName() + "   - " + item.getCost() + " gold");
            System.out.println("Harvest in: " + item.getHarvestTime() + " mins");
            System.out.println("XP Gained: " + item.getXp());
        }

        System.out.print("[M]ain | City [F]armers | ");

        boolean choiceFlag = true;

        while (choiceFlag) {
            int itemChoice = 0;
            int quantityChoice = 0;
            System.out.print("Select choice > ");
            String choice = sc.nextLine();


            try {
                itemChoice = Integer.parseInt(choice);
                choice = "";
            } catch (NumberFormatException e) {
                //swallow
            }

            if (choice.equals("M")) {

                choiceFlag = false;
                ProfileMenu.readOptions(currentUser);
                return;

            } else if (choice.equals("F")) {

                choiceFlag = false;
                readOptions(currentUser);
                return;

            } else if (!choice.equals("")) {

                System.out.println("Please enter a valid choice.");

            } else {

                try {
                    System.out.print("Enter quantity > ");
                    quantityChoice = sc.nextInt();

                    if (itemChoice > 0 && itemChoice <= crops.size()) {

                        choiceFlag = false;
                        int cost = crops.get(itemChoice-1).getCost() * quantityChoice;

                        if (CityFarmerCtrl.registerPurchase(crops.get(itemChoice-1), quantityChoice, currentUser)) {
                            System.out.println("" + quantityChoice + " bags of seeds purchased for " + cost + " gold.");
                        } else {
                            System.out.println("Insufficient gold.");
                        }
                        
                    } else {

                        System.out.println("Please enter a valid quantity.");

                    }

                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid choice.");
                }

            }



        }

    }

    public static void accessMyInventory(UserProfile currentUser){
        displayFarmHeader("case3", currentUser);

        ArrayList<Inventory> items = CityFarmerCtrl.retireveInventory(currentUser);
        boolean flag = true;
        Scanner sc= new Scanner(System.in);

        for (int i = 0; i < items.size(); i ++) {
            System.out.println( "" + (i+1) + ". " + items.get(i).getQuantity() + " Bags of " + 
                CityFarmerCtrl.retrieveCropNameByCropID(items.get(i).getCropID()) );
        }

        System.out.print("[M]ain | City [F]armers | ");

        while (flag) {
            System.out.print("select choice > ");
            String choice = sc.nextLine();

            if (choice.equals("M")) {
                flag = false;
                ProfileMenu.readOptions(currentUser);
                return;
            } else if (choice.equals("F")) {
                flag = false;
                readOptions(currentUser);
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }

    }

    public static String accessFriendMenu(UserProfile currentUser){
        
        String returnWhere = CityFarmerCtrl.readFriendMenuOptions(currentUser);
        return returnWhere;

    // Here need to print friend list

    }

    public static String accessFriendFarmMenu(String friendUsername){

        String returnWhere = CityFarmerCtrl.readFriendFarmMenuOptions(friendUsername);
        return returnWhere;

    }

    // public void sendGift(){
    //     break;
    // }

    // public void returnToMagnet(){
    //     // Calls social magnet menu screen
    //     break;
    // }

}