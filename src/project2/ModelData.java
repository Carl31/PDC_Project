/*
 * Holding data to do with MVC function.
 */
package project2;

/**
 *
 * @author carls
 */
public class ModelData {
    private UserData user;
    private String username;
    private boolean isLoggedIn;
    
    public ModelData() {
        this.user = null;
        this.username = null;
        this.isLoggedIn = false;
    }
    
    public ModelData(String username, UserData user) {
        this.user = user;
        this.username = username;
        this.isLoggedIn = true;
    }
    
    public void logOut() {
        this.isLoggedIn = false;
        this.user = null;
        this.username = "";
    }

    /**
     * @return the user
     */
    public UserData getUser() {
        return user;
    }
    
    
    
}
