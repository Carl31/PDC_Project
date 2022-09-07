/*
 * An interface for a FlashCard Game
 */
package pdc_project;

/**
 *
 * @author carls
 */
public interface CardGame {
    
    /*
    *   Starts the game and returns the users newly obtained data upon finishing.
    */
    public UserData start() throws InterruptedException;
     
}
