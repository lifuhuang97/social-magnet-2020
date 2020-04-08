/**
 * FarmMenu
 */

// package main.java;

import java.util.*;

public class CityFarmerMenu {

    public static void displayFarmHeader(String type, UserProfile user){ // + add User object as arg

        PlotDAO.checkIfPlotCountNeedsUpdating(user);

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
                    returnWhere = accessStore(user);
                    if(returnWhere.equals("main")){
                        return;
                    }else{
                        break;
                    }
                case "3":
                    returnWhere = accessMyInventory(user);
                    if(returnWhere.equals("main")){
                        return;
                    }else{
                        break;
                    }
                case "4":
                    returnWhere = accessFriendMenu(user);
                    if(returnWhere.equals("main")){
                        return;
                    }else if(returnWhere.equals("farm")){
                        break;
                    }else{
                        String anotherReturnWhere = accessFriendFarmMenu(user, returnWhere);
                        if(anotherReturnWhere.equals("main")){
                            return;
                        }else{
                            break;
                        }
                    }
                case "5":
                    returnWhere = accessGiftMenu(user);
                    if(returnWhere.equals("main")){
                        return;
                    }else{
                        break;
                    }
                case "M":
                    System.out.println();
                    return;
                default:
                    System.out.println("You did not enter a valid option.");
            }
        }while (choice != "M");

        sc.close();
    }

    public static String viewFarmMenu(UserProfile user){

        CityFarmerCtrl ctrl = new CityFarmerCtrl(user);

        String returnWhere = ctrl.readFarmlandOptions();
        return returnWhere;
    }

    public static String accessStore(UserProfile user){

        CityFarmerCtrl ctrl = new CityFarmerCtrl(user);
        
        String returnWhere = ctrl.readFarmStoreOptions();
        return returnWhere;
    }

    public static String accessMyInventory(UserProfile user){

        CityFarmerCtrl ctrl = new CityFarmerCtrl(user);
        
        String returnWhere = ctrl.readFarmInventoryOptions();
        return returnWhere;

    }

    public static String accessFriendMenu(UserProfile user){

        CityFarmerCtrl ctrl = new CityFarmerCtrl(user);
        
        String returnWhere = ctrl.readFriendMenuOptions();
        return returnWhere;
    }

    public static String accessFriendFarmMenu(UserProfile user, String friendUsername){

        CityFarmerCtrl ctrl = new CityFarmerCtrl(user);
        
        String returnWhere = ctrl.readFriendFarmMenuOptions(friendUsername);
        return returnWhere;
    }

    public static String accessGiftMenu(UserProfile user){

        CityFarmerCtrl ctrl = new CityFarmerCtrl(user);
        
        String returnWhere = ctrl.readGiftOptions();
        return returnWhere;
    }

}