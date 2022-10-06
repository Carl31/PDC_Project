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
    private boolean isPlaying;
    private boolean canStart;
    protected ArrayList<Word> words;
    private boolean gameEnded;
    protected String message;
    protected String cardsRemaining;
    protected String userAnswer;
    protected String cardWord;
    
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
        this.cardWord = "";
    }
    
    public ModelData(String username, UserData user) {
        this();
        this.user = user;
        this.username = username;
        this.isLoggedIn = true;
    }
    
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
    
    
    
    
    
    
}
