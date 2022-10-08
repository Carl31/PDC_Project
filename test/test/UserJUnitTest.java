/*
 * Test to see if username is valid and logout logs out user
 */
package test;

import org.junit.Test; // for @Test
import org.junit.*;
import project2.Model;
import static org.junit.Assert.*;

/**
 *
 * @author carls
 */
public class UserJUnitTest {

    private Model model;
    private String username;

    public UserJUnitTest() {
    }

    @Before
    public void setUp() {
        model = new Model();
        username = "aValidUsername";
    }

    @Test
    public void testVerifyUsername() {
        System.out.println("Verifying usernames...");
        assertTrue(model.verifyUsername(username));
        username = "xx";
        assertFalse(model.verifyUsername(username));
        username = "xxy";
        assertTrue(model.verifyUsername(username));
        username = "xxyy";
        assertTrue(model.verifyUsername(username));
        username = "Hello4";
        assertTrue(model.verifyUsername(username));
        username = "Helllo";
        assertTrue(model.verifyUsername(username));
        username = "John_H";
        assertFalse(model.verifyUsername(username));
    }
    
    @Test
    public void testLogout() {
        System.out.println("Checking resets...");
        model.logout();
        assertNull(model.data.getUser());
        assertEquals("", model.data.getUsername());
    }
}
