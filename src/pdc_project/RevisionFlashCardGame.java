/*
 * A revision flashcard game class which is ran by GameManu class
 */
package pdc_project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author carls
 */
public class RevisionFlashCardGame implements CardGame {

    private GameConfig config;
    private UserData data;
    private Queue<Card> cards;
    private ArrayList<Word> wordsArray;
    private int finalScore;

    public RevisionFlashCardGame(GameConfig config, UserData data, HashSet<Word> words) {
        this.data = data;
        this.cards = new LinkedList<Card>();
        this.finalScore = 0;
        this.config = config;

        // converts set to list of words
        this.wordsArray = new ArrayList<Word>();
        for (Word temp : words) {
            wordsArray.add(temp);
        }
    }

    /*
    * Generates cards (from users' incorrectCards data
    */
    private void generateCards() {
        for (Word temp : wordsArray) {
            cards.add(new Card(config.getLang(), temp));
        }
    }

    @Override
    public UserData start() throws InterruptedException {
        generateCards();
        int prevScore = 0;
        final float score;
        int total = 0;

        System.out.println("Revision starting!");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("...\n");
        TimeUnit.SECONDS.sleep(2);
        String input = "";

        // start game
        while (!cards.isEmpty() && !input.equals("s")) {
            cards.peek().printQuestion();
            System.out.print("\t\tCards still in your revision pile: " + cards.size()+"\n");
            TimeUnit.SECONDS.sleep(1);

            input = InputGetter.getAlphanumeric("Your answer:");

            if (!input.equals("s")) { // user enters "s" to quit and save progress
                finalScore += cards.peek().checkAnswer(input);

                // if incorrect, add card to back of queue
                if (finalScore <= prevScore) {
                    cards.add(cards.remove());
                } else { // remove card from queue
                    cards.remove();
                    total++;
                }

                TimeUnit.SECONDS.sleep(1);
                prevScore = finalScore;
            }
        }

        // game ended ... save user data and print results
        data.getIncorrectWords().clear();
        for (Card tempCard : cards) {
            data.getIncorrectWords().add(tempCard.getWord());
        }
        
        if (input.equals("s")) System.out.println("Saving progress...");
        TimeUnit.SECONDS.sleep(1);
        
        System.out.format("Revision Ended! You completed: "+total+" words!\n");
        return data;
    }
}
