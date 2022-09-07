/*
 * Object to hold data for each user
 */
package project2;

import java.util.HashSet;

/**
 *
 * @author carls
 */
public class UserData {
    private int gamesPlayed;
    private float correctPercent;
    private HashSet<Word> incorrectWords;
    
    public UserData (int gamesPlayed, float correctPercent, HashSet<Word> incorrectWords) {
        this.gamesPlayed = gamesPlayed;
        this.correctPercent = correctPercent;
        this.incorrectWords = incorrectWords;
    }
    
    /*
    * Increment games played
    */
    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }
    
    /*
    * Add 'correct' percentage from last game to their current avergae correct percentage
    */
    public void addToCorrectPercent(float perc) {
        this.correctPercent = ((((this.correctPercent / 100.0f) * this.gamesPlayed) + (perc / 100.0f)) / (float)(this.gamesPlayed + 1)) * 100;
    }

    /**
     * @return the gamesPlayed
     */
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * @return the correctPercent
     */
    public float getCorrectPercent() {
        return correctPercent;
    }

    /**
     * @return the incorrectWords
     */
    public HashSet<Word> getIncorrectWords() {
        return incorrectWords;
    }
    
    @Override
    public String toString() {
        String output = "";
        output += this.gamesPlayed+":"+this.correctPercent+":";
        for (Word temp : this.incorrectWords) {
            output += temp+", ";
        }
        return output;
    }
}
