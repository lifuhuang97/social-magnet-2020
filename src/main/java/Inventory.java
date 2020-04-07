// package main.java;

import java.util.*;

public class Inventory {
    private int userID;
    private int cropID;
    private int quantity;


    public Inventory(int userID, int cropID, int quantity) {
        this.userID = userID;
        this.cropID = cropID;
        this.quantity = quantity;
    }

    public int getUserID() {
        return userID;
    }

    public int getCropID() {
        return cropID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity (int amount) {
        quantity += amount;
    }

    public void deductQuantity (int amount) {
        quantity -= amount;
    }
}