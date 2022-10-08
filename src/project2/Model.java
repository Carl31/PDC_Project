/*
 * The Model class for MVC
 */
package project2;

import java.util.ArrayList;
import java.util.Comparator;
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
        this.data.isInDb = false;
        this.data.isInStats = false;
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

        if (!configData.isRevision()) {
            try {
                startFlashCardGame();
            } catch (InterruptedException ex) {
            }
        } else {

            if (data.getUser().getIncorrectWords().isEmpty()) { // checks if user has incorrect words to revise
                data.message = "Unable to revise: Revision pile empty!!";
                data.displayWarning = true;
                notifyView();
                data.displayWarning = false;
                data.setIsPlaying(false);
            } else {
                data.message = "Rules: Revision stops when you have correctly answered ALL your cards.\nRevision doesn't affect user scores.";
                data.displayWarning = true;
                notifyView();
                data.displayWarning = false;
                try {
                    startRevisionFlashCardGame();
                } catch (InterruptedException ex) {
                }
            }
        }

        data.setGameEnded(true);
        notifyView();
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
        finalScore = 0;
        int prevScore = 0;
        int numCards = configData.getNumCards();
        final float score;
        data.configEnabled = false;
        data.displayCard = true;
        data.setGameEnded(false);
        data.isWaiting = true;
        //data.cardsRemaining = (configData.getNumCards() + " / " + configData.getNumCards());
        notifyView();
        data.configEnabled = true;

        // start game
        for (Card c : cards) {
            data.displayCard = true;
            data.hasAnswered = false;
            // update question
            data.currentCard = c;
            // print card number for user // update cardCountLabel
            data.cardsRemaining = ((configData.getNumCards() - numCards + 1) + " / " + configData.getNumCards());
            notifyView();

            data.hasAnswered = true;
            data.displayCard = false;

            finalScore += c.checkAnswer(data.userAnswer);

            if (!data.isPlaying()) {
                return;
            }

            // if incorrect, add incorrect word to users array of incorrect words (for revision)
            if (finalScore <= prevScore) {
                if (!data.getUser().getIncorrectWords().contains(c.getWord())) {
                    data.getUser().getIncorrectWords().add(c.getWord());
                }
                data.message = "Wrong! The correct answer is\n\t " + data.currentCard.getAnswer().toUpperCase();
            } else {
                data.message = "Correct!";
            }

            notifyView();
            prevScore = finalScore;
            numCards--;
        }

        data.displayCard = false;
        if (!data.isPlaying()) {
            return;
        }
        // game ended ... save user data and print results
        score = ((float) finalScore / (float) configData.getNumCards()) * 100;
        data.getUser().addToCorrectPercent(score);
        data.getUser().incrementGamesPlayed();
        data.message = String.format("\nYou got: %.2f%%\nGo to the \'revision\' section to revise what you got wrong!", score);

        db.UpdateUserData(data.getUsername(), data.getUser(), data.getUser().getIncorrectWords());
        notifyView();
    }

    public void startRevisionFlashCardGame() throws InterruptedException {
        generateUserCards();
        finalScore = 0;
        int prevScore = 0;
        final float score;
        int total = 0;

        data.configEnabled = false;
        data.displayCard = true;
        data.setGameEnded(false);
        data.setIsPlaying(true);
        data.isWaiting = true;
        data.cardsRemaining = (configData.getNumCards() + " / " + configData.getNumCards());
        notifyView();
        data.configEnabled = true;

        data.message = "Revision starting!";

        // start game
        while (!cards.isEmpty() && data.isPlaying()) {
            data.displayCard = true;
            data.hasAnswered = false;

            // update question
            data.currentCard = cards.peek();
            // print card number for user // update cardCountLabel
            data.cardsRemaining = ("\t\tCards still in your revision pile: " + cards.size() + "\n");
            notifyView();

            data.hasAnswered = true;
            data.displayCard = false;

            finalScore += cards.peek().checkAnswer(data.userAnswer);

            if (data.isGameEnded()) {
                return;
            }

            // if incorrect, add card to back of queue
            if (finalScore <= prevScore) {
                cards.add(cards.remove());
                data.message = "Wrong! The correct answer is\n\t " + data.currentCard.getAnswer().toUpperCase();
            } else { // if correct, remove card from queue
                cards.remove();
                total++;
                data.message = "Correct!";
            }
            notifyView();
            prevScore = finalScore;

        }

        // game ended ... save user data and print results
        data.getUser().getIncorrectWords().clear();
        for (Card tempCard : cards) {
            data.getUser().getIncorrectWords().add(tempCard.getWord());
        }
        data.displayCard = false;
        data.message = "Revision finished. Cards revised: " + total;
        db.UpdateUserData(data.getUsername(), data.getUser(), data.getUser().getIncorrectWords());
        notifyView();
    }

    private void generateUserCards() {
        cards.clear();
        for (Word temp : data.getUser().getIncorrectWords()) {
            cards.add(new Card(configData.getLang(), temp));
        }
    }

    protected void getDbWords(boolean sortInSpanish, boolean retrieveFromDb) {
        ArrayList<Word> tempWords = data.words;
        if (retrieveFromDb) tempWords = db.getWordsAsArray();

        
        if (sortInSpanish) {
            tempWords.sort(new Comparator<Word>() {
            @Override
            public int compare(Word w1, Word w2) {
                return (w1.getSpanish().compareTo(w2.getSpanish()));
            }
        });
        } else {
            tempWords.sort(new Comparator<Word>() {
            @Override
            public int compare(Word w1, Word w2) {
                return (w1.getEnglish().compareTo(w2.getEnglish()));
            }
        });
        }
        this.data.words = tempWords;
    }
    
    protected void addWord(String eng, String esp) {
        data.wordAdded = false;
        if (!db.containsWord(esp)) {
            if (isAlphanumeric(eng) && isAlphanumeric(esp) && !esp.equals("") && !esp.equals("")) {
                db.insertWord(new Word(esp, "to "+eng));
                data.wordAdded = true;
                data.listUpdated = true;
            }
        }
    }
    
    protected void removeWord(String selected) {
        String[] esp = selected.split(" ", 5);
        data.listUpdated = false;
        data.wordRemoved = false;
        if (db.deleteWord(esp[4])) {
            data.wordRemoved = true;
            data.listUpdated = true;
        }
    }
    
    private boolean isAlphanumeric(String word) {
        return (word.matches("[a-zA-Z\u00f1]*"));
    }

}
