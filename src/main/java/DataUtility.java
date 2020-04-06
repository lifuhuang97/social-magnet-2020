package main.java;

import java.sql.*;
import java.util.ArrayList;

public class DataUtility {

    // Set constants for connection
    public static final String DB_NAME = "magnet";
    // modify port, username and password to ur db

    // // LIFU CHANGE
    public static final String CONNECTION_STRING = "jdbc:mysql://localhost:8889/" + DB_NAME + "?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    // // Macintosh
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";
    
    // Windows 
    // public static final String USERNAME = "root";
    // public static final String PASSWORD = "";

    // Multiple update, insert, delete operations
    public static void multiQueryUpdate(ArrayList<String> submittedstatements) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
            conn.setAutoCommit(false);

            ArrayList<PreparedStatement> statements = new ArrayList<>();

            for(String submittedstatement: submittedstatements){
                statements.add(conn.prepareStatement(submittedstatement));
            }

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
        }finally{
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Couldn't close connection: " + e.getMessage());
            }
        }
    }

    // Single update, insert, delete operations
    public static void queryUpdate(String statement){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
            // Set this to false to not automatically update database
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(statement);
            stmt.executeUpdate();
            conn.commit(); // commits the database transaction
            conn.close();
        }catch (SQLException e) {
            System.out.println("Error in a query: " + e.getMessage());
        }finally{
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Couldn't close connection: " + e.getMessage());
            }
        }
    }

    // Used when you create a new row in the database and require the newly created object id for other insertions
    public static int queryUpdateRetrieveID (String querystatement){
        Connection conn = null;
        int queryInfo = 0;
        int newID = 0;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
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
        }finally{
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Couldn't close connection: " + e.getMessage());
            }
        }
        return newID;
    }

    // For updating support tables (e.g. POST --> USER_POST)
    public static void associationUpdate (String table, int var1, int var2){

        Connection conn = null;;

        try {
            conn = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + table + " VALUES (" + var1 + ", " + var2 + " )");
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println("Failed to update pairings: " + e.getMessage());
        }finally{
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Couldn't close connection: " + e.getMessage());
            }
        }
    }

    // Select and return multiple rows
    public static ArrayList<ArrayList<String>> querySelect(String statement) {

        // instantiate connection 
        Connection conn = null;

        // results arraylist to be returned
        ArrayList<ArrayList<String>> allResults = new ArrayList<>();


        try {
            // start connection
            conn = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
            
            // prepare the statement w/ connection
            PreparedStatement stmt = conn.prepareStatement(statement);
            ResultSet rs = stmt.executeQuery();

            // get information about queried table
            ResultSetMetaData metadata = rs.getMetaData();

            // know how many columns exist in the query
            int columnCount = metadata.getColumnCount();

            // add queried data into arraylist as arraylist<string>
            while(rs.next()){
                ArrayList<String> newData = new ArrayList<>();
                for(int i=1; i<=columnCount; i++){
                    if(metadata.getColumnTypeName(i).equals("INT")){
                        // convert queried ints to string
                        newData.add(Integer.toString(rs.getInt(i)));
                    }else{
                        newData.add(rs.getString(i));
                    }
                }
                allResults.add(newData);
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Couldn't close connection: " + e.getMessage());
            }
        }
        return allResults;
    }

    // Select and return one row 
    public static ArrayList<String> singleQuerySelect(String statement) {

        // instantiate connection 
        Connection conn = null;

        // results arraylist to be returned
        ArrayList<String> result = new ArrayList<>();

        try {
            // start connection
            conn = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
            
            // prepare the statement w/ connection
            PreparedStatement stmt = conn.prepareStatement(statement);
            ResultSet rs = stmt.executeQuery();

            // get information about queried table
            ResultSetMetaData metadata = rs.getMetaData();

            // know how many columns exist in the query
            int columnCount = metadata.getColumnCount();

            // add queried data into arraylist as arraylist<string>
            while(rs.next()){
                for(int i=1; i<=columnCount; i++){
                    if(metadata.getColumnTypeName(i).equals("INT")){
                        // convert queried ints to string
                        result.add(Integer.toString(rs.getInt(i)));
                    }else{
                        result.add(rs.getString(i));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Couldn't close connection: " + e.getMessage());
            }
        }
        return result;
    }

}
