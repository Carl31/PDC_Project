/*
 * Class for a single word (eng and esp)
 */
package pdc_project;

/**
 *
 * @author carls
 */
public class Word {
    private final String english;
    private final String spanish;
    
    public Word(String spanish, String english) {
        this.english = english;
        this.spanish = spanish;
    }

    /**
     * @return the english word
     */
    public String getEnglish() {
        return english;
    }

    /**
     * @return the spanish word
     */
    public String getSpanish() {
        return spanish;
    }
    
    @Override
    public String toString() {
        return (this.spanish+"/"+this.english);
    }
}
