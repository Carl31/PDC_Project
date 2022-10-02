/*
 * The controller class for the VMC
 */
package project2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 *
 * @author carls
 */
public class Controller implements ActionListener {

    public View view;
    public Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        this.view.addActionListener(this); // Add Actionlistener (the instance of this class) to View.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand(); // Obtain the text displayed on the component.
        if (actionCommand.equals(view.loginBtn.getActionCommand())) {
            // upon login
            if (model.verifyUsername(view.username.getText())) {
                model.data = model.db.getUser(view.username.getText());
                if (model.data.isLoggedIn()) {
                    model.notifyView();
                }
            }

        } else if (actionCommand.equals(view.logoutBtn.getActionCommand())) {
            // upon logout
            model.logout();
            model.notifyView();
        } else if (actionCommand.equals(view.exitBtn.getActionCommand())) {
            // upon exit
            model.exitGame();
        }
    }
}
