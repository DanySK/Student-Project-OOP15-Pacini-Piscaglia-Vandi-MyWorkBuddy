package it.unibo.oop.myworkoutbuddy.view.handlers;

import it.unibo.oop.myworkoutbuddy.view.AccessView;
import it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory;
import it.unibo.oop.myworkoutbuddy.view.factory.StyleSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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
    private Button btnRegister;

    @FXML
    private PasswordField txtPassword;

    /**
     * Open menuView.
     */
    @FXML
    public void login() {
        new FxWindowFactory().openWindow("Menu.fxml", false);
        closeWindow();
    }

    /**
     * Open registration view.
     */
    @FXML
    public void register() {
        /* Opening registration window */
        new FxWindowFactory().openWindow("Registration.fxml", false);
        closeWindow();
    }

    /**
     * Change cssSheet with original one.
     */
    @FXML
    void setOriginalStyle() {
        StyleSingleton.setCssStyle("original.css");
        new FxWindowFactory().openWindow("Access.fxml", false);
        closeWindow();
    }

    /**
     * Change cssSheet with dark one.
     */
    @FXML
    public void setDarkStyle() {
        StyleSingleton.setCssStyle("dark.css");
        new FxWindowFactory().openWindow("Access.fxml", false);
        closeWindow();
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

    /* Close a JavaFx window */
    private void closeWindow() {
        final Stage stageAccess = (Stage) btnRegister.getScene().getWindow();
        stageAccess.close();
    }

}
