// // package main.java;

import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.Math;

public class CityFarmerCtrl {

    private UserProfile user;

    public CityFarmerCtrl(UserProfile user){
        this.user = user;
    }

    public String readFarmlandOptions(){

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
                        clearPlot(clearablePlots.get(num), num);
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

    public String readFarmStoreOptions(){

        String choice;
        String secondChoice;
        Scanner sc = new Scanner(System.in);
        ArrayList<Crop> crops;

        do{
            int itemChoice = 0;
            int quantityChoice = 0;
            CityFarmerMenu.displayFarmHeader("case2", user);
            System.out.println("Seeds Available:");
            crops = CropDAO.retrieveAll();
            for (int i = 0; i < crops.size(); i ++) {
                Crop item = crops.get(i);
                String name = item.getName();
                int nameLength = name.length();

                String gap = "";
                for(int j=0;j<(10-nameLength);j++){
                    gap += " ";
                }
                
                System.out.println("" + (i+1) + "." + name + gap + " - " + item.getCost() + " gold");

                int harvestTime = item.getHarvestTime();
                String harvestTimeSuffix = " mins";

                if(harvestTime > 60 && harvestTime % 60 == 0){
                    harvestTime = harvestTime / 60;
                    harvestTimeSuffix = " hours";
                }

                System.out.println("  Harvest in: " + harvestTime + harvestTimeSuffix);
                System.out.println("  XP Gained: " + item.getXp());
            }
            System.out.println();
            System.out.print("[M]ain | City [F]armers | ");
            System.out.print("Select choice > ");

            choice = sc.nextLine();

            if(choice.equals("M")){
                System.out.println();
                return "main";
            }else if(choice.equals("F")){
                System.out.println();
                return "farm";
            }

            try{
                itemChoice = Integer.parseInt(choice);

                if(itemChoice <= crops.size()){
                    choice = "Y";
                }else{
                    choice = "N";
                }
            }catch(NumberFormatException e){
                choice = "N";
            }

            switch(choice){
                case "Y" :
                    System.out.print("Enter quantity > ");
                    String qtyStr = sc.nextLine();

                    try{
                        quantityChoice = Integer.parseInt(qtyStr);
                        secondChoice = "Y";
                    }catch(NumberFormatException e){
                        secondChoice = "N";
                    }

                    switch(secondChoice){
                        case "Y":
                            Crop cropChoice = crops.get(itemChoice-1);
                            user = registerPurchase(cropChoice, quantityChoice, user);
                            break;
                        case "N":
                            System.out.println("Invalid action!");
                            break;
                        default:
                            System.out.println("Invalid action!");
                    }
                    break;
                case "N" :
                    System.out.println("Invalid action!");
                    break;
                default :
                    System.out.println("Invalid action!");
            }

        }while(choice.charAt(0) != 'M' | choice.charAt(0) != 'F');

        return "";
    }

    public String readFarmInventoryOptions(){

        ArrayList<Inventory> items = retrieveInventory();
        Scanner sc= new Scanner(System.in);

        while (true) {
            CityFarmerMenu.displayFarmHeader("case3", user);
            for (int i = 0; i < items.size(); i ++) {
                System.out.println( "" + (i+1) + ". " + items.get(i).getQuantity() + " Bags of " + 
                retrieveCropNameByCropID(items.get(i).getCropID()) );
            }
            System.out.print("[M]ain | City [F]armers | ");
            System.out.print("select choice > ");

            String choice = sc.nextLine();

            if (choice.equals("M")) {
                return "main";
            } else if (choice.equals("F")) {
                return "farm";
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    public String readFriendMenuOptions(){

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

    public ArrayList<String> readFriendFarmMenuOptions(String friendUsername){

        ArrayList<String> returnWhere = new ArrayList<String>();
        returnWhere.add("");
        String choice;
        Scanner sc = new Scanner(System.in);
        

        UserProfile friend = UserProfileDAO.getUserProfileByUsername(friendUsername);

        do{
            System.out.println();
            displayFriendFarmDetails(friend);
            HashMap<Integer,Integer> stealable = displayPlots(friend, 2).get("grown");

            int rangeLimit = stealable.size();
            
            System.out.print("[M]ain | City [F]armers | [S]teal > ");
            choice = sc.nextLine();
            int num = 0;

            switch(choice){
                case "M":
                    returnWhere.set(0,"main");
                    return returnWhere;
                case "F":
                    returnWhere.set(0,"farm");
                    return returnWhere;
                case "S":
                    if(!stealable.isEmpty()){
                        ArrayList<String> result = processSteal(friend,stealable);
                        returnWhere.addAll(result);
                    }else{
                        System.out.println("There isn't any produce for you to steal.");
                    }
                    break;
                default:
                    System.out.println("You did not enter a valid option.");
            }
        }while(choice != "M" || choice != "F");
        sc.close();

        return returnWhere;

    }

    public String readGiftOptions(){

        String returnWhere = "";
        String choice;
        Scanner sc = new Scanner(System.in);
        int giftChoice = 0;

        do{
            CityFarmerMenu.displayFarmHeader("case5", user);
            System.out.println("Gifts Available:");
            System.out.println("1. 1 Bag of Papaya Seeds");
            System.out.println("2. 1 Bag of Pumpkin Seeds");
            System.out.println("3. 1 Bag of Sunflower Seeds");
            System.out.println("4. 1 Bag of Watermelon Seeds");
            System.out.print("[R]eturn to main | Select choice > ");
            
            choice = sc.nextLine();

            if(choice.equals("R")){
                System.out.println();
                return "main";
            }

            try{
                giftChoice = Integer.parseInt(choice);

                if(giftChoice > 0 && giftChoice < 5){
                    choice = "Y";
                }else{
                    choice = "N";
                }
            }catch(InputMismatchException | NumberFormatException e){
                choice = "N";
            }

            switch(choice){
                case "Y":
                    System.out.print("Send to> ");
                    String sendToString = sc.nextLine();

                    String[] inputUsernames = sendToString.split(",");

                    Pattern p = Pattern.compile("[^a-z0-9 !.,@&:()$]", Pattern.CASE_INSENSITIVE);    

                    ArrayList<UserProfile> validFriends = new ArrayList<UserProfile>();

                    //Validity check
                    for(String username : inputUsernames){
                        Matcher m = p.matcher(username);
                        boolean hasSpecialCharacters = m.find();

                        if(hasSpecialCharacters){
                            System.out.println("Invalid username input, please try again");
                            break;
                        }else{
                            // check if is valid userprofile
                            UserProfile friendProfile = UserProfileDAO.getUserProfileByUsername(username);
                            if(friendProfile != null){
                                int friendId = friendProfile.getUserId();
                                // check if is a friend
                                if(FriendsDAO.isFriends(this.user.getUserId(),friendId)){
                                    validFriends.add(friendProfile);
                                }else{
                                    System.out.println(username + " is not your friend.");
                                    break;
                                }
                            }
                        }
                    }

                    boolean ValidToProceed = true;

                    if(validFriends.size() == inputUsernames.length){

                        // check if already sent a gift to any of the friends in the past 24hr
                        for(UserProfile friend : validFriends){

                            String friendUsername = friend.getUsername();

                            if(GiftDAO.checkIfSentGift(this.user, friend)){
                                System.out.println(friendUsername + " has already received a gift from you in the past 24 hours.");
                                ValidToProceed = false;
                                break;
                            }
                        }
                    }else{
                        System.out.println("Invalid friends' username input, please try again.");
                        break;
                    }

                    if(ValidToProceed){
                        for(UserProfile friend : validFriends){
                            processGiftSending(friend, giftChoice);
                        }
                        System.out.println("Gift posted to your friends' wall");
                        break;
                    }

                case "N":
                    System.out.println("Invalid choice!");
                    break;
                }
                System.out.println();
            }while(choice.charAt(0) != 'R');
        
        return returnWhere;
    }


    public void processGiftSending(UserProfile friend, int giftChoice){

        // giftchoice 1 is papaya, 2 is pumpkin, 3 is sunfloewr, 4 is watermelon (same as cropID)

        WallCtrl userWallCtrl = new WallCtrl(this.user);

        Crop selectedGift = CropDAO.getCropById(giftChoice);
        String cropName = selectedGift.getName();

        String postMessage = "Here is a bag " + cropName + " seeds for you. - City Farmers";

        // Create new post on friend wall and get post ID
        String postID = userWallCtrl.post(postMessage, friend, 2);

        // Create new gift in DB thats linked to postID
        GiftDAO.createNewGift(this.user.getUserId(), friend.getUserId(), postID, giftChoice);

    }


    public void displayFriendFarmDetails(UserProfile friend){

        System.out.println("Name: " + friend.getFullName());
        System.out.println("Title: " + friend.getRank().getRankName());
        System.out.println("Gold: " + friend.getGold());
        
        
    }

    public HashMap<String,HashMap<Integer,Integer>> displayPlots(UserProfile user, int version){

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

    public String retrieveCropNameByCropID (int cropID) {
        return CropDAO.retrieveName(cropID);
    }

    public ArrayList<Inventory> retrieveInventory () {
        return InventoryDAO.retrieveByUserID(user.getUserId());
    }

    public UserProfile registerPurchase (Crop crop, int quantity, UserProfile userprofile) {
        int cost = crop.getCost()*quantity*(-1);
        int newGold = userprofile.getGold() + cost;

        if (newGold >= 0) {
            UserProfile updatedUser = UserProfileDAO.updateUserGoldAndXp(userprofile, 0, cost);
            System.out.println("" + quantity + " bags of seeds purchased for " + (cost*-1) + " gold.");
            InventoryDAO.updateInventory(updatedUser.getUserId(), crop.getCropID(), quantity);
            return updatedUser;
        } else {
            System.out.println("Insufficient gold.");
            return userprofile;
        }
    }

    public String plantPlot(int plotId, int choiceId){

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

    public void clearPlot(int plotId, int choiceId){
        // reset plot's cropId and planted time
        Plot selectedPlot = PlotDAO.getPlotById(plotId);

        PlotDAO.removeCrop(selectedPlot);

        System.out.println("Plot " + choiceId + " has been cleared.");

    }

    public UserProfile harvestPlot(int plotId, UserProfile user){
        
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
        
        // Check for rankup, add plot if yes
        PlotDAO.checkIfPlotCountNeedsUpdating(user);

        // Empty plot to default state
        PlotDAO.removeCrop(selectedPlot);

        System.out.println("You have harvested " + harvestedQty + " " + cropThatsGrowingHere.getName() 
                           + " for " + earnedXp + " XP and " + earnedGold + " gold.");

        return user;
    }

    /**
     * 1. Check if there's a record of u stealing from this plot already. If no, continue
     * 
     // TODO: FOR EACH PLOT
     * 
     * FLOW: 2. Check stealable percentage of current plot, get produce stealable by * percentage
     * get lowest value.
     * 
     * 3. Tabulate stolen info into XP and gold tally.
     * 4. Update friend's plot details in DB
     * 5. Add a record into UserSteal
     * 
     * 
     * @param friend
     * @param stealable
     */

    public ArrayList<String> processSteal(UserProfile friend, HashMap<Integer,Integer> stealable){

        ArrayList<String> result = new ArrayList<String>();
        result.add("");
        result.add("");

        int myUserId = this.user.getUserId();
        // Note: stealable is a hashmap of hisPlotIndex -> plotId
        int totalStolenPercentage = 0;

        String finalStatement = "You have successfully stolen ";

        Map<String,Integer> stolenRecords = new HashMap<String,Integer>();

        int tallyXp = 0;
        int tallyGold = 0;

        Collection<Integer> plotIdsThatICanStealFrom = stealable.values();

        for(Integer plotId : plotIdsThatICanStealFrom){

            // Check if already stolen
            if(!UserStealDAO.checkIfAlreadyStole(myUserId, plotId)){

                Plot currentPlot = PlotDAO.getPlotWithStealableById(plotId);
                Crop currentCropGrown = CropDAO.getCropById(currentPlot.getCropID());
                String currentCropName = currentCropGrown.getName();

                int cropXp = currentCropGrown.getXp();
                int cropGold = currentCropGrown.getSaleprice();

                if(currentPlot.getStolen() < 20){

                    int existingProduce = currentPlot.getProductAmt();
                    int currentStolen = currentPlot.getStolen();

                    double percentageStealable = (20 - currentStolen) * 0.01;
                    double temp = existingProduce * percentageStealable;
                    double availableToSteal = (int) Math.floor(temp);

                    double randomPercentage = (1 + (new Random().nextInt(5))) * 0.01;
                    double randomedStealAmt = randomPercentage * existingProduce;
                    if(randomedStealAmt < 1){
                        randomedStealAmt = 1;
                    }

                    int finalStolen = (int) Math.min(availableToSteal, randomedStealAmt);


                    double stolenPercentage = finalStolen / existingProduce;
                    int finalStolenPercentage = (int) Math.floor(stolenPercentage);


                    tallyXp += finalStolen * cropXp;
                    tallyGold += finalStolen * cropGold;

                    int newStolen = finalStolenPercentage + currentStolen;

                    System.out.println("This is final stolen percentage" + finalStolenPercentage);
                    System.out.println("this is current stolen " + currentStolen);

                    int newProduce = existingProduce - finalStolen;

                    System.out.println("This is new stolen" + newStolen);
                    System.out.println("this is new produce" + newProduce);

                    PlotDAO.updatePlotStolenAmt(plotId, newStolen, newProduce);
                    UserStealDAO.addStealRecord(myUserId, plotId);

                    if(stolenRecords.computeIfPresent(currentCropName,(k,v)->v+finalStolen) == null){
                        stolenRecords.put(currentCropName,finalStolen);
                    }

                    

                }
            }else{
                System.out.println("You have already stolen from this user.");
                return result;
            }
        }

        result.set(0,Integer.toString(tallyXp));
        result.set(1,Integer.toString(tallyGold));

        // UserProfileDAO.updateUserGoldAndXp(this.user, tallyXp, tallyGold);
        // this.user = UserProfileDAO.getUserProfileByUserId(myUserId);
        
        for(Map.Entry<String,Integer> entry : stolenRecords.entrySet()){
            String plural = "";

            if(entry.getValue() > 1){
                plural = "s";
            }

            finalStatement += "" + entry.getValue() + " " + entry.getKey() + plural + ",";

        }

        String finalStmtCut = finalStatement.substring(0, finalStatement.length()-1);
        finalStmtCut += " for " + tallyXp + "XP, and " + tallyGold + " gold.";

        System.out.println(finalStmtCut);
        return result;
    }

    
    

}