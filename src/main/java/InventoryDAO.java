// package main.java;
import java.util.*;

public class InventoryDAO {

    /**
     * Retrieve an ArrayList of Inventory objects associated userID
     * @param userID to retrieve Inventory objects by
     * @return ArrayList of Inventory objects
     */
    public static ArrayList<Inventory> retrieveByUserID (int userID) {
        String stmt = "SELECT * FROM INVENTORY WHERE USERID = " + userID;
        ArrayList<Inventory> result = new ArrayList<>();

        ArrayList<ArrayList<String>> arrayResults = DataUtility.querySelect(stmt);

        for (ArrayList<String> each : arrayResults) {
            Inventory item = new Inventory(Integer.parseInt(each.get(0)), Integer.parseInt(each.get(1)), Integer.parseInt(each.get(2)));
            result.add(item);
        }
        
        return result;
    }

    /**
     * Update Inventory object 
     * @param userID userID associated with Inventory
     * @param cropID cropID associated with Inventory
     * @param quantity quantity associated with Inventory
     */
    public static void updateInventory (int userID, int cropID, int quantity) {
        String stmt = "SELECT * FROM INVENTORY WHERE USERID = " + userID + " AND CROPID = " + cropID;
        ArrayList<ArrayList<String>> arrayResults = DataUtility.querySelect(stmt);

        if (arrayResults.size() == 0) {
            String stmts = "INSERT INTO INVENTORY (userID, cropID, quantity) VALUES ('" + userID + "', '" + cropID + "', '" + quantity + "');";
            DataUtility.queryUpdate(stmts);
        } else {
            int newQuantity = Integer.parseInt(arrayResults.get(0).get(2)) + quantity;
            String stmts = "UPDATE INVENTORY SET QUANTITY = " + newQuantity + " WHERE USERID = " + userID + " AND CROPID = " + cropID;
            DataUtility.queryUpdate(stmts);
        }

    }

}