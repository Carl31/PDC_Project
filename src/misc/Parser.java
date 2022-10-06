package misc;

/*
 * Used for parsing word data (words.txt) into Derby DB (note: drop all tables before running)
 * Only needs to be ran once on every new device attempting to execute PDC_Project.java.
 *
 * @see PDC_Project.java
 */
import java.io.*;
import java.util.Stack;
import project2.Database;
import project2.Word;

public class Parser {
    private Database db = new Database();

    public Parser() {
        db.dbsetup();
        
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
                        saveToDb(new Word(spanish, english));
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
        db.closeConnections();
    }
    
    private void saveToDb(Word word) {
        db.insertWord(word);
    }

    public static void main(String[] args) {
        Parser p = new Parser();
    }
}
