/*
 * Test to see if the views' messages/warnings are updated accordingly
 */
package test;

import java.util.HashSet;
import org.junit.Test; // for @Test
import org.junit.*;
import project2.Model;
import static org.junit.Assert.*;
import project2.GameConfig;
import project2.UserData;
import project2.Word;

/**
 *
 * @author carls
 */
public class RevisionGameJUnitTest {

    private Model model;
    private String username;

    public RevisionGameJUnitTest() {
    }

    @Before
    public void setUp() {
        model = new Model();
    }

    @Test
    public void testStartGameNoCards() {
        model.data.setUser(new UserData(0, 0, new HashSet<Word>()));
        System.out.println("Testing empty card set.");
        model.setConfigData(new GameConfig(3, "Spanish", true));
        model.startGame();
        assertTrue(model.data.getMessage().equals("Unable to revise: Revision pile empty!!"));
        assertFalse(model.data.isPlaying());
    }
    
    @Test
    public void testStartGameStats() {
        model.data.setUser(new UserData(10, 4.4f, new HashSet<Word>()));
        System.out.println("Testing user stats.");
        model.setConfigData(new GameConfig(3, "Spanish", true));
        model.startGame();
        assertTrue(model.data.getUser().getGamesPlayed() == 10);
        assertFalse(model.data.getUser().getCorrectPercent() == 0.0f);
    }
}
