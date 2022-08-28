/*
 * A flashcard game class which is ran by GameMenu class
 */
package pdc_project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author carls
 */
public class FlashCardGame implements CardGame { //TODO: Make this class

    private GameConfig config;
    private UserData data;
    private Queue<Card> cards;
    private ArrayList<Word> wordsArray;
    private int finalScore;

    public FlashCardGame(GameConfig config, UserData data, ArrayList<Word> wordsArray) {
        this.config = config;
        this.data = data;
        this.cards = new LinkedList<Card>();
        this.finalScore = 0;
        this.wordsArray = wordsArray;
    }

    /*
    *  Gets random words from the database (ensuring no duplicates)
    */
    private void generateCards() {
        Word newWord = null;
        HashSet<Word> generatedWords = new HashSet<Word>(); // set of already-generated words (to avoid duplicates)
        Random rand = new Random();
        int index = 0;

        for (int i = 0; i < config.getNumCards(); i++) {
            index = rand.nextInt(wordsArray.size());
            newWord = wordsArray.get(index);
            if (generatedWords.contains(newWord)) {
                i--;
            } else {
                generatedWords.add(newWord);
                cards.add(new Card(config.getLang(), newWord));
            }
        }
    }

    @Override
    public UserData start() throws InterruptedException {
        // init variables...
        generateCards();
        int prevScore = 0;
        int cardsRemaining = config.getNumCards();
        final float score;
        List<Word> wrongWords = new ArrayList<Word>();

        System.out.println("Game starting!");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("...\n");

        // start game
        for (Card c : cards) {
            TimeUnit.SECONDS.sleep(2);
            c.printQuestion();
            TimeUnit.SECONDS.sleep(1);
            // print card number for user
            System.out.print("\t\tCard " + (config.getNumCards() - cardsRemaining + 1) + "/" + config.getNumCards() + "\n");
            TimeUnit.SECONDS.sleep(1);

            // starting timer
            if (config.getTimed().equals("yes")) { // if user selected time-attack...
                Timer timer = new Timer(false);

                CountDown countDown = new CountDown(); // creates our coutdown timer class

                timer.scheduleAtFixedRate(countDown, 1, 1000);
                String answer = InputGetter.getAlphanumeric("Your answer:");
                if (countDown.outOfTime) {
                    System.out.println("The correct answer was: " + c.getAnswer());
                } else {
                    finalScore += c.checkAnswer(answer);
                }
                
                if (finalScore <= prevScore) {
                    wrongWords.add(c.getWord());
                }

                timer.cancel();
                timer.purge();
            } else { // if user didn't select time-attack...
                finalScore += c.checkAnswer(InputGetter.getAlphanumeric("Your answer:"));

                // if incorrect, add incorrect word to users array of incorrect words (for revision)
                if (finalScore <= prevScore && !data.getIncorrectWords().contains(c.getWord())) {
                    data.getIncorrectWords().add(c.getWord());
                }
            }

            TimeUnit.SECONDS.sleep(1);
            prevScore = finalScore;
            cardsRemaining--;
        }

        // game ended ... save user data and print results
        if (config.getTimed().equals("no")) { // if user didn't select time-attack... (saves results)
            score = ((float) finalScore / (float) config.getNumCards()) * 100;
            data.addToCorrectPercent(score);
            data.incrementGamesPlayed();
            System.out.format("\nYou got: %.2f%%\nGo to the \'revision\' section to revise what you got wrong!\n", score);
        } else { // else don't save results but print out all thier incorrect words instead
            score = ((float) finalScore / (float) config.getNumCards()) * 100;
            System.out.format("\nYou got: %.2f%%\nWords incorrect:\n", score);
            for (Word word : wrongWords) {
                System.out.println(word);
            }
            System.out.print("\n");
        }
        return data;
    }
}
