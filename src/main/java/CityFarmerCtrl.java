// // package main.java;

import java.util.*;

public class CityFarmerCtrl {

    public static String readFarmlandOptions(UserProfile user){

        HashMap<String,ArrayList<Integer>> status;

        Scanner sc = new Scanner(System.in);
        String choice;

        do{
            CityFarmerMenu.displayFarmHeader("case1", user);
            status = displayPlots(user, 1);
            System.out.print("[M]ain | City [F]armers | [P]lant | [C]lear | [H]arvest > ");
            choice = sc.nextLine();
            int num = 0;

            if(choice.matches("^P[1-9]$")){
                num = Integer.parseInt(choice.substring(1));
                choice = "P";
            }else if (choice.matches("^C[1-9]$")){
                num = Integer.parseInt(choice.substring(1));
                choice = "C";
            }else if (choice.matches("^H[1-9]$")){
                num = Integer.parseInt(choice.substring(1));
                choice = "H";
            }

            switch(choice) {
                case "M" :
                    System.out.println();
                    return "main";
                case "F" :
                    System.out.println();
                    return "farm";
                case "P" :
                    ArrayList<Integer> plantablePlots = status.get("empty");
                    if(plantablePlots.contains(num)){
                        //perform plant crop
                    }else{
                        System.out.println("Invalid action!");
                    }
                    break;
                case "C" :
                    ArrayList<Integer> clearablePlots = status.get("wilted");
                    if(clearablePlots.contains(num)){
                        // perform clear plot
                    }else{
                        System.out.println("Invalid action!");
                    }
                    break;
                case "H" :
                    ArrayList<Integer> harvestablePlots = status.get("grown");
                    if(harvestablePlots.contains(num)){
                        // perform harvest
                    }else{
                        System.out.println("Invalid action!");
                    }
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


    public static HashMap<String,ArrayList<Integer>> displayPlots(UserProfile user, int version){

        HashMap<String,ArrayList<Integer>> result = new HashMap<String,ArrayList<Integer>>();

        ArrayList<Integer> emptyPlotList = new ArrayList<Integer>();
        ArrayList<Integer> growingList = new ArrayList<Integer>();
        ArrayList<Integer> grownList = new ArrayList<Integer>();
        ArrayList<Integer> wiltedList = new ArrayList<Integer>();

        result.put("grown",grownList);
        result.put("wilted", wiltedList);
        result.put("empty", emptyPlotList);
        result.put("growing",growingList);

        // version 1 is self, version 2 is friend

        ArrayList<Plot> myPlots = PlotDAO.getMyPlots(user);
        int numberOfPlots = myPlots.size();

        if(version == 1){
            System.out.println("You have " + numberOfPlots + " plots of land.");
        }

        for (int i = 0; i < numberOfPlots; i++) {
            boolean wiltChecker = false;
            int index = i+1;

            Plot plot = myPlots.get(i);

            String plotDetails = PlotDAO.printPlotDetails(plot);

            String[] checkPlotGrowthRate = plotDetails.split("\t");
            String plotGrowthChecker = checkPlotGrowthRate[checkPlotGrowthRate.length-1];

            if(plotGrowthChecker.equals("[##########] 100%")){
                wiltChecker = PlotDAO.produceYieldAndWiltChecker(plot);
                if(!wiltChecker){
                    grownList.add(index);
                }else{
                    wiltedList.add(index);
                }
            }else{
                if(plotGrowthChecker.equals("<empty>")){
                    emptyPlotList.add(index);
                }else{
                    growingList.add(index);
                }
            }

            if(wiltChecker){
                Crop crop = CropDAO.getCropById(plot.getCropID());
                System.out.println(index + ". " + crop.getName() + "\t" + "[  wilted  ]");
            }else{
                System.out.println(index + ". " + PlotDAO.printPlotDetails(plot));
            }
        }

        return result;
        
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