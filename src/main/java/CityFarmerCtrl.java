// // package main.java;

import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.Math;
/**
 * This is the central control of all displays after CityFarmerMenu
 */
public class CityFarmerCtrl {

    private UserProfile user;

    /** Returns a CityFarmerCtrl object that can be called to display farm sub screens for
     * the specified user
     * 
     * @param user initialize an instance of CityFarmerCtrl with given user
     */
    public CityFarmerCtrl(UserProfile user){
        this.user = user;
    }

    /** Displays farmland and its associated options
     * <p>
     * This method returns when M or F is called, which will determine the screen that the
     * user sees next. If M, 'main' is returned so that CityFarmerMenu will know to return
     * and hence the user can see the Social Magnet Main Menu straightaway.
     * 
     * @return 'main' or 'farm' or empty string
     */
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

    /** Displays the farm's store and its associated options for purchase.
     * <p>
     * This method returns when M or F is called, which will determine the screen that the
     * user sees next. If M, 'main' is returned so that CityFarmerMenu will know to return
     * and hence the user can see the Social Magnet Main Menu straightaway.
     * 
     * @return 'main' or 'farm' or empty string
     */
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

    /** Displays the user's inventory for view. Only M or F can be called.
     * <p>
     * This method returns when M or F is called, which will determine the screen that the
     * user sees next. If M, 'main' is returned so that CityFarmerMenu will know to return
     * and hence the user can see the Social Magnet Main Menu straightaway.
     * 
     * @return 'main' or 'farm' or empty string
     */
    public String readFarmInventoryOptions(){

        ArrayList<Inventory> items = InventoryDAO.retrieveByUserID(user.getUserId());
        Scanner sc= new Scanner(System.in);

        while (true) {
            CityFarmerMenu.displayFarmHeader("case3", user);
            for (int i = 0; i < items.size(); i ++) {
                System.out.println( "" + (i+1) + ". " + items.get(i).getQuantity() + " Bags of " + 
                CropDAO.retrieveName(items.get(i).getCropID()) );
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

    /** Displays the user's friends menu. User can input the index that his/her friend is
     * displayed at to access their farm.
     * <p>
     * This method returns when M or F is called, which will determine the screen that the
     * user sees next. If M, 'main' is returned so that CityFarmerMenu will know to return
     * and hence the user can see the Social Magnet Main Menu straightaway.
     * 
     * @return 'main', 'farm', empty string or a friend's username. If it returns a friend's
     * username, it triggers the CityFarmerMenu to pass this friend's username to the
     * method below, readFriendFarmMenuOptions, to display his/her farm.
     */
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

    /**Displays a friend's farm to the current user. The user can opt to steal
     * from any fully grown plots and gain XP and Gold if successful.
     * 
     * @param friendUsername This method takes in an user's username and displays
     * his/her farm to the currentUser with a different interface as compared to
     * viewing his/her own farm.
     * 
     * @return an ArrayList of Strings with index 0 being 'main', 'farm' or '' to
     * direct CityFarmerMenu accordingly, index 1 and index 2 being the gained XP
     * and Gold if the user successfully stole any yield from his/her friend. The
     * user's profile will be updated after the return and will be displayed
     * immediately.
     */
    public ArrayList<String> readFriendFarmMenuOptions(String friendUsername){

        ArrayList<String> returnWhere = new ArrayList<String>();
        returnWhere.add("");
        returnWhere.add("0");
        returnWhere.add("0");
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
                        if(!result.isEmpty()){
                            returnWhere.set(1,result.get(0));
                            returnWhere.set(2,result.get(1));
                        }
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

    /** Displays the farm's gift options. After choosing an item, the user will be prompted
     * to input someone's username. The user can input multiple usernames as long as they 
     * are comma separated with no space. These usernames must be his/her friend and the
     * user cannot send to a friend a gift if one has already been sent in the past 24 hours.
     * <p>
     * This method returns when M or F is called, which will determine the screen that the
     * user sees next. If M, 'main' is returned so that CityFarmerMenu will know to return
     * and hence the user can see the Social Magnet Main Menu straightaway.
     * 
     * @return 'main' or 'farm' or empty string
     */
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
            }while(choice.charAt(0) != 'R');
        
        return returnWhere;
    }

    /** This is a support method in CityFarmerCtrl that helps to process a gift sending
     *  process for the user. This method posts a gift post on the friend's wall and
     *  updates the Post, Gift database accordingly by calling the associated DAOs & Ctrl.
     * 
     * @param friend Selected friend to receive the gift
     * @param giftChoice The ID of the chosen gift
     */
    private void processGiftSending(UserProfile friend, int giftChoice){

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

    /** This is a support method in CityFarmerCtrl that prints a friend's name, title and gold
     *  after the user chooses to view his/her friend's farm.
     * 
     * @param friend
     */
    private void displayFriendFarmDetails(UserProfile friend){

        System.out.println("Name: " + friend.getFullName());
        System.out.println("Title: " + friend.getRank().getRankName());
        System.out.println("Gold: " + friend.getGold());
        
    }

    /** This is a support method that helps to print the full farm of an user. It has two versions
     *  which can be specified by the display methods above.
     * <p>
     * This method returns a nested HashMap that will help in validation in the display methods. For
     * example, if a plot is wilted, the user can only clear it. If the plot is empty, the user can
     * only plant on it. If the plot is growing, nothing can be done. If the plot is grown, users can
     * harvest or steal from it.
     * 
     * @param user The selected user's profile
     * @param version 1 for self, 2 for friend
     * @return A nested HashMap of the categorization of plots in their various phases
     * (empty / growing / grown / wilted) to help in validation for the farmland options.
     */
    private HashMap<String,HashMap<Integer,Integer>> displayPlots(UserProfile user, int version){

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

    /** This method registers purchases that the user make at the shop screen. It will
     *  update the user's gold in this method and return the userprofile so that the
     *  change in gold can be updated immediately.
     *  <p>
     *  The user's gold and inventory are updated in the database in this method.
     * 
     * 
     * @param crop Crop object that's bought
     * @param quantity quantity of the crop bought
     * @param userprofile the user who made the purchase
     * @return updated userprofile of the purchasing user (reflects change in gold)
     */
    private UserProfile registerPurchase (Crop crop, int quantity, UserProfile userprofile) {
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

    /** The user has to select from the seeds that he owns
     *  to plant a crop on the selected plot.
     *  <p>
     *  This method plants the seed in the selected plot.
     *  This method can only be called on an empty plot.
     * 
     * @param plotId selected plot ID
     * @param choiceId selected crop ID within the method
     * @return 'main', 'farm' or '' to direct the farmlandMenu
     *  to the supposed to reach page
     */
    private String plantPlot(int plotId, int choiceId){

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

    /** This method clears the plot of wilted crops and 
     *  updates the database accordingly. This method can
     *  only be called when the plot has wilted crops.
     * 
     * 
     * @param plotId   ID of the plot to clear
     * @param choiceId Index number selected at the menu
     */
    private void clearPlot(int plotId, int choiceId){
        // reset plot's cropId and planted time
        Plot selectedPlot = PlotDAO.getPlotById(plotId);

        PlotDAO.removeCrop(selectedPlot);

        System.out.println("Plot " + choiceId + " has been cleared.");

    }

    /** This method harvests a plot that is fully grown.
     *  This method can only be called on a fully grown plot.
     *  <p>
     *  This method returns the updated userprofile after
     *  updating the user's XP and GOLD from harvest in the database.
     * 
     * @param plotId ID of plot to harvest from
     * @param user UserProfile object of the user
     * @return
     */
    private UserProfile harvestPlot(int plotId, UserProfile user){
        
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

    /** This method processes a steal after secondary validations.
     *  This method can only be reached if the selected friend has fully grown plots.
     *  <p>
     *  This method validates whether
     *  1) The user has already stolen from this friend
     *  2) The plot has already been stolen by more than 20%
     *  3) The user is stealing more than 5% in this current heist session
     *  
     * 
     * @param friend the friend UserProfile object that is chosen to steal from
     * @param stealable A hashmap of indexIds to plotIds of stealable plots
     * 
     * @return an ArrayList of Strings to help redirect the previous screen. The
     *  arraylist includes three elements:
     * 1) Direction: either "main", "farm" or "". Works the same as abovementioned
     * 2) Any XP change. Either a string value of XP Gained, or "".
     * 3) Any Gold change. Either a string value of Gold gained, or "".
     */


    private ArrayList<String> processSteal(UserProfile friend, HashMap<Integer,Integer> stealable){

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
                    if(totalStolenPercentage < 5){

                        double existingProduce = (double) currentPlot.getProductAmt();
                        double currentStolen = (double) currentPlot.getStolen();

                        double percentageStealable = (20 - currentStolen) / 100;
                        double temp = existingProduce * percentageStealable;

                        double availableToSteal = Math.floor(temp);

                        double randomPercentage = (1 + (new Random().nextInt(5-totalStolenPercentage))) * 0.01;

                        double randomedStealAmt = randomPercentage * existingProduce;

                        if(randomedStealAmt < 1){
                            randomedStealAmt = 1;
                        }

                        double finalStolen = Math.min(availableToSteal, randomedStealAmt);

                        double stolenPercentage = finalStolen / existingProduce * 100;

                        double finalStolenPercentage = Math.floor(stolenPercentage);


                        tallyXp += finalStolen * cropXp;
                        tallyGold += finalStolen * cropGold;

                        double newStolen = finalStolenPercentage + currentStolen;

                        double newProduce = existingProduce - finalStolen;

                        PlotDAO.updatePlotStolenAmt(plotId, (int)finalStolenPercentage, (int)newProduce);
                        UserStealDAO.addStealRecord(myUserId, plotId);

                        if(stolenRecords.computeIfPresent(currentCropName,(k,v)->v+(int)finalStolen) == null){
                            stolenRecords.put(currentCropName,(int)finalStolen);
                        }

                        totalStolenPercentage += (int) finalStolenPercentage;
                    }
                }else{
                    System.out.println("This user cannot be stolen from anymore.");
                    return result;
                }
            }else{
                System.out.println("You have already stolen from this user.");
                return result;
            }
        }

        result.set(0,Integer.toString(tallyXp));
        result.set(1,Integer.toString(tallyGold));
        
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