package it.unibo.oop.myworkoutbuddy.view.handlers;

import it.unibo.oop.myworkoutbuddy.view.FxStageWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

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

    private Button lastPressed;

    private String cssSelectStyle = "-fx-background-color: yellow; -fx-font: bold 10pt 'Serif';";

    @FXML
    private void setCreateRoutineView(ActionEvent event) {
        mainPane.setCenter(new FxStageWindow().openWindow("CreateRoutine.fxml", "application.css", true));
        btnCreate.setStyle(cssSelectStyle);
        resetStyle();
        lastPressed = btnCreate;
    }

    @FXML
    private void setSelectRoutineView(ActionEvent event) {
        mainPane.setCenter(new FxStageWindow().openWindow("SelectRoutine.fxml", "application.css", true));
        btnSelect.setStyle(cssSelectStyle);
        resetStyle();
        lastPressed = btnSelect;
    }

    @FXML
    private void setStatisticsView(ActionEvent event) {
        mainPane.setCenter(new FxStageWindow().openWindow("Statistics.fxml", "application.css", true));
        btnStatistics.setStyle(cssSelectStyle);
        resetStyle();
        lastPressed = btnStatistics;
    }

    @FXML
    private void setUserSettingsView(ActionEvent event) {
        mainPane.setCenter(new FxStageWindow().openWindow("UserSettings.fxml", "application.css", true));
        btnSettings.setStyle(cssSelectStyle);
        resetStyle();
        lastPressed = btnSettings;
    }

    private void resetStyle() {
        if (lastPressed != null) {
            lastPressed.setStyle("-fx-font: 13px 'Serif'; -fx-padding: 10; -fx-background-color: orange;");
        }
    }

}
