/*
 * Holding data to do with MVC function.
 */
package project2;

import java.util.ArrayList;

/**
 *
 * @author carls
 */
public class ModelData {
    private UserData user;
    private String username;
    private boolean isLoggedIn;
    private boolean isPlaying; // when user is in play mode
    private boolean canStart; // when all game config options are entered as valid
    private ArrayList<Word> words; // all word data from db
    private boolean gameEnded; // if game is over
    private String message; // message to be printed to screen
    protected String cardsRemaining;
    protected String userAnswer;
    protected Card currentCard;
    protected boolean isWaiting; // if waiting for user to enter their answer
    protected boolean configEnabled; // controls which buttons/components should be set to disabled/enabled
    protected boolean displayCard; // if view needs to show new card
    protected boolean hasAnswered; // if user has given their answer
    protected boolean displayWarning; // for displaying a message before a game has started
    protected boolean isInDb; // if user is in database menu
    protected boolean listUpdated; // if list of words needs refreshing
    protected boolean wordAdded; // check is new word was added to db successfully
    protected boolean wordRemoved; // check if selected word was removed from db successfully
    protected boolean isInStats; // to check if user is in statistics menu
    
    public ModelData() {
        this.user = null;
        this.username = null;
        this.isLoggedIn = false;
        this.words = null;
        this.isPlaying = false;
        this.canStart = false;
        this.gameEnded = false;
        this.message = "";
        this.cardsRemaining = "";
        this.userAnswer = "";
        this.currentCard = null;
        this.isWaiting = false;
        this.configEnabled = true;
        this.displayCard = true;
        this.hasAnswered = false;
        this.displayWarning = false;
        this.isInDb = false;
        this.listUpdated = false;
        this.wordAdded = false;
        this.wordRemoved = false;
        this.isInStats = false;
    }
    
    public ModelData(String username, UserData user) {
        this();
        this.user = user;
        this.username = username;
        this.isLoggedIn = true;
    }
    
    /*
    * Upon logout
    */
    public void logOut() {
        this.setIsLoggedIn(false);
        this.setUser(null);
        this.setUsername("");
    }

    /**
     * @return the user
     */
    public UserData getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserData user) {
        this.user = user;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the isLoggedIn
     */
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    /**
     * @param isLoggedIn the isLoggedIn to set
     */
    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    /**
     * @return the isPlaying
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * @param isPlaying the isPlaying to set
     */
    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    /**
     * @return the canStart
     */
    public boolean isCanStart() {
        return canStart;
    }

    /**
     * @param canStart the canStart to set
     */
    public void setCanStart(boolean canStart) {
        this.canStart = canStart;
    }

    /**
     * @return the gameEnded
     */
    public boolean isGameEnded() {
        return gameEnded;
    }

    /**
     * @param gameEnded the gameEnded to set
     */
    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the words
     */
    public ArrayList<Word> getWords() {
        return words;
    }

    /**
     * @param words the words to set
     */
    public void setWords(ArrayList<Word> words) {
        this.words = words;
    } 
}
