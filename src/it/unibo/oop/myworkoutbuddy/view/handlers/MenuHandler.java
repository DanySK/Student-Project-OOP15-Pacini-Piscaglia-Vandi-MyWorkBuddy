package it.unibo.oop.myworkoutbuddy.view.handlers;

import it.unibo.oop.myworkoutbuddy.view.FxStageWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

/**
 *
 * Handler of the view menu, it allows user to switch view.
 *
 */
public class MenuHandler {

    @FXML
    private BorderPane mainPane;

    @FXML
    void setCreateRoutineView(ActionEvent event) {
        mainPane.setCenter(new FxStageWindow().openWindow("CreateRoutine.fxml", "application.css", true));
    }

    @FXML
    void setSelectRoutineView(ActionEvent event) {
        mainPane.setCenter(new FxStageWindow().openWindow("SelectRoutine.fxml", "application.css", true));
    }

    @FXML
    void setStatisticsView(ActionEvent event) {
        mainPane.setCenter(new FxStageWindow().openWindow("Statistics.fxml", "application.css", true));
    }

    @FXML
    void setUserSettingsView(ActionEvent event) {
        mainPane.setCenter(new FxStageWindow().openWindow("UserSettings.fxml", "application.css", true));
    }

}
