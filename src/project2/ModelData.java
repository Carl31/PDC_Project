/*
 * Holding data to do with MVC function.
 */
package project2;

import java.util.ArrayList;

/**
 *
 * @author carls
 */
public class ModelData {
    private UserData user;
    private String username;
    private boolean isLoggedIn;
    protected ArrayList<Word> words;
    
    public ModelData() {
        this.user = null;
        this.username = null;
        this.isLoggedIn = false;
        this.words = null;
    }
    
    public ModelData(String username, UserData user) {
        this.user = user;
        this.username = username;
        this.isLoggedIn = true;
    }
    
    public void logOut() {
        this.setIsLoggedIn(false);
        this.setUser(null);
        this.setUsername("");
    }

    /**
     * @return the user
     */
    public UserData getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserData user) {
        this.user = user;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the isLoggedIn
     */
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    /**
     * @param isLoggedIn the isLoggedIn to set
     */
    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
    
    
    
    
    
}
