/*
 * A flash card object for a FlashCardGame.
 */
package project2;

/**
 *
 * @author carls
 */
public class Card {

    private Word word;
    private String question;
    private String answer;
    private String lang;

    public Card(String lang, Word word) {
        this.lang = lang;
        this.word = word;
        initWord();
    }

    /*
    *   returnd the Word of this flashcard
    */
    public Word getWord() {
        return word;
    }

    /*
    *   Sets the question and answer based off the language given
    */
    private void initWord() {
        switch (getLang()) {
            case ("Spanish"):
                answer = word.getSpanish();
                question = word.getEnglish();
                break;
            case ("English"):
                answer = word.getEnglish();
                question = word.getSpanish();
                break;
            default:
                if (Math.random() < 0.5f) { // getting 50/50 chance - user chose 'random'
                    answer = word.getSpanish();
                    question = word.getEnglish();
                    lang = "Spanish";
                } else {
                    answer = word.getEnglish();
                    question = word.getSpanish();
                    lang = "English";
                }
        }
    }

    /*
    *  Checks if user answers is correct, awards a point. 
    */
    public int checkAnswer(String uAnswer) {
        int reward = 0;
        //Check the answer based on the absolute value of the difference between uAnswer and cAnswer. 
        if (answer.equals(uAnswer)) {
            reward = 1;
        }
        return reward;
    }
    
    /*
    *   Returns the correct answer string of this card
    */
    public String getAnswer() {
        return answer;
    }

    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @return the lang
     */
    public String getLang() {
        return lang;
    }
    
    
    
}
