/*
 * A menu for the FlashCardGame class
 */
package project2;

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
public class GameMenu extends Menu { // DB line 67

    protected final ModelData data;
    protected final ArrayList<Word> wordsArray;

    public GameMenu(ModelData data) {
        this.data = data;
        this.wordsArray = data.words;
    }

    @Override
    public void run() {
        // initialising variables
        String playAgain = "yes";
        int input = 0;
        HashSet<Word> incorrectWords = new HashSet<Word>();

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

            switch (input) {
                case (1):
                    GameConfig config = new GameConfig();
                    config.getFlashConfig();
                    if (playAgain.equals("no")) {
                        playAgain = "yes";
                    }

                    while (playAgain.equals("yes")) { // for easy replayability 
                        incorrectWords.clear();

                        FlashCardGame game = new FlashCardGame(config, data.getUser(), wordsArray);

                        try {
                            UserData updatedData = game.start();
                            // userData.put(username, updatedData); // DB -- replace current users data with currentData
                        } catch (InterruptedException ex) {
                        }

                        playAgain = InputGetter.getYesOrNo("Would you like to play again? (\"yes\" or \"no\")");
                    }
                    break;
                case (2):

                    incorrectWords.clear();

                    if (data.getUser().getIncorrectWords().isEmpty()) { // checks if user has incorrect words to revise
                        System.out.println("Unable to revise: Revision pile empty!!");
                    } else {
                        System.out.println("Rules: Revision stops when you have correctly answered ALL your cards, or you press \'s\' to save progress.");

                        GameConfig reviseConfig = new GameConfig();
                        reviseConfig.getRevisionConfig();

                        RevisionFlashCardGame game = new RevisionFlashCardGame(reviseConfig, data.getUser());

                        try {
                            UserData updatedData = game.start();
                            // userData.put(username, updatedData); // DB -- replace current users data with currentData
                        } catch (InterruptedException ex) {
                        }
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
