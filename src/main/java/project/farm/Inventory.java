package project.farm;

import java.util.*;

/**
 * This is the Inventory class
 */

public class Inventory {
    private int userID;
    private int cropID;
    private int quantity;

    /**
     * Constructs a specified Inventory object
     * @param userID the userID associated with Inventory
     * @param cropID  the cropID associated with Inventory
     * @param quantity the quantity associated with Inventory
     */
    public Inventory(int userID, int cropID, int quantity) {
        this.userID = userID;
        this.cropID = cropID;
        this.quantity = quantity;
    }

    /**
     * Gets the userID
     * @return the userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Gets the cropID
     * @return the cropID
     */
    public int getCropID() {
        return cropID;
    }

    /**
     * Gets the quantity
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Add quantity 
     * @param the amount to add
     */
    public void addQuantity (int amount) {
        quantity += amount;
    }

    /**
     * Deduct quantity
     * @param the amount to deduct
     */
    public void deductQuantity (int amount) {
        quantity -= amount;
    }
}