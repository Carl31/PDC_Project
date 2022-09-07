/*
 * A menu for the FlashCardGame class
 */
package pdc_project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carls
 */
public class GameMenu extends Menu {

    private final ReaderWriter udrw;
    private final Map<String, UserData> userData;
    private final String username;
    private final ArrayList<Word> wordsArray;

    public GameMenu(ReaderWriter udrw, Map<String, UserData> userData, String username, Map<String, String> words) {
        this.udrw = udrw;
        this.userData = userData;
        this.username = username;

        // convert words hashmap into an array of words
        this.wordsArray = new ArrayList<Word>();
        for (Map.Entry<String, String> temp : words.entrySet()) {
            wordsArray.add(new Word(temp.getKey(), temp.getValue()));
        }
    }

    @Override
    public void run() {
        // initialising variables
        String playAgain = "yes";
        int input = 0;
        HashSet<Word> incorrectWords = new HashSet<Word>();
        UserData currentData = new UserData(0, 0, incorrectWords);

        while (input != 3) { // loops while player doesn't go back to main menu (press 3)
            System.out.println("\n\t\tGAME MENU:");
            System.out.println("\nWhat would you like to do?");
            System.out.println("\t 1 - Play: Play a round of Verb Cards!");
            System.out.println("\t 2 - Revise: Test yourself with the words you've previsouly gotten wrong!");
            System.out.println("\t 3 - Go Back: Back to main menu.");

            while (input < 1 || input > 3) {
                input = InputGetter.getPosInt("Enter \'1\', \'2\' or \'3\': ", 3);
            }

            // clears cached words from previus game
            incorrectWords.clear();
            currentData = new UserData(0, 0, incorrectWords);

            switch (input) {
                case (1):
                    GameConfig config = new GameConfig();
                    config.getFlashConfig();
                    if (playAgain.equals("no")) {
                        playAgain = "yes";
                    }

                    while (playAgain.equals("yes")) { // for easy replayability 
                        incorrectWords.clear();

                        // get user data from user database
                        if (userData.containsKey(username)) {
                            currentData = userData.get(username);
                        } else {
                            userData.put(username, currentData);
                        }

                        FlashCardGame game = new FlashCardGame(config, currentData, wordsArray);

                        try {
                            currentData = game.start();
                        } catch (InterruptedException ex) {
                        }

                        userData.put(username, currentData);
                        udrw.writeTo(userData);

                        playAgain = InputGetter.getYesOrNo("Would you like to play again? (\"yes\" or \"no\")");
                    }
                    break;
                case (2):

                    incorrectWords.clear();

                    if (userData.containsKey(username)) {
                        currentData = userData.get(username);
                    } else {
                        userData.put(username, currentData);
                    }

                    if (currentData.getIncorrectWords().isEmpty()) { // checks if user has incorrect words to revise
                        System.out.println("Unable to revise: Revision pile empty!!");
                    } else {
                        System.out.println("Rules: Revision stops when you have correctly answered ALL your cards, or you press \'s\' to save progress.");

                        GameConfig reviseConfig = new GameConfig();
                        reviseConfig.getRevisionConfig();

                        RevisionFlashCardGame game = new RevisionFlashCardGame(reviseConfig, currentData, currentData.getIncorrectWords());

                        try {
                            currentData = game.start();
                        } catch (InterruptedException ex) {
                        }

                        userData.put(username, currentData);
                        udrw.writeTo(userData);
                    }
                    break;
                case (3):
                    break;
                default:
                    break;
            }
            if (input != 3) {
                input = 0;
            }
        }
    }
}
