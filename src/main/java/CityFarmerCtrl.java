// // package main.java;

import java.util.*;

public class CityFarmerCtrl {

    public static String readFarmlandOptions(UserProfile user){

        Scanner sc = new Scanner(System.in);
        String choice;

        do{
            CityFarmerMenu.displayFarmHeader("case1", user);
            displayPlots(user, 1);
            System.out.print("[M]ain | City [F]armers | [P]lant | [C]lear | [H]arvest > ");
            choice = sc.nextLine();

            switch(choice) {
                case "M" :
                    System.out.println();
                    return "main";
                case "F" :
                    System.out.println();
                    return "farm";
                case "P" :
                    break;
                case "C" :
                    break;
                case "H" :
                    break;
                
                    default :
                    System.out.println("Invalid choice, please try again");
            }
        }while (choice != "M" || choice != "F");

        sc.close();
        return "";
        
    }

    public static String readFriendMenuOptions(UserProfile user){

        Scanner sc = new Scanner(System.in);
        String choice;
        int friendChoice = 0;

        do{
            CityFarmerMenu.displayFarmHeader("case4", user);

            int count = 1;

            ArrayList<UserProfile> friendsList = FriendsDAO.getFriendsByUserId(user.getUserId());
    
            System.out.println("My Friends: ");
            for(UserProfile friend: friendsList){
                System.out.println("" + count + ". " + friend.getFullName() + " (" + friend.getUsername() + ")");
                count++;
            }

            System.out.println("");
            System.out.print("[M]ain | [C]ity Farmer Main | Select choice > ");

            try{
                choice = sc.nextLine();

                if(choice.charAt(0) != 'M' || choice.charAt(0) != 'F'){
                    friendChoice = Integer.parseInt(choice) - 1;
                    choice = "G";
                }
            }catch(InputMismatchException e){
                sc.next();
                choice = "X";
            }

            switch(choice) {
                case "M" :
                    System.out.println();
                    return "main";
                case "F" :
                    System.out.println();
                    return "farm";
                case "G" :
                    UserProfile friend = friendsList.get(friendChoice);
                    return friend.getUsername();
                
                default :
                    System.out.println("Invalid choice, please try again");
            }
        }while (choice != "M" || choice != "F");

        sc.close();
        return "";
        
    }

    public static String readFriendFarmMenuOptions(String friendUsername){

        String returnWhere = "";
        String choice;
        Scanner sc = new Scanner(System.in);

        UserProfile friend = UserProfileDAO.getUserProfileByUsername(friendUsername);

        do{
            System.out.println();
            displayFriendFarmDetails(friend);
            System.out.print("[M]ain | City [F]armers | [S]teal > ");
            choice = sc.nextLine();

            switch(choice){
                case "M":
                    System.out.println();
                    return "main";
                case "F":
                    System.out.println();
                    return returnWhere;
                case "S":
                // steal something
                    break;
                default:
                    System.out.println("You did not enter a valid option.");
            }
        }while(choice != "M" || choice != "F");
        sc.close();

        return returnWhere;

    }


    public static void displayFriendFarmDetails(UserProfile friend){

        System.out.println("Name: " + friend.getFullName());
        System.out.println("Title: " + friend.getRank().getRankName());
        System.out.println("Gold: " + friend.getGold());
        displayPlots(friend, 2);
        
    }


    public static void displayPlots(UserProfile user, int version){

        // version 1 is self, version 2 is friend

        ArrayList<Plot> myPlots = PlotDAO.getMyPlots(user);
        int numberOfPlots = myPlots.size();

        if(version == 1){
            System.out.println("You have " + numberOfPlots + " plots of land.");
        }

        for (int i = 0; i < numberOfPlots; i++) {

            int index = i+1;

            Plot plot = myPlots.get(i);

            System.out.println(index + ". " + PlotDAO.printPlotDetails(plot));
        }
        
    }

    public static ArrayList<Crop> retrieveAllCrops () {
        return CropDAO.retrieveAll();
    }

    public static String retrieveCropNameByCropID (int cropID) {
        return CropDAO.retrieveName(cropID);
    }

    public static ArrayList<Inventory> retireveInventory (UserProfile userProfile) {
        return InventoryDAO.retrieveByUserID(userProfile.getUserId());
    }

    public static boolean registerPurchase (Crop crop, int quantity, UserProfile userprofile) {
        int cost = crop.getCost()*quantity;;
        int newGold = userprofile.getGold() - cost;

        if (newGold > 0) {
            userprofile.setGold(newGold);
            UserProfileDAO.updateUserGold(userprofile, -cost);
            InventoryDAO.updateInventory(userprofile.getUserId(), crop.getCropID(), quantity);
            return true;
        } else {
            return false;
        }

    }
    


}