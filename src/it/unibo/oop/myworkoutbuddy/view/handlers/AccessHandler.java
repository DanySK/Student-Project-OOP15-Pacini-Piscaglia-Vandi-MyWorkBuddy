package it.unibo.oop.myworkoutbuddy.view.handlers;

import java.io.IOException;

import it.unibo.oop.myworkoutbuddy.view.AccessView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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
    void login(final ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void register(final ActionEvent event) {

        /* Opening registration window */
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("../Registration.fxml"));
            final Stage stageRegister = new Stage();
            final BorderPane root = (BorderPane) loader.load();
            final Scene scene = new Scene(root);
            final RegistrationHandler access = loader.getController();
            scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
            stageRegister.setScene(scene);
            stageRegister.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* Closing login window */
        final Stage stageAccess = (Stage) btnRegister.getScene().getWindow();
        stageAccess.close();
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
