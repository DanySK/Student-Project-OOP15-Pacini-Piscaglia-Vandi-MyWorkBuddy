package it.unibo.oop.myworkoutbuddy.view.handlers;

import static it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory.replaceWindow;
import static it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory.setCssStyle;
import static it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory.showDialog;
import static it.unibo.oop.myworkoutbuddy.view.handlers.ViewsHandler.getObserver;

import java.util.Optional;

import it.unibo.oop.myworkoutbuddy.view.AccessView;
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
        if (getObserver().loginUser().isEmpty()) {
            replaceWindow("Menu.fxml", txtID.getScene());
        } else {
            final StringBuilder errors = new StringBuilder();
            getObserver().loginUser().forEach(err -> errors.append(err));
            showDialog("Uncorrect data", errors.toString(), Optional.empty(), AlertType.ERROR);
        }
    }

    /**
     * Open registration view.
     */
    @FXML
    private void register() {
        /* Opening registration window */
        replaceWindow("Registration.fxml", txtPassword.getScene());
    }

    /**
     * Change cssSheet with original one.
     */
    @FXML
    private void setOriginalStyle() {
        setCssStyle("original.css");
        replaceWindow("Access.fxml", btnSelect.getScene());
    }

    /**
     * Change cssSheet with dark one.
     */
    @FXML
    private void setDarkStyle() {
        setCssStyle("dark.css");
        replaceWindow("Access.fxml", btnSelect.getScene());
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
