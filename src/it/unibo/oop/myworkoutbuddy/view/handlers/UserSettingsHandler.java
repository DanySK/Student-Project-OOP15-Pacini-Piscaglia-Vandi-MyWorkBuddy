package it.unibo.oop.myworkoutbuddy.view.handlers;

import static it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory.showDialog;
import static it.unibo.oop.myworkoutbuddy.view.handlers.ViewsHandler.getObserver;

import java.util.Optional;

import it.unibo.oop.myworkoutbuddy.view.UserSettingsView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * 
 * Handler of the accessView. It handles the events captured by the GUI
 * collecting user changes.
 */
public final class UserSettingsHandler implements UserSettingsView {

    @FXML
    private TextField surname;

    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private TextField age;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField passwordConfirm;

    @Override
    public String getNewName() {
        return name.getText();
    }

    @Override
    public String getNewSurname() {
        return surname.getText();
    }

    @Override
    public int getNewAge() {
        try {
            return Integer.parseInt(age.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public String getNewEmail() {
        return email.getText();
    }

    @Override
    public String getNewPassword() {
        return password.getText();
    }

    @Override
    public String getPasswordConfirm() {
        return passwordConfirm.getText();
    }

    @FXML
    private void saveChanges() {
        if (getObserver().setUserData()) {
            showDialog("Data saved!", "Your data has been successfully saved!", Optional.empty(),
                    AlertType.ERROR);
        } else {
            showDialog("Wrong data", "You have inserted wrong data", Optional.empty(), AlertType.ERROR);
        }
    }

    /**
     * It fills textFields with current user data.
     */
    public void initialize() {
        final GridPane txtPane = (GridPane) name.getParent();
        getObserver().getUserData().forEach((field, data) -> {
            System.out.println(field + "-" + data);
            txtPane.getChildren().stream()
            .filter(txt -> txt.getId().equals(field))
            .map(f -> (TextField) f)
            .forEach(f -> f.setText(data.toString()));
        });
    }

}
