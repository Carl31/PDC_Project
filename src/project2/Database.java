/*
 * The database class for the application. Derby DB.
 */
package project2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    
    /*
     * Setting up the database -- create tables and injects word data if not present. 
    */
    public void dbsetup() {
        try {
            conn = DriverManager.getConnection(url, dbusername, dbpassword);
            System.out.println(url + " connected...");
            Statement statement = conn.createStatement();
            conn.setAutoCommit(false);
            String table = "";

            table = "UserData";
            if (!checkIfTableExists(table)) {
                statement.executeUpdate("CREATE TABLE " + table + " (userId INT, username VARCHAR(20), gamesPlayed INT, correctPercent FLOAT)");
            }

            table = "WordData";
            if (!checkIfTableExists(table)) {
                statement.executeUpdate("CREATE TABLE " + table + " (wordId INT, spanish VARCHAR(30), english VARCHAR(30))");
                // inject word data into db
                inject();
            }

            table = "UserWordMap";
            if (!checkIfTableExists(table)) {
                statement.executeUpdate("CREATE TABLE " + table + " (userId INT, wordId INT)");
            }

            // update static db ints...
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM WordData");
            rs.next();
            wordIdNum = rs.getInt(1);

            rs = statement.executeQuery("SELECT COUNT(*) FROM UserData");
            rs.next();
            userIdNum = rs.getInt(1);

            statement.close();
        } catch (Throwable e) {
            System.err.println(e);
        }
    }

    /*
    * Method to check the username for the login. If no user exists, create it with empty scores.
    */
    public ModelData getUser(String username) {
        ModelData data = new ModelData();

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT userId, username, gamesPlayed, correctPercent FROM UserData WHERE username = '" + username + "'");

            if (rs.next()) { // i.e if user found in USERDATA table, update ModelData
                String name = rs.getString("username");
                System.out.println("Welcome back, " + username + "!");

                // get user data ...
                // get hashset of words from user_id to word_id table mappings
                HashSet<Word> incorrectWords = getWords(Integer.parseInt(rs.getString("userId")));
                data = new ModelData(username, new UserData(rs.getInt("gamesPlayed"), rs.getFloat("correctPercent"), incorrectWords));
                rs.close();

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
        data.setIsLoggedIn(true);
        return data;
    }

    /*
     * Checks if a table exists in the database already
    */
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

    /*
    * Saves user data to userdata table
    */
    public void UpdateUserData(String username, UserData data, HashSet<Word> incorrectWords) {
        Statement statement;
        try {
            statement = conn.createStatement();
            statement.executeUpdate("UPDATE UserData SET gamesPlayed=" + data.getGamesPlayed() + " WHERE username='" + username + "'");
            statement.executeUpdate("UPDATE UserData SET correctPercent=" + data.getCorrectPercent() + " WHERE username='" + username + "'");
            // update users incorrectWords (update user_id to word_id mapping table) ...
            updateMappingTable(username, incorrectWords);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    * Closes connection to the database -- error-catching
    */
    public void closeConnections() {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /*
    * Gets users incorrect words as a set based on userId
    */
    public HashSet<Word> getWords(int id) {
        HashSet<Word> usersWords = new HashSet<>();
        String spanish = "";
        String english = "";
        try {
            Statement statement1 = conn.createStatement();
            ResultSet rs = statement1.executeQuery("SELECT wordId FROM UserWordMap WHERE userId = " + id + ""); // take away '

            ResultSet wordsRs = null;
            Statement statement2 = conn.createStatement();

            while (rs.next()) { // get all user words
                int wordId = Integer.parseInt(rs.getString("wordId"));
                wordsRs = statement2.executeQuery("SELECT spanish, english FROM WordData WHERE wordId = " + wordId + ""); // take away '
                wordsRs.next();
                spanish = wordsRs.getString("spanish");
                english = wordsRs.getString("english");

                usersWords.add(new Word(spanish, english));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return usersWords;
    }

    /*
    * Inserts test data into the database (for testing only)
    */
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

    /*
    * Updates the user-word mappings
    */ 
    private void updateMappingTable(String username, HashSet<Word> words) {
        // for all words in the set, add the word-user map entry if it already doesn't exist                
        HashSet<Word> incorrectWords = new HashSet<Word>();
        String userId = "";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT userId FROM UserData WHERE username = '" + username + "'");

            if (rs.next()) { // i.e if user found in USERDATA table, get users incorrect words
                userId = rs.getString("userId");

                // get hashset of words from user_id to word_id table mappings - removed below line to comply with game functionality
                // incorrectWords = getWords(Integer.parseInt(userId));
                clearUsersWords(userId);

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

    /*
    * Gets all words from database as an array list
    */
    public ArrayList<Word> getWordsAsArray() {
        ArrayList<Word> dbWords = new ArrayList<Word>();

        String spanish = "";
        String english = "";
        try {
            Statement statement1 = conn.createStatement();

            for (int id = 0; id < wordIdNum; id++) {
                ResultSet rs = statement1.executeQuery("SELECT spanish, english FROM WordData WHERE wordId = " + id + "");

                while (rs.next()) { // get all words

                    spanish = rs.getString("spanish");
                    english = rs.getString("english");

                    dbWords.add(new Word(spanish, english));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dbWords;
    }

    /*
    * Deletes all user incorrect words from database
    */
    private void clearUsersWords(String userId) {
        try {
            Statement statement = conn.createStatement();
            // add userId-wordId table entry
            statement.executeUpdate("DELETE FROM USERWORDMAP WHERE UserId = " + userId);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertWord(Word word) {
        try {
            Statement statement = conn.createStatement();

            statement.executeUpdate("INSERT INTO WordData VALUES(" + wordIdNum + ", '" + word.getSpanish() + "', '" + word.getEnglish() + "')");
            wordIdNum++;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Inserted: " + word.getSpanish()); // for testing
    }

    /*
    * Inserts an array list of words into database
    */
    public void insertWords(ArrayList<Word> words) {
        for (Word word : words) {
            insertWord(word);
        }
    }

    /*
    * Deletes a single word from the database (spanish word being the unique key)
    */
    public boolean deleteWord(String spanish) {
        boolean deleted = false;
        if (containsWord(spanish)) {
            try {
                Statement statement = conn.createStatement();

                statement.executeUpdate("DELETE FROM WordData WHERE spanish = '" + spanish + "'");
                wordIdNum++;
                deleted = true;
            } catch (SQLException ex) {
                Logger.getLogger(Database.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Deleted: " + spanish); // for testing
        } else {
            System.out.println("Doesn't contain word: " + spanish); // for testing
        }
        
        return deleted;
    }

    /*
    * Returns if a word exists in the database
    */
    public boolean containsWord(String spanish) {
        boolean isPresent = false;
        try {
            Statement statement = conn.createStatement();

            ResultSet rs = statement.executeQuery("SELECT wordId FROM WordData WHERE spanish = '" + spanish + "'");

            if (rs.next()) {
                isPresent = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return isPresent;
    }
    
    /*
    * Injects empty database with word data from words.txt file.
    */
    private void inject() {
        try {

            // Passing the path to the file as a parameter
            FileReader fr = new FileReader("words.txt");

            // Declaring loop variable
            int i;

            char temp;
            String spanish = "";
            String english = "";
            String line = "";

            // Holds true till there is nothing to read
            while ((i = fr.read()) != -1) // Print all the content of a file
            {
                temp = (char) i;

                switch (temp) {
                    case (':'):
                        spanish = line;
                        line = "";
                        break;
                    case ('\n'):
                        english = line;
                        line = "";
                        insertWord(new Word(spanish, english));
                        break;
                    default:
                        line += temp;
                        break;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("Error reading from file.");
        }
    }
}
