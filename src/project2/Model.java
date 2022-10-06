/*
 * The Model class for MVC
 */
package project2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Queue;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author carls
 */
public class Model extends Observable {

    public ModelData data;
    private DatabaseMenu dbMenu;
    private StatsMenu statsMenu;
    protected Database db;
    protected GameConfig configData;
    private ArrayList<Word> wordData;

    private Queue<Card> cards;
    private int finalScore;

    public Model() {
        // setup db, menus and mvc data
        db = new Database();
        db.dbsetup();
        wordData = db.getWordsAsArray();

        data = new ModelData();
        configData = null;
        dbMenu = new DatabaseMenu(data);
        statsMenu = new StatsMenu(data);
        cards = new LinkedList<Card>();
    }

//    public void startGameMenu() {
//        // ensures ModelData for gameMenu is updated
//        gameMenu.wordsArray = db.getWordsAsArray();
//        gameMenu.run();
//    }
//    
//    public void startDBMenu() {
//        // ensure ModelData for DBMenu is updated
//        dbMenu.run();
//    }
//    
//    public void startStatsMenu() {
//        // ensure ModelData for statsMenu is updated
//        statsMenu.run();
//    }
    public void logout() {
        this.data.setUsername("");
        this.data.setUser(null);
        this.data.setIsLoggedIn(false);
        this.data.setIsPlaying(false);
    }

    public void notifyView() {
        this.setChanged();
        notifyObservers(data);
    }

    public boolean verifyUsername(String username) {
        if (username == null || username.length() < 3) {
            return false;
        }

        return isAllowedName(username.toCharArray());
    }

    private boolean isAllowedName(char[] username) {
        boolean isValid = true;

        for (char c : username) {

            // if alphanumeric character
            if (!Character.isLetterOrDigit(c)) {
                isValid = false;
            }
        }

        return isValid;
    }

    protected void exitGame() {
        db.closeConnections();
        System.exit(0);
    }

    protected void startGame() {

        // initialising variables
        HashSet<Word> incorrectWords = new HashSet<Word>();

        while (data.isCanStart()) { // loops while player doesn't go back to main menu
            // clears cached words from previus game
            incorrectWords.clear();

            if (!configData.isRevision()) {
                try {
                    startFlashCardGame();
                } catch (InterruptedException ex) {
                }
            } else {

                incorrectWords.clear();

                if (data.getUser().getIncorrectWords().isEmpty()) { // checks if user has incorrect words to revise
                    data.message = "Unable to revise: Revision pile empty!!";
                    notifyView();
                } else {
                    data.message = "Rules: Revision stops when you have correctly answered ALL your cards.";

                    try {
                        startRevisionFlashCardGame();
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }

        // TODO: put updatedData into db
    }

    private void generateCards() {
        cards.clear();
        Word newWord = null;
        HashSet<Word> generatedWords = new HashSet<Word>(); // set of already-generated words (to avoid duplicates)
        Random rand = new Random();
        int index = 0;

        for (int i = 0; i < configData.getNumCards(); i++) {
            index = rand.nextInt(wordData.size());
            newWord = wordData.get(index);
            if (generatedWords.contains(newWord)) {
                i--;
            } else {
                generatedWords.add(newWord);
                cards.add(new Card(configData.getLang(), newWord));
            }
        }
    }

    public void startFlashCardGame() throws InterruptedException {
        // init variables...
        generateCards();
        int prevScore = 0;
        int cardsRemaining = configData.getNumCards();
        final float score;
        List<Word> wrongWords = new ArrayList<Word>();

        // start game
        for (Card c : cards) {
            // print question
            data.cardWord = c.getQuestion();
            // print card number for user // update cardCountLabel
            data.cardsRemaining = ((configData.getNumCards() - cardsRemaining + 1) + "/" + configData.getNumCards());
            notifyView();

            finalScore += c.checkAnswer(data.userAnswer);

            // if incorrect, add incorrect word to users array of incorrect words (for revision)
            if (finalScore <= prevScore && !data.getUser().getIncorrectWords().contains(c.getWord())) {
                data.getUser().getIncorrectWords().add(c.getWord());
            }

            prevScore = finalScore;
            cardsRemaining--;
        }

        // game ended ... save user data and print results
        score = ((float) finalScore / (float) configData.getNumCards()) * 100;
        data.getUser().addToCorrectPercent(score);
        data.getUser().incrementGamesPlayed();
        data.message = String.format("\nYou got: %.2f%%\nGo to the \'revision\' section to revise what you got wrong!", score);
        notifyView();
    }

    public void startRevisionFlashCardGame() throws InterruptedException {
        generateUserCards();
        int prevScore = 0;
        final float score;
        int total = 0;

        data.message = "Revision starting!";
        String input = "";

        // start game
        while (!cards.isEmpty()) {
            data.cardWord = cards.peek().getQuestion();
            data.cardsRemaining = ("\t\tCards still in your revision pile: " + cards.size() + "\n");

            input = data.userAnswer;

            finalScore += cards.peek().checkAnswer(input);

            // if incorrect, add card to back of queue
            if (finalScore <= prevScore) {
                cards.add(cards.remove());
            } else { // remove card from queue
                cards.remove();
                total++;
            }
            prevScore = finalScore;

        }

        // game ended ... save user data and print results
        data.getUser().getIncorrectWords().clear();
        for (Card tempCard : cards) {
            data.getUser().getIncorrectWords().add(tempCard.getWord());
        }
    }

    private void generateUserCards() {
        cards.clear();
        for (Word temp : data.getUser().getIncorrectWords()) {
            cards.add(new Card(configData.getLang(), temp));
        }
    }

}
