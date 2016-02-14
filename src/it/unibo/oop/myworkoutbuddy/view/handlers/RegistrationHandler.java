package it.unibo.oop.myworkoutbuddy.view.handlers;

import it.unibo.oop.myworkoutbuddy.view.RegistrationView;
import it.unibo.oop.myworkoutbuddy.view.ViewsObserver;
import it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    private Button btnReturnLogin;

    private ViewsObserver observer = ViewsHandler.getObserver();

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
    private void register() {
        // if (observer.registerUser()) {
        FxWindowFactory.showDialog("User registered!",
                "Congratulations " + getUsername() + ", you are now registered!");
        returnLogin();
        // }else{

        // }
    }

    @FXML
    private void returnLogin() {
        FxWindowFactory.replaceWindow("Access.fxml", btnReturnLogin.getScene());
    }

}
