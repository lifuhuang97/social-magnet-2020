// package main.java;

import java.sql.*;
import java.util.ArrayList;


/** This class contains all the methods that
 *  accesses the established database "magnet"
 */
public class DataUtility {

    // Set constants for connection
    public static final String DB_NAME = "magnet";

    // modify port, username and password to ur db
    public static final String CONNECTION_STRING = "jdbc:mysql://localhost:8889/" + DB_NAME + "?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    // Macintosh
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";
    
    // Windows 
    // public static final String USERNAME = "root";
    // public static final String PASSWORD = "";


    
    /** This method helps one to execute more than one insert/update/delete 
     * statements to change multiple records in the database at once.
     * 
     * @param submittedstatements An arraylist of statements to be executed
     */
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

    /** This method helps one to execute a single insert/update/delete statement
     * to change one record in the database.
     * 
     * @param statement The statement to be executed
     */
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
 
    /** This method is used when you need the auto_incremented table ID after 
     *  inserting a data. Used especially for posts & comments.
     * 
     * @param querystatement The statement to be executed
     * @return the unique ID thats generated from the database.
     */
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



    /** This method returns a nested ArrayList of query results for a select query
     *  that returns more than one row.
     * 
     * @param statement The statement to be executed
     * @return An arraylist of all the results, each row stored in one arraylist of strings
     */
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

    /** This method returns an ArrayList<String> that contains a singular result.
     *  This method is only used when the caller is sure that there's only one result.
     * 
     * @param statement The statement to be executed
     * @return The queried row of database data stored in an ArrayList of Strings
     */
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
