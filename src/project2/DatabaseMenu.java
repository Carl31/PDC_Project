/*
 * The menu for the words database
 */
package project2;

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
public class DatabaseMenu { // Line 78!!!!!!

    private ModelData data;
    private Map<String, String> words;

    public DatabaseMenu(ModelData data) {
        this.data = data;
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
