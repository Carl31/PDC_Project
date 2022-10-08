/*
 * Test to see if Card class is giving points correctly.
 */
package test;

import org.junit.Test; // for @Test
import org.junit.*;
import project2.Model;
import static org.junit.Assert.*;
import project2.GameConfig;

/**
 *
 * @author carls
 */
public class CardJUnitTest {

    private Model model;

    public CardJUnitTest() {
    }

    @Before
    public void setUp() {
        model = new Model();
        model.setConfigData(new GameConfig(6, "Spanish", false));
        model.generateCards();
    }

    @Test
    public void testCheckAnswer() {
        System.out.println("Testing card checkanswer.");
        
        for (int i = 0; i < 3; i++) {
            String answer = model.getCards().peek().getAnswer();
            assertTrue(model.getCards().poll().checkAnswer(answer) > 0);
        }
        for (int i = 0; i < 3; i++) {
            assertTrue(model.getCards().poll().checkAnswer("wrongAnswer") == 0);
        }
    }
}
