package it.unibo.oop.myworkoutbuddy.view.handlers;

import it.unibo.oop.myworkoutbuddy.view.AccessView;
import it.unibo.oop.myworkoutbuddy.view.FxStageWindow;
import javafx.event.ActionEvent;
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
    private Button btnLogin;

    @FXML
    private MenuButton btnSelect;

    @FXML
    private Button btnRegister;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private void login(final ActionEvent event) {
        final FxStageWindow window = new FxStageWindow();
        window.openWindow("Menu.fxml", "application.css", false);
        closeWindow();
    }

    @FXML
    private void register(final ActionEvent event) {

        /* Opening registration window */
        final FxStageWindow window = new FxStageWindow();
        window.openWindow("Registration.fxml", "application.css", false);

        /* Closing login window */
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
