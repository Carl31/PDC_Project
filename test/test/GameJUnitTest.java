/*
 * Test to see if cards are generated correctly.
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
        model.setConfigData(new GameConfig(3, "Spanish", false));
        model.generateCards();
    }

    @Test
    public void testGenerateCards() {
        assertTrue(model.getCards().size() == 3);
        assertTrue(model.getCards().poll().getLang().equals("Spanish"));
        assertTrue(model.getCards().poll().getLang().equals("Spanish"));
        assertTrue(model.getCards().poll().getLang().equals("Spanish"));
        assertNull(model.getCards().poll());
    }
}
