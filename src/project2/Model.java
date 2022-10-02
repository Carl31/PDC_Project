/*
 * The Model class for MVC
 */
package project2;

import java.util.Observable;

/**
 *
 * @author carls
 */
public class Model extends Observable {

    public ModelData data;
    private GameMenu gameMenu;
    private DatabaseMenu dbMenu;
    private StatsMenu statsMenu;
    protected Database db;

    public Model() {
        // setup db, menus and mvc data
        db = new Database();
        db.dbsetup();
        
        data = new ModelData();
        gameMenu = new GameMenu(data);
        dbMenu = new DatabaseMenu(data);
        statsMenu = new StatsMenu(data);
    }
    
    public void startGameMenu() {
        // ensure ModelData for gameMenu is updated
        gameMenu.run();
    }
    
    public void startDBMenu() {
        // ensure ModelData for DBMenu is updated
        dbMenu.run();
    }
    
    public void startStatsMenu() {
        // ensure ModelData for statsMenu is updated
        statsMenu.run();
    }
    
    public void logout() {
        this.data.setUsername("");
        this.data.setUser(null);
        this.data.setIsLoggedIn(false);
    }
    
    public void notifyView() {
        this.setChanged();
        notifyObservers(data);
    }
    
    public boolean verifyUsername(String username) {
        if (username == null || username.length() < 3) {
            return false;
        }

        return isAllowedName(username.toCharArray());
    }
    
    private boolean isAllowedName(char[] username) {
        boolean isValid = true;
        
        for (char c : username) {

            // if alphanumeric character
            if (!Character.isLetterOrDigit(c)) isValid = false;
        }

        return isValid;
    }
    
    protected void exitGame() {
        db.closeConnections();
        System.exit(0);
    }
}
