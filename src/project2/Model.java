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
    protected Database db;
    private GameConfig configData;
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

    public void generateCards() {
        getCards().clear();
        Word newWord = null;
        HashSet<Word> generatedWords = new HashSet<Word>(); // set of already-generated words (to avoid duplicates)
        Random rand = new Random();
        int index = 0;

        for (int i = 0; i < getConfigData().getNumCards(); i++) {
            index = rand.nextInt(getWordData().size());
            newWord = getWordData().get(index);
            if (generatedWords.contains(newWord)) {
                i--;
            } else {
                generatedWords.add(newWord);
                getCards().add(new Card(getConfigData().getLang(), newWord));
            }
        }
    }

    public void startFlashCardGame() throws InterruptedException {
        // init variables...
        generateCards();
        finalScore = 0;
        int prevScore = 0;
        int numCards = getConfigData().getNumCards();
        final float score;
        data.configEnabled = false;
        data.displayCard = true;
        data.setGameEnded(false);
        data.isWaiting = true;
        //data.cardsRemaining = (configData.getNumCards() + " / " + configData.getNumCards());
        notifyView();
        data.configEnabled = true;

        // start game
        for (Card c : getCards()) {
            data.displayCard = true;
            data.hasAnswered = false;
            // update question
            data.currentCard = c;
            // print card number for user // update cardCountLabel
            data.cardsRemaining = ((getConfigData().getNumCards() - numCards + 1) + " / " + getConfigData().getNumCards());
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
        score = ((float) finalScore / (float) getConfigData().getNumCards()) * 100;
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
        data.cardsRemaining = (getConfigData().getNumCards() + " / " + getConfigData().getNumCards());
        notifyView();
        data.configEnabled = true;

        data.message = "Revision starting!";

        // start game
        while (!cards.isEmpty() && data.isPlaying()) {
            data.displayCard = true;
            data.hasAnswered = false;

            // update question
            data.currentCard = getCards().peek();
            // print card number for user // update cardCountLabel
            data.cardsRemaining = ("\t\tCards still in your revision pile: " + getCards().size() + "\n");
            notifyView();

            data.hasAnswered = true;
            data.displayCard = false;

            finalScore += getCards().peek().checkAnswer(data.userAnswer);

            if (data.isGameEnded()) {
                return;
            }

            // if incorrect, add card to back of queue
            if (finalScore <= prevScore) {
                getCards().add(getCards().remove());
                data.message = "Wrong! The correct answer is\n\t " + data.currentCard.getAnswer().toUpperCase();
            } else { // if correct, remove card from queue
                getCards().remove();
                total++;
                data.message = "Correct!";
            }
            notifyView();
            prevScore = finalScore;

        }

        // game ended ... save user data and print results
        data.getUser().getIncorrectWords().clear();
        for (Card tempCard : getCards()) {
            data.getUser().getIncorrectWords().add(tempCard.getWord());
        }
        data.displayCard = false;
        data.message = "Revision finished. Cards revised: " + total;
        db.UpdateUserData(data.getUsername(), data.getUser(), data.getUser().getIncorrectWords());
        notifyView();
    }

    private void generateUserCards() {
        getCards().clear();
        for (Word temp : data.getUser().getIncorrectWords()) {
            getCards().add(new Card(getConfigData().getLang(), temp));
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

    /**
     * @return the configData
     */
    public GameConfig getConfigData() {
        return configData;
    }

    /**
     * @param configData the configData to set
     */
    public void setConfigData(GameConfig configData) {
        this.configData = configData;
    }

    /**
     * @return the wordData
     */
    public ArrayList<Word> getWordData() {
        return wordData;
    }

    /**
     * @param wordData the wordData to set
     */
    public void setWordData(ArrayList<Word> wordData) {
        this.wordData = wordData;
    }

    /**
     * @return the cards
     */
    public Queue<Card> getCards() {
        return cards;
    }

    /**
     * @param cards the cards to set
     */
    public void setCards(Queue<Card> cards) {
        this.cards = cards;
    }
    
    

}
