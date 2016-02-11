package it.unibo.oop.myworkoutbuddy.view.handlers;

import it.unibo.oop.myworkoutbuddy.view.AccessView;
import it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private MenuButton btnSelect;

    @FXML
    private PasswordField txtPassword;

    /**
     * Open menuView.
     */
    @FXML
    private void login() {
        FxWindowFactory.replaceWindow("Menu.fxml", txtID.getScene());
    }

    /**
     * Open registration view.
     */
    @FXML
    private void register() {
        /* Opening registration window */
        FxWindowFactory.replaceWindow("Registration.fxml", txtPassword.getScene());
    }

    /**
     * Change cssSheet with original one.
     */
    @FXML
    private void setOriginalStyle() {
        FxWindowFactory.setCssStyle("original.css");
        FxWindowFactory.replaceWindow("Access.fxml", btnSelect.getScene());
    }

    /**
     * Change cssSheet with dark one.
     */
    @FXML
    private void setDarkStyle() {
        FxWindowFactory.setCssStyle("dark.css");
        FxWindowFactory.replaceWindow("Access.fxml", btnSelect.getScene());
    }

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
