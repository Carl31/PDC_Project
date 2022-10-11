/*
 * Test to see if cards are generated correctly and game runs.
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
public class GameJUnitTest {

    private Model model;
    private String username;

    public GameJUnitTest() {
    }

    @Before
    public void setUp() {
        model = new Model();
    }

    @Test
    public void testGenerateCards() {
        System.out.println("Testing card amount.");
        model.setConfigData(new GameConfig(3, "Spanish", false));
        model.generateCards();
        
        assertTrue(model.getCards().size() == 3);
        assertTrue(model.getCards().poll().getLang().equals("Spanish"));
        assertTrue(model.getCards().poll().getLang().equals("Spanish"));
        assertTrue(model.getCards().poll().getLang().equals("Spanish"));
        assertNull(model.getCards().poll());
        
        System.out.println("Testing card language when Random...");
        int spanishCards = 0;
        int englishCards = 0;
        model.setConfigData(new GameConfig(10, "Random", false));
        model.generateCards();
        
        for (int i = 0; i < 10; i++) {
            if (model.getCards().peek().getLang().equals("English")) {
                englishCards++;
                model.getCards().poll();
            } else if (model.getCards().peek().getLang().equals("Spanish")) {
                spanishCards++;
                model.getCards().poll();
            }
        }
        assertTrue(englishCards > 0);
        assertTrue(spanishCards > 0);
    }
}
