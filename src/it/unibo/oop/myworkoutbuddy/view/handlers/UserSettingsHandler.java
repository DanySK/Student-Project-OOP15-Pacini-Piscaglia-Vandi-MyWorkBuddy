package it.unibo.oop.myworkoutbuddy.view.handlers;

import it.unibo.oop.myworkoutbuddy.view.UserSettingsView;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * 
 * Handler of the accessView. It handles the events captured by the GUI
 * collecting user changes.
 */
public class UserSettingsHandler implements UserSettingsView {

    @FXML
    private TextField txtSurname;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtPassword;

    @Override
    public String getNewName() {
        return txtName.getText();
    }

    @Override
    public String getNewSurname() {
        return txtSurname.getText();
    }

    @Override
    public int getNewAge() {
        return Integer.parseInt(txtAge.getText());
    }

    @Override
    public String getNewEmail() {
        return txtEmail.getText();
    }

    @Override
    public String getNewPassword() {
        return txtPassword.getText();
    }

}
