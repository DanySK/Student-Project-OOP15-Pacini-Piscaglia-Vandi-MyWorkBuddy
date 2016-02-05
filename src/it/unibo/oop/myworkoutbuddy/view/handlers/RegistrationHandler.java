package it.unibo.oop.myworkoutbuddy.view.handlers;

import java.io.IOException;

import it.unibo.oop.myworkoutbuddy.view.RegistrationView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * 
 * Handler of the RegistrationView. It handles the events captured by the GUI
 * and collects user personal information.
 */
public class RegistrationHandler implements RegistrationView {

    @FXML
    private TextField txtWeight;

    @FXML
    private TextField txtSurname;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtUser;

    @FXML
    private TextField txtHeight;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnReturnLogin;

    @Override
    public String getName() {
        return txtName.getText();
    }

    @Override
    public String getSurname() {
        return txtSurname.getText();
    }

    @Override
    public int getAge() {
        return Integer.parseInt(txtAge.getText());
    }

    @Override
    public String getEmail() {
        return txtEmail.getText();
    }

    @Override
    public String getPassword() {
        return txtPassword.getText();
    }

    @Override
    public String getUsername() {
        return txtUser.getText();
    }

    @Override
    public int getHeight() {
        return Integer.parseInt(txtHeight.getText());
    }

    @Override
    public double getWeight() {
        return Double.parseDouble(txtWeight.getText());
    }

    @FXML
    void register(final ActionEvent event) {
        returnLogin(null);
    }

    @FXML
    void returnLogin(final ActionEvent event) {
        /* Opening login window */
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("../Access.fxml"));
            final Stage stageRegister = new Stage();
            final BorderPane root = (BorderPane) loader.load();
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
            stageRegister.setScene(scene);
            stageRegister.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* Closing registration window */
        final Stage stageAccess = (Stage) btnRegister.getScene().getWindow();
        stageAccess.close();
    }

}
