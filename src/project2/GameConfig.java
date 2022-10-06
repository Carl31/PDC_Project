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
    private boolean isRevision;
    
    public GameConfig(int numCards, String lang, boolean isRevision) {
        this.numCards = numCards;
        this.lang = lang;
        this.isRevision = isRevision;
    }

    /**
     * @return the numCards
     */
    public int getNumCards() {
        return numCards;
    }

    /**
     * @param numCards the numCards to set
     */
    public void setNumCards(int numCards) {
        this.numCards = numCards;
    }

    /**
     * @return the lang
     */
    public String getLang() {
        return lang;
    }

    /**
     * @param lang the lang to set
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * @return the isRevision
     */
    public boolean isRevision() {
        return isRevision;
    }

    /**
     * @param isRevision the isRevision to set
     */
    public void setIsRevision(boolean isRevision) {
        this.isRevision = isRevision;
    }
    
    
    
    
    
    
    
}
