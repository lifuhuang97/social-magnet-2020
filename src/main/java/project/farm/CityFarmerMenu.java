/**
 * FarmMenu
 */

// package main.java;

import java.util.*;

/**
 * A class with static methods that can be called from social magnet menu
 * to display farm options.
 */
public class CityFarmerMenu {

    /** This is a static helper method to help display the standard starting interface of any farm sub
     *  screens. 
     * 
     * @param type Type of sub screen, goes by "case" + 1-5, or "main"
     * @param user the user object thats currently using the application
     */
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

    /** This is a static method that displays the farm main menu.
     *  Called from ProfileMenu only.
     *  <p>
     *  This is the primary screen of City Farmer. This method redirects
     *  requests to any sub screens in City Farmer by calling the associated
     *  methods.
     * 
     * 
     * @param user UserProfile object of current user
     */
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
                        user = UserProfileDAO.getUserProfileByUserId(user.getUserId());
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

    /** This method creates a ctrl of the current user object and
     *  displays his farmland.
     * 
     * @param user UserProfile object of current user
     * @return Redirection string with the value of
     * 'main' / 'farm' / ''. If 'main', returns from this screen
     *  and displays ProfileMenu. Else, stays in this screen.
     */
    public static String viewFarmMenu(UserProfile user){

        CityFarmerCtrl ctrl = new CityFarmerCtrl(user);

        String returnWhere = ctrl.readFarmlandOptions();
        return returnWhere;
    }

    /** This method creates a ctrl of the current user object and
     *  displays his farmland.
     * 
     * @param user UserProfile object of current user
     * @return Redirection string with the value of
     * 'main' / 'farm' / ''. If 'main', returns from this screen
     *  and displays ProfileMenu. Else, stays in this screen.
     */
    public static String accessStore(UserProfile user){

        CityFarmerCtrl ctrl = new CityFarmerCtrl(user);
        
        String returnWhere = ctrl.readFarmStoreOptions();
        return returnWhere;
    }

    /** This method creates a ctrl of the current user object and
     *  displays his inventory.
     * 
     * @param user UserProfile object of current user
     * @return Redirection string with the value of
     * 'main' / 'farm' / ''. If 'main', returns from this screen
     *  and displays ProfileMenu. Else, stays in this screen.
     */
    public static String accessMyInventory(UserProfile user){

        CityFarmerCtrl ctrl = new CityFarmerCtrl(user);
        
        String returnWhere = ctrl.readFarmInventoryOptions();
        return returnWhere;
    }

    /** This method creates a ctrl of the current user object and
     *  displays his friends.
     * 
     * @param user UserProfile object of current user
     * @return Redirection string with the value of
     * either a friend's username or an empty screen.
     * 
     * Helps to redirect the next method to the correct farm screen.
     */
    public static String accessFriendMenu(UserProfile user){

        CityFarmerCtrl ctrl = new CityFarmerCtrl(user);
        
        String returnWhere = ctrl.readFriendMenuOptions();
        return returnWhere;
    }

    /** This method creates a ctrl of the current user object and
     *  displays the selected friend's farm.
     * 
     * @param user UserProfile object of current user
     * @param friendUsername the username of the friend whose farm should be displayed
     * @return Redirection string with the value of
     * 'main' / 'farm' / ''. If 'main', returns from this screen
     *  and displays ProfileMenu. Else, stays in this screen.
     */
    public static String accessFriendFarmMenu(UserProfile user, String friendUsername){

        CityFarmerCtrl ctrl = new CityFarmerCtrl(user);
        
        UserProfile friendUser = UserProfileDAO.getUserProfileByUsername(friendUsername);

        PlotDAO.checkIfPlotCountNeedsUpdating(friendUser);

        ArrayList<String> returnWhere = ctrl.readFriendFarmMenuOptions(friendUsername);

        System.out.println(returnWhere.toString());

        if(!returnWhere.get(1).equals("")){
            UserProfileDAO.updateUserGoldAndXp(user, Integer.parseInt(returnWhere.get(1)), Integer.parseInt(returnWhere.get(2)));
        }
        UserProfile updatedUser = UserProfileDAO.getUserProfileByUserId(user.getUserId());

        user = updatedUser;

        return returnWhere.get(0);
    }

    public static String accessGiftMenu(UserProfile user){

        CityFarmerCtrl ctrl = new CityFarmerCtrl(user);
        
        String returnWhere = ctrl.readGiftOptions();
        return returnWhere;
    }

}