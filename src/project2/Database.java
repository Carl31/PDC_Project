/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carls
 */
public class Database {
    Connection conn = null;
    String url = "jdbc:derby:VerbCardsDB;create=true"; //url of the DB host
    String dbusername = "pdc";  //your DB username
    String dbpassword = "pdc";  //your DB password
    
    //Setting up DerbyDB
    public void dbsetup() {
        try {
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            Statement statement = conn.createStatement();
            String table = "";
            
            table = "UserData";
            if (!checkIfTableExists(table)) {
                statement.executeUpdate("CREATE TABLE " + table + " (username VARCHAR(20), gamesPlayed INT, correctPercent FLOAT");
            }
            
            table = "WordData";
            if (!checkIfTableExists(table)) {
                statement.executeUpdate("CREATE TABLE " + table + " (spanish VARCHAR(20), english VARCHAR(20)");
            }
            
            table = "UserWordMap";
            if (!checkIfTableExists(table)) {
                statement.executeUpdate("CREATE TABLE " + table + " (userId INT, wordId INT");
            }
            statement.close();
        } catch (Throwable e) {
            System.out.println(e);
        }
    }
    
    //Method to check the username for the login. If no user exists, create it with empty scores.
    public ModelData getUser(String username) {
         ModelData data = new ModelData();
         
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT username, gamesPlayed, correctPercent FROM UserData WHERE username = '" + username + "'");
             
            if (rs.next()) { // i.e if user found in USERDATA table, update ModelData
                String name = rs.getString("username");
                System.out.println("Welcome back, "+name+"!");
                
                // get user data
                HashSet<Word> incorrectWords = new HashSet<Word>();
                // TODO: get hashset of words from user_id to word_id table mappings
                data = new ModelData(username, new UserData(rs.getInt("gamesPlayed"), rs.getFloat("correctPercent"), incorrectWords));
                
                    
            } else { // if user not found, then create new user using the input username
                System.out.println("Creating new user: "+username);
                
                // set user data
                HashSet<Word> incorrectWords = new HashSet<Word>();
                data = new ModelData(username, new UserData(0, 0.0f, incorrectWords));
                
                // insert user data into db
                statement.executeUpdate("INSERT INTO UserData "
                        + "VALUES('" + username + "', '" + data.getUser().getGamesPlayed() + "', '" + data.getUser().getCorrectPercent() + "')");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    private boolean checkIfTableExists(String newTableName) {
        boolean isPresent = false;
        try {
            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);
            while (rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(newTableName) == 0) {
                    System.out.println(tableName + " is present.");
                    isPresent = true;
                }
            }
            if (rsDBMeta != null) {
                rsDBMeta.close();
            }
        } catch (SQLException ex) {
        }
        return isPresent;
    }
    
    // saves user data to userdata table
    public void UpdateUserData(String username, UserData data, HashSet<Word> incorrectWords) {
        Statement statement;
        try {
            statement = conn.createStatement();
            statement.executeUpdate("UPDATE UserData SET gamesPlayed=" + data.getGamesPlayed() + " WHERE username='" + username + "'");
            statement.executeUpdate("UPDATE UserData SET correctPecent=" + data.getCorrectPercent() + " WHERE username='" + username + "'");
            // TODO: update users incorrectWords (update user_id to word_id mapping table)
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // TODO: make a function for updating user-word mappings
    // TODO: make a function for getting user-word mappings
}
