// // package main.java;

import java.util.*;

public class CityFarmerCtrl {

    public static String readFarmlandOptions(UserProfile user){

        HashMap<String,HashMap<Integer,Integer>> status;

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
                    HashMap<Integer,Integer> plantablePlots = status.get("empty");

                    Set<Integer> plantableIndexes = plantablePlots.keySet();

                    if(plantableIndexes.contains(num)){
                        plantPlot(plantablePlots.get(num), num);
                    }else{
                        System.out.println("Invalid action!");
                    }
                    break;
                case "C" :
                    HashMap<Integer,Integer> clearablePlots = status.get("wilted");

                    Set<Integer> clearableIndexes = clearablePlots.keySet();

                    if(clearableIndexes.contains(num)){
                        //perform clear crop
                    }else{
                        System.out.println("Invalid action!");
                    }
                    break;
                case "H" :
                    HashMap<Integer,Integer> harvestablePlots = status.get("grown");

                    Set<Integer> harvestableIndexes = harvestablePlots.keySet();
                    
                    if(harvestableIndexes.contains(num)){
                        user = harvestPlot(harvestablePlots.get(num), user);
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


    public static HashMap<String,HashMap<Integer,Integer>> displayPlots(UserProfile user, int version){

        HashMap<String,HashMap<Integer,Integer>> result = new HashMap<String,HashMap<Integer,Integer>>();

        HashMap<Integer,Integer> emptyPlotList = new HashMap<Integer,Integer>();
        HashMap<Integer,Integer> growingList = new HashMap<Integer,Integer>();
        HashMap<Integer,Integer> grownList = new HashMap<Integer,Integer>();
        HashMap<Integer,Integer> wiltedList = new HashMap<Integer,Integer>();

        result.put("grown",grownList);
        result.put("wilted", wiltedList);
        result.put("empty", emptyPlotList);
        result.put("growing",growingList);

        // version 1 is self, version 2 is friend

        ArrayList<Plot> myPlots = PlotDAO.getMyPlots(user);

        // System.out.println("these are my plots" + myPlots.toString());
        int numberOfPlots = myPlots.size();

        if(version == 1){
            System.out.println("You have " + numberOfPlots + " plots of land.");
        }


        for (int i = 0; i < numberOfPlots; i++) {
            boolean wiltChecker = false;
            int index = i+1;

            Plot plot = myPlots.get(i);

            int plotId = plot.getPlotID();

            String plotDetails = PlotDAO.printPlotDetails(plot);


            String[] checkPlotGrowthRate = plotDetails.split("\t");
            String plotGrowthChecker = checkPlotGrowthRate[checkPlotGrowthRate.length-1];

            if(plotGrowthChecker.equals("[##########] 100%")){
                wiltChecker = PlotDAO.produceYieldAndWiltChecker(plot);
                if(!wiltChecker){
                    grownList.put(index,plotId);
                }else{
                    wiltedList.put(index,plotId);
                }
            }else{
                if(plotGrowthChecker.equals("<empty>")){
                    emptyPlotList.put(index,plotId);
                }else{
                    growingList.put(index,plotId);
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

    public static String plantPlot(int plotId, int choiceId){

        Scanner sc = new Scanner(System.in);

        // get inventory of items
        int userId = PlotDAO.retrievePlotOwner(plotId);
        ArrayList<Inventory> inventory = InventoryDAO.retrieveByUserID(userId);

        ArrayList<String> cropsThatIHave;
        ArrayList<Crop> cropThatIHaveToBeProcessed = new ArrayList<Crop>();

        String choice;
        int choiceIndex = 0;

        do{

            cropsThatIHave = new ArrayList<String>();

            int index = 1;
            System.out.println();
            System.out.println("Select the crop:");

            for(Inventory inv : inventory){

                Crop currentCrop = CropDAO.getCropById(inv.getCropID());
                String currentCropName = currentCrop.getName();

                cropThatIHaveToBeProcessed.add(currentCrop);
                cropsThatIHave.add(currentCropName);
                
                System.out.println(index + ". " + currentCropName);
                index++;
            }

            System.out.print("[M]ain | City [F]armers | Select Choice > ");

            choice = sc.nextLine();

            if(!choice.equals("F") || !choice.equals("M")){
                int length = choice.length();
                boolean isNumber = Character.isDigit(choice.charAt(0));

                if(length == 1 && isNumber){
                    choiceIndex = Integer.parseInt(choice);
                    if(choiceIndex <= (cropsThatIHave.size())){
                        choice = "Y";
                    }else{
                        choice = "N";
                    }
                }else{
                    choice = "N";
                }
            }

            switch(choice){
                case "M":
                    return "main";
                case "F":
                    return "farm";
                case "Y":
                    break;
                default:
                    System.out.println("You did not enter a valid option.");
            }
        }while (choice.charAt(0) != 'Y');


        Inventory inventoryUsed = inventory.get(choiceIndex -1);

        Crop chosenToPlant = cropThatIHaveToBeProcessed.get(choiceIndex-1);

        int chosenToPlantId = inventoryUsed.getCropID();

        InventoryDAO.updateInventory(userId,chosenToPlantId , inventoryUsed.getQuantity()-1);   

        Plot utilizedPlot = PlotDAO.getPlotById(plotId);

        PlotDAO.plantNewCrop(utilizedPlot, chosenToPlantId);

        System.out.println(chosenToPlant.getName() + " planted on plot " + choiceId);

        return "";
    }

    public static void clearPlot(int plotId){
        // reset plot's cropId and planted time

    }

    public static UserProfile harvestPlot(int plotId, UserProfile user){
        
        Plot selectedPlot = PlotDAO.getPlotById(plotId);
        int curUserId = user.getUserId();

        int harvestedQty = selectedPlot.getProductAmt();

        Crop cropThatsGrowingHere = CropDAO.getCropById(selectedPlot.getCropID());

        int cropExp = cropThatsGrowingHere.getXp();
        int cropGold = cropThatsGrowingHere.getSaleprice();

        int earnedXp = harvestedQty * cropExp;
        int earnedGold = harvestedQty * cropGold;

        // Update user's gold and xp
        user = UserProfileDAO.updateUserGoldAndXp(user, earnedXp, earnedGold);
        if(UserProfileDAO.checkIfRankUpdate(user)){
            user = UserProfileDAO.getUserProfileByUserId(curUserId);
        }
        
        // Empty plot to default state
        PlotDAO.removeCrop(selectedPlot);

        System.out.println("You have harvested " + harvestedQty + " " + cropThatsGrowingHere.getName() 
                           + " for " + earnedXp + " XP and " + earnedGold + " gold.");

        return user;
    }
    

}