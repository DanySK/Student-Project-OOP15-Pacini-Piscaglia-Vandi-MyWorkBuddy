package it.unibo.oop.myworkoutbuddy.view.handlers;

import java.util.Optional;

import it.unibo.oop.myworkoutbuddy.view.AccessView;
import it.unibo.oop.myworkoutbuddy.view.ViewsObserver;
import it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * 
 * Handler of the accessView. It handles the events captured by the GUI and
 * collects user input
 *
 */
public final class AccessHandler implements AccessView {

    @FXML
    private TextField txtID;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private MenuButton btnSelect;

    /**
     * Open menuView.
     */
    @FXML
    private void login() {
        if (ViewsHandler.getObserver().loginUser()) {
            FxWindowFactory.replaceWindow("Menu.fxml", txtID.getScene());
        } else {
            FxWindowFactory.showDialog("Uncorrect data", "Your username or password isn't correct", Optional.empty(),
                    AlertType.ERROR);
        }
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
    public String getPassword() {
        return txtPassword.getText();
    }

    @Override
    public String getStyle() {
        return btnSelect.getText();
    }

    @Override
    public String getUsername() {
        return txtID.getText();
    }

}
