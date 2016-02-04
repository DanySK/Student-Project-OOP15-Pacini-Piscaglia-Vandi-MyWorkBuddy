package it.unibo.oop.myworkoutbuddy.view.handlers;

import it.unibo.oop.myworkoutbuddy.view.AccessView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * 
 * Handler of the accessView. It handles the events captured by the GUI and
 * collects user input
 *
 */
public class AccessHandler implements AccessView {

    @FXML
    private TextField txtID;

    @FXML
    private Button btnLogin;

    @FXML
    private MenuButton btnSelect;

    @FXML
    private Button btnRegister;

    @FXML
    private PasswordField txtPassword;

    @Override
    public String getID() {
        return txtID.getText();
    }

    @Override
    public String getPassword() {
        return txtPassword.getText();
    }

    @Override
    public String getStyle() {
        return btnSelect.getText();
    }

}
