/*
 * A class to hold game configuration data for replayability
 */
package project2;

/**
 *
 * @author carls
 */
public class GameConfig {
    private int numCards;
    private String lang;
    private String timed;
    
    public GameConfig() {};
    
    /*
    *  Used to instantiate a flash card game
    */
    public void getFlashConfig() {
        numCards = InputGetter.getPosInt("Number of flash cards? (max 50)", 50);
        lang = InputGetter.getLanguage("Flashcards language? (\"english\" or \"spanish\" or \"random\"): ");
        timed = InputGetter.getYesOrNo("Time attack? (\"yes\" or \"no\") - Note - time attack does not save user points or data.");
    }
    
    /*
    *  Used for getting game config for a revision flash card game (no number of cards or time attack)
    */
    public void getRevisionConfig() {
        lang = InputGetter.getLanguage("Flashcards language? (\"english\" or \"spanish\" or \"random\"): ");
    }

    /**
     * @return the numCards
     */
    public int getNumCards() {
        return numCards;
    }

    /**
     * @return the lang
     */
    public String getLang() {
        return lang;
    }

    /**
     * @return the timed
     */
    public String getTimed() {
        return timed;
    }
    
    /*
    *  Sets the number of cards in a game
    */
    public void setNumCards(int numCards) {
        this.numCards = numCards;
    }
}
