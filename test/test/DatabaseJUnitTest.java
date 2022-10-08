/*
 * Test to see if cards are generated correctly.
 */
package test;

import org.junit.Test; // for @Test
import org.junit.*;
import project2.Model;
import static org.junit.Assert.*;
import project2.Word;

/**
 *
 * @author carls
 */
public class DatabaseJUnitTest {

    private Model model;
    private String username;

    public DatabaseJUnitTest() {
    }

    @Before
    public void setUp() {
        model = new Model();
    }

    @Test
    public void testGetWordsInOrder() {
        System.out.println("Testing order of words.");
        model.getDbWords(true, true);
        
        for (int i = 0; i < 100; i ++) {
            assertTrue(model.data.getWords().get(i).getSpanish().compareTo(model.data.getWords().get(i + 1).getSpanish()) <= 0);
        }
        
        model.getDbWords(false, true);
        
        for (int i = 0; i < 100; i ++) {
            assertTrue(model.data.getWords().get(i).getEnglish().compareTo(model.data.getWords().get(i + 1).getEnglish()) <= 0);
        }
    }
    
    @Test
    public void testAddContainsRemoveWord() {
        Word word = new Word("hola", "hello");
        model.getDb().insertWord(word);
        assertTrue(model.getDb().containsWord(word.getSpanish()));
        model.getDb().deleteWord(word.getSpanish());
        assertFalse(model.getDb().containsWord(word.getSpanish()));
    }
    
    @Test
    public void testGetUserWords() {
        model.getDb().insertTestData();
        model.data = model.getDb().getUser("John");
        
        assertTrue(model.data.getWords().size() == 1);
    }
}
