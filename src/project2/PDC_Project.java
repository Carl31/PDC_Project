/*
 * Driver class for project.
 */
package project2;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author carls
 */
public class PDC_Project {

    public static void main(String[] args) {
//        // initialising program word data (esp & eng) ...
//        Map<String, String> words = new HashMap<String, String>();
//        ReaderWriter wfrw = new WordsFileReaderWriter(new File("words.txt"));
//        words = wfrw.readFrom();
//
//        // initialising program user data ...
//        Map<String, UserData> userData = new HashMap<String, UserData>();
//        ReaderWriter udrw = new UserDataReaderWriter(new File("userStats.txt"));
//        userData = udrw.readFrom();
//
//        // get player username
//        String userName = InputGetter.getAlphanumeric("Username: ").toUpperCase();
//        
//        System.out.println("\n ---------- Welcome, " + userName + ", to \'Verb Cards\' Spanish Language Program and Learning Resource ---------- \n");
//
//        while (true) { // keep playung until user enters "quit"
//            int input = 0;
//            printMenu();
//            while (input < 1 || input > 3) {
//                input = InputGetter.getPosInt("Enter \'1\', \'2\' or \'3\': ", 3);
//            }
//
//            switch (input) {
//                case (1):
//                    Menu game = new GameMenu(udrw, userData, userName, words);
//                    game.run();
//                    break;
//                case (2):
//                    Menu db = new DatabaseMenu(wfrw, words);
//                    db.run();
//                    break;
//                case (3):
//                    Menu stats = new StatsMenu(udrw, userName);
//                    stats.run();
//                    break;
//            }
//            System.out.println("\t\tMAIN MENU:");
//        }
            Database db = new Database();
            db.dbsetup();
            db.getUser("John");
            db.insertTestData();
    }

    private static void printMenu() {
        System.out.println("Note: To quit at any time, enter \"quit\".");
        System.out.println("\nWhat would you like to do?");
        System.out.println("\t 1 - Play Verb Cards: Test your vocabulary!");
        System.out.println("\t 2 - Browse Database: Find translations, add and remove words!");
        System.out.println("\t 3 - Stats View: View your game statistics!");
    }

}
