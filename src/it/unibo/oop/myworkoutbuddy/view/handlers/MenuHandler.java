package it.unibo.oop.myworkoutbuddy.view.handlers;

import it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * Handler of the view menu, it allows user to switch view.
 *
 */
public class MenuHandler {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Button btnSelect;

    @FXML
    private Button btnStatistics;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnCreate;

    @FXML
    private Label menuTitle;

    private Button lastPressed;

    private String cssSelectStyle = "-fx-background-color: yellow; -fx-font: bold 10pt 'Serif';";

    /**
     * Set createRoutine view in the menu center.
     */
    @FXML
    public void setCreateRoutineView() {
        mainPane.setCenter(new FxWindowFactory().openWindow("CreateRoutine.fxml", true));
        menuTitle.setText("Create Routine");
        btnCreate.setStyle(cssSelectStyle);
        resetStyle();
        lastPressed = btnCreate;
    }

    /**
     * Set selectRoutine view in the menu center.
     */
    @FXML
    public void setSelectRoutineView() {
        mainPane.setCenter(new FxWindowFactory().openWindow("SelectRoutine.fxml", true));
        menuTitle.setText("Select Routine");
        btnSelect.setStyle(cssSelectStyle);
        resetStyle();
        lastPressed = btnSelect;
    }

    /**
     * Set user statistics view in the menu center.
     */
    @FXML
    public void setStatisticsView() {
        mainPane.setCenter(new FxWindowFactory().openWindow("Statistics.fxml", true));
        menuTitle.setText("Statistics");
        btnStatistics.setStyle(cssSelectStyle);
        resetStyle();
        lastPressed = btnStatistics;
    }

    /**
     * Set user settings view in the menu center.
     */
    @FXML
    public void setUserSettingsView() {
        mainPane.setCenter(new FxWindowFactory().openWindow("UserSettings.fxml", true));
        menuTitle.setText("User Settings");
        btnSettings.setStyle(cssSelectStyle);
        resetStyle();
        lastPressed = btnSettings;
    }

    @FXML
    void quitApp() {
        System.exit(0);
    }

    /**
     * Return to login view.
     */
    @FXML
    public void logout() {
        new FxWindowFactory().openWindow("Access.fxml", false);
        final Stage stageAccess = (Stage) menuTitle.getScene().getWindow();
        stageAccess.close();
    }

    private void resetStyle() {
        if (lastPressed != null) {
            lastPressed.setStyle("-fx-font: 13px 'Serif'; -fx-padding: 10;");
        }
    }

}
