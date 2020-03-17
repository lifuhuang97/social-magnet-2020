package main.java.com.company;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

public class DataUtility {

    // Set constants for connection
    public static final String DB_NAME = "magnet";
    public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    private static Connection conn;
    // To open a connection

    public static boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    // To close a connection
    public static void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public static void multiQueryUpdate(ArrayList<PreparedStatement> statements) {

        try {
            // Set this to false to not automatically update database
            conn.setAutoCommit(false);

            for (PreparedStatement stmt : statements) {
                try {
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("Error in a query: " + e.getMessage());
                }
            }

            conn.commit(); // commits the database transaction
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
    }

    public static void queryUpdate(PreparedStatement stmt){

        try {
            // Set this to false to not automatically update database
            conn.setAutoCommit(false);
            stmt.executeUpdate();
            conn.commit(); // commits the database transaction
        }catch (SQLException e) {
            System.out.println("Error in a query: " + e.getMessage());
        }
    }

    public static int queryUpdateRetrieveID (String querystatement){

        int queryInfo = 0;
        int newID = 0;
        ResultSet rs = null;

        try {
            PreparedStatement stmt = conn.prepareStatement(querystatement, Statement.RETURN_GENERATED_KEYS);
            queryInfo = stmt.executeUpdate();

            if(queryInfo == 1){
                rs = stmt.getGeneratedKeys();
                if(rs.next()){
                    newID = rs.getInt(1);
                }
            }

        }catch(SQLException e){
            System.out.println("Error in query: " + e.getMessage());
        }
        return queryInfo;
    }

    public static void associationUpdate (String table, int var1, int var2){

        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + table + " VALUES (" + var1 + ", " + var2 + " )");
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println("Failed to update pairings: " + e.getMessage());
        }
    }

    public static ResultSet QuerySelect(PreparedStatement stmt){

        ResultSet rs = null;

        try{
            rs = stmt.executeQuery();
        }catch(SQLException e){
            System.out.println("Query failed: " + e.getMessage());
        }
        return rs;
    }

}




