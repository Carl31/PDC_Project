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
    String url = "jdbc:derby:VerbCards_Ebd; create=true"; //"jdbc:derby:VerbCardsDB_Ebd; create=true"; //url of the DB host
    String dbusername = "pdc";  //your DB username
    String dbpassword = "pdc";  //your DB password
    private static int userIdNum = 0;
    private static int wordIdNum = 0;

    //Setting up DerbyDB
    public void dbsetup() {
        try {
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            System.out.println(url + " connected...");
            Statement statement = conn.createStatement();
            String table = "";

            table = "UserData";
            if (!checkIfTableExists(table)) {
                statement.executeUpdate("CREATE TABLE " + table + " (userId INT, username VARCHAR(20), gamesPlayed INT, correctPercent FLOAT)");
            }

            table = "WordData";
            if (!checkIfTableExists(table)) {
                statement.executeUpdate("CREATE TABLE " + table + " (wordId INT, spanish VARCHAR(20), english VARCHAR(20))");
            }

            table = "UserWordMap";
            if (!checkIfTableExists(table)) {
                statement.executeUpdate("CREATE TABLE " + table + " (userId INT, wordId INT)");
            }
            statement.close();
        } catch (Throwable e) {
            System.err.println(e);
        }
    }

    //Method to check the username for the login. If no user exists, create it with empty scores.
    public ModelData getUser(String username) {
        ModelData data = new ModelData();

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT userId, username, gamesPlayed, correctPercent FROM UserData WHERE username = '" + username + "'");

            if (rs.next()) { // i.e if user found in USERDATA table, update ModelData
                String name = rs.getString("username");
                System.out.println("Welcome back, " + name + "!");

                // get user data ...
                // get hashset of words from user_id to word_id table mappings
                HashSet<Word> incorrectWords = getWords(Integer.parseInt(rs.getString("userId")));
                data = new ModelData(username, new UserData(rs.getInt("gamesPlayed"), rs.getFloat("correctPercent"), incorrectWords));

            } else { // if user not found, then create new user using the input username
                System.out.println("Creating new user: " + username);

                // set user data
                HashSet<Word> incorrectWords = new HashSet<Word>();
                data = new ModelData(username, new UserData(0, 0.0f, incorrectWords));

                // insert user data into db
                statement.executeUpdate("INSERT INTO UserData VALUES(" + userIdNum + ", '" + username + "', 0, 0.0)");
                userIdNum++;
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
            updateMappingTable(username, incorrectWords);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    // a function for getting words set based on userId
    public HashSet<Word> getWords(int id) {
        HashSet<Word> usersWords = new HashSet<>();
        String spanish = "";
        String english = "";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT wordId FROM UserWordMap WHERE userId = " + id + ""); // take away '

            ResultSet wordsRs = null;

            while (rs.next()) { // get all user words
                int wordId = Integer.parseInt(rs.getString("wordId"));
                wordsRs = statement.executeQuery("SELECT spanish, english FROM WordData WHERE wordId = " + wordId + ""); // take away '

                spanish = wordsRs.getString("spanish");
                english = wordsRs.getString("english");

                usersWords.add(new Word(spanish, english));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return usersWords;
    }

    public void insertTestData() {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO WordData VALUES(0, 'Hola', 'Hello')");
            HashSet<Word> tempWords = new HashSet<Word>();
            tempWords.add(new Word("Hola", "Hello"));
            updateMappingTable("John", tempWords);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // a function for updating user-word mappings
    private void updateMappingTable(String username, HashSet<Word> words) {
        // for all words in the set, add the word-user map entry if it already doesn't exist                
        HashSet<Word> incorrectWords = new HashSet<Word>();
        String userId = "";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT userId FROM UserData WHERE username = '" + username + "'");

            if (rs.next()) { // i.e if user found in USERDATA table, get users incorrect words
                userId = rs.getString("userId");

                // get hashset of words from user_id to word_id table mappings
                incorrectWords = getWords(Integer.parseInt(userId));

            } else { // if user not found, then create new user using the input username
                throw new SQLException("User not found.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        // check what words in words set are not present in incorrectWords set, then add them to users incorrect words mapping table...
        HashSet<Word> unaddedWords = new HashSet<Word>();
        for (Word word : words) {
            if (!incorrectWords.contains(word)) {
                unaddedWords.add(word);
            }
        }

        // add unaddedWords to the user word mappings...
        String wordId = "";
        try {
            Statement statement = conn.createStatement();
            for (Word word : unaddedWords) {
                ResultSet rs = statement.executeQuery("SELECT wordId FROM WordData WHERE spanish = '" + word.getSpanish() + "'");

                if (rs.next()) { // i.e if word is found in WORDDATA table, add it in USERWORDMAP table
                    wordId = rs.getString("wordId");

                    // add userId-wordId table entry
                    statement.executeUpdate("INSERT INTO USERWORDMAP VALUES(" + userId + ", " + wordId + ")");

                } else { // if user not found, then create new user using the input username
                    throw new SQLException("Word not found.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
