/*
 * The menu for the words database
 */
package pdc_project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author carls
 */
public class DatabaseMenu extends Menu {

    private final ReaderWriter wfrw;
    private Map<String, String> words;

    public DatabaseMenu(ReaderWriter wfrw, Map<String, String> words) {
        this.wfrw = wfrw;
        this.words = words;
    }

    @Override
    public void run() {
        // initialising variables
        int input = 0;

        System.out.println("\nWelcome to our word database!");

        // keeps looping until  user goes 'back' (presses 4).
        while (input != 4) {
            System.out.println("\n\t\tDATABASE MENU:");
            System.out.println("\nWhat would you like to do?");
            System.out.println("\t 1 - View Database: View all words!");
            System.out.println("\t 2 - Word Search: Search for a translation!");
            System.out.println("\t 3 - Edit Database: Create new words or delete existing words!");
            System.out.println("\t 4 - Go Back: Back to main menu.");

            while (input < 1 || input > 4) {
                input = InputGetter.getPosInt("Enter \'1\', \'2\', \'3\' or \'4\': ",4);
            }

            switch (input) {
                case (1):
                    printAlphabetically(words);
                    break;
                case (2):
                    String prefix = InputGetter.getAlphanumeric("Enter prefix or word (add \"to \" infront of english words):");
                    printAlphabetically(searchWords(prefix));
                    break;
                case (3):
                    int option = 0;
                    System.out.println("\t\t 1 - Create a word");
                    System.out.println("\t\t 2 - Delete a word");
                    System.out.println("\t\t 3 - Back");
                    option = InputGetter.getPosInt("Enter \'1\', \'2\' or  \'3\': ", 3);

                    switch (option) {
                        case (1):
                            createWord();
                            break;
                        case (2):
                            deleteWord(InputGetter.getAlphanumeric("Enter spanish word to delete: "));
                            break;
                        case (3):
                            break;
                    }
                    break;
                default:
                    break;
            }
            wfrw.writeTo(words);
            if (input != 4) {
                input = 0;
            }
        }
    }

    /*
    * Allows user to add a word to the database  
    */
    private void createWord() {
        System.out.println("Please enter both languages of new word");
        String spanishWord = InputGetter.getAlphanumeric("\tSpanish: ");

        if (words.containsKey(spanishWord)) {
            System.out.println("Database already contains " + spanishWord + "(" + words.get(spanishWord) + ")!");
        } else {
            words.put(spanishWord, InputGetter.getAlphanumeric("\tEnglish: "));
            System.out.println("New word added -> " + spanishWord + " : " + words.get(spanishWord));
        }

    }

    /*
    *   Allows user to delete a word from the database
    */
    private void deleteWord(String toDelete) {
        if (words.containsKey(toDelete)) {
            words.remove(toDelete);
            System.out.println("\"" + toDelete + "\" has been deleted.");
        }
        else {
            System.out.println(toDelete+" doesn't exist in the database!");
        }

    }

    /*
    *  Returns a map of words based on the input prefix  
    */
    public HashMap<String, String> searchWords(String prefix) {
        HashMap query = new HashMap<String, String>();

        // adds all spanish words starting with prefix
        for (Map.Entry<String, String> word : words.entrySet()) {
            if (word.getKey().startsWith(prefix)) {
                query.put(word.getKey(), word.getValue());
            }
        }

        // adds all english words starting with prefix (if they're not already added)
        for (Map.Entry<String, String> word : words.entrySet()) {
            if (word.getValue().startsWith(prefix) && !words.containsValue(word)) {
                query.put(word.getKey(), word.getValue());
            }
        }

        if (query.isEmpty()) {
            query.put("No results for", prefix);
        }
        return query;
    }

    /*
    *  Private helper method to get a key (spanish word) from a value (english word) in a map of string-string word translations
    */
    private static String getKeyFromValue(Map<String, String> map, String value, ArrayList<String> printedWords) {
        int frequency = Collections.frequency(printedWords, value);
        int count = 0;
        
        // this is to ensure that all words are printed even if a spanish word may have multiple english tranlsations
        for (Entry<String, String> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                if (count == frequency) {
                    return entry.getKey();
                }
                else {
                    count++;
                } 
            }
        }
        return null;
    }

    /*
    *  Print all words alphabetically and neatly - dependant on what language the user chooses
    */
    private void printAlphabetically(Map<String, String> words) {
        char letter = '@';
        List<String> alphabeticWords = new ArrayList<String>();
        String lang = InputGetter.getLanguage("Output alphabetically in which language? (\"english\" or \"spanish\"): ");

        // add all spanish/english words to temporary list
        for (Map.Entry<String, String> word : words.entrySet()) {
            if (lang.equals("spanish")) {
                alphabeticWords.add(word.getKey());
            } else {
                alphabeticWords.add(word.getValue());
            }
        }
        Collections.sort(alphabeticWords); // alphabetise temporary list

        // print list in a nice format with each new start letter in the list being printed too
        ArrayList<String> printedWords = new ArrayList<String>();
        for (String word : alphabeticWords) {
            if (lang.equals("spanish")) {
                if (word.charAt(0) != letter) {
                    System.out.println("\t" + ((char) (word.charAt(0) - 32)));
                    letter = word.charAt(0);
                }
                System.out.println("\t\t" + word + " : " + words.get(word));
            } else {
                if (word.charAt(3) != letter) { // charAt(3) to account for "to " in english translations
                    System.out.println("\t" + ((char) (word.charAt(3) - 32)));
                    letter = word.charAt(3);
                }
                System.out.println("\t\t" + word + " : " + getKeyFromValue(words, word, printedWords));
                printedWords.add(word);
            }
        }
    }
}
