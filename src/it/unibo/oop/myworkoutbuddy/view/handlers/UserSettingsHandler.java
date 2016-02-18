package it.unibo.oop.myworkoutbuddy.view.handlers;

import static it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory.showDialog;

import java.util.Optional;

import it.unibo.oop.myworkoutbuddy.view.UserSettingsView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * 
 * Handler of the accessView. It handles the events captured by the GUI
 * collecting user changes.
 */
public final class UserSettingsHandler implements UserSettingsView {

    @FXML
    private TextField txtSurname;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtAge;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtPassConfirm;

    @Override
    public String getNewName() {
        return txtName.getText();
    }

    @Override
    public String getNewSurname() {
        return txtSurname.getText();
    }

    @Override
    public int getNewAge() {
        return Integer.parseInt(txtAge.getText());
    }

    @Override
    public String getNewEmail() {
        return txtEmail.getText();
    }

    @Override
    public String getNewPassword() {
        return txtPassword.getText();
    }

    @Override
    public String getPasswordConfirm() {
        return txtPassConfirm.getText();
    }

    @FXML
    private void saveChanges() {
        if (ViewsHandler.getObserver().setUserData()) {
            showDialog("Data saved!", "Your data has been successfully saved!", Optional.empty(),
                    AlertType.ERROR);
        } else {
            showDialog("Wrong data", "You have inserted wrong data", Optional.empty(), AlertType.ERROR);
        }
    }

}
