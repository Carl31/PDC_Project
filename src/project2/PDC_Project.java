/*
 * Driver class for project.
 */
package project2;

/**
 *
 * @author carls
 */
public class PDC_Project {

    public static void main(String[] args) {
        // main for testing db ...
//            Database db = new Database();
//            db.dbsetup();
//            db.getUser("John");
//            db.insertTestData();

        // main for building mvc ...
        View view = new View();
        Model model = new Model();
        Controller controller = new Controller(view, model);
        model.addObserver(view); // This builds the connection between view and model
    }

//    private static void printMenu() {
//        System.out.println("Note: To quit at any time, enter \"quit\".");
//        System.out.println("\nWhat would you like to do?");
//        System.out.println("\t 1 - Play Verb Cards: Test your vocabulary!");
//        System.out.println("\t 2 - Browse Database: Find translations, add and remove words!");
//        System.out.println("\t 3 - Stats View: View your game statistics!");
//    }

}
