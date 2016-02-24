package it.unibo.oop.myworkoutbuddy.view.handlers;

import static it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory.replaceWindow;
import static it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory.showDialog;

import java.util.Optional;

import it.unibo.oop.myworkoutbuddy.view.RegistrationView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import static it.unibo.oop.myworkoutbuddy.view.handlers.ViewsHandler.getObserver;

/**
 * 
 * Handler of the RegistrationView. It handles the events captured by the GUI
 * and collects user personal information.
 */
public final class RegistrationHandler implements RegistrationView {

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
    private PasswordField txtPassConfirm;

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
        try {
            return Integer.parseInt(txtAge.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
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
    public String getPasswordConfirm() {
        return txtPassConfirm.getText();
    }

    @Override
    public String getUsername() {
        return txtUser.getText();
    }

    @Override
    public int getHeight() {
        try {
            return Integer.parseInt(txtHeight.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public double getWeight() {
        try {
            return Double.parseDouble(txtWeight.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @FXML
    private void register() {
        final StringBuilder errors = new StringBuilder();
        getObserver().registerUser().forEach(err -> errors.append(err + "\n"));
        if (errors.toString().isEmpty()) {
            showDialog("User registered!", "Congratulations " + getUsername() + ", you are now registered!",
                    Optional.empty(), AlertType.INFORMATION);
            returnLogin();
        } else {
            showDialog("Uncorrect data inserted!", errors.toString(), Optional.empty(), AlertType.ERROR);
        }
    }

    @FXML
    private void returnLogin() {
        replaceWindow("Access.fxml", btnReturnLogin.getScene());
    }

}
