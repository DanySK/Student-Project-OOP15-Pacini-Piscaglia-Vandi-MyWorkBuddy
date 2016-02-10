package it.unibo.oop.myworkoutbuddy.view.handlers;

import it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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

    @FXML
    private VBox btnContainer;

    @FXML
    private Button btnQuit;

    @FXML
    private Button btnLogout;

    private Button lastPressed;

    private static final double WIDTH_BUTTON_ANIMATION = 250;

    private static final double WIDTH_BUTTON_NORMAL = 200.0;

    private static final double HIDE_MENU_DELTA_WIDTH = 100.0;

    private static final double SHOW_MENU_DELTA_WIDTH = 80;

    private static final String cssSelectStyle = "-fx-background-color: yellow; -fx-font: bold 10pt 'Serif';";

    /**
     * Set createRoutine view in the menu center.
     */
    @FXML
    private void setCreateRoutineView() {
        sceneSwitch("CreateRoutine.fxml", btnCreate, "Create your routine");
    }

    /**
     * Set selectRoutine view in the menu center.
     */
    @FXML
    private void setSelectRoutineView() {
        sceneSwitch("SelectRoutine.fxml", btnSelect, "Select your routine");
    }

    /**
     * Set user statistics view in the menu center.
     */
    @FXML
    private void setStatisticsView() {
        sceneSwitch("Statistics.fxml", btnStatistics, "Statistics");
    }

    /**
     * Set user settings view in the menu center.
     */
    @FXML
    private void setUserSettingsView() {
        sceneSwitch("UserSettings.fxml", btnSettings, "User Settings");
    }

    /**
     * Terminates application.
     */
    @FXML
    private void quitApp() {
        System.exit(0);
    }

    /**
     * Return to login view.
     */
    @FXML
    private void logout() {
        new FxWindowFactory().openWindow("Access.fxml", false);
        final Stage stageAccess = (Stage) menuTitle.getScene().getWindow();
        stageAccess.close();
    }

    /**
     * Animation when user move mouse on a button
     */
    @FXML
    private void moveCreateMouseAnimation() {
        setMouseAnimation(btnCreate);
    }

    /**
     * Animation when user move mouse on a button
     */
    @FXML
    private void exitCreateMouseAnimation() {
        unSetMouseAnimation(btnCreate);
    }

    @FXML
    private void moveSelectMouseAnimation() {
        setMouseAnimation(btnSelect);
    }

    @FXML
    private void exitSelectMouseAnimation() {
        unSetMouseAnimation(btnSelect);
    }

    @FXML
    private void moveStatisticsMouseAnimation() {
        setMouseAnimation(btnStatistics);
    }

    @FXML
    private void exitStatisticsMouseAnimation() {
        unSetMouseAnimation(btnStatistics);
    }

    @FXML
    private void moveSettingsMouseAnimation() {
        setMouseAnimation(btnSettings);
    }

    @FXML
    private void exitSettingsMouseAnimation() {
        unSetMouseAnimation(btnSettings);
    }

    @FXML
    private void moveLogoutMouseAnimation() {
        setMouseAnimation(btnLogout);
    }

    @FXML
    private void exitLogoutMouseAnimation() {
        unSetMouseAnimation(btnLogout);
    }

    @FXML
    private void moveQuitMouseAnimation() {
        setMouseAnimation(btnQuit);
    }

    @FXML
    private void exitQuitMouseAnimation() {
        unSetMouseAnimation(btnQuit);
    }

    @FXML
    private void openMenu() {
        btnContainer.setTranslateX(SHOW_MENU_DELTA_WIDTH);
    }

    @FXML
    private void hideMenu() {
        btnContainer.setTranslateX(-HIDE_MENU_DELTA_WIDTH);
    }

    /**
     * Factored code of mouse animation.
     */
    private void setMouseAnimation(final Button btn) {
        btn.setStyle("-fx-font-weight: bold");
        if (btn != btnQuit && btn != btnLogout) {
            btn.setMaxWidth(WIDTH_BUTTON_ANIMATION);
        }
    }

    /**
     * Factored code to unSet mouse animation.
     */
    private void unSetMouseAnimation(final Button btn) {
        btn.setStyle("-fx-font: 13px 'Serif'; -fx-padding: 10;");
        if (btn != btnQuit && btn != btnLogout) {
            btn.setMaxWidth(WIDTH_BUTTON_NORMAL);
        }
    }

    /**
     * Set the initial style.
     */
    private void resetButtonStyle() {
        if (lastPressed != null) {
            lastPressed.setStyle("-fx-font: 13px 'Serif'; -fx-padding: 10;");
        }
    }

    /**
     * Factored code of scene switch and button management.
     */
    private void sceneSwitch(final String fxmlFile, final Button btnPressed, final String menuLabel) {
        if (lastPressed != btnPressed) {
            mainPane.setCenter(new FxWindowFactory().openWindow(fxmlFile, true));
            menuTitle.setText(menuLabel);
            btnPressed.setStyle(cssSelectStyle);
            resetButtonStyle();
            lastPressed = btnPressed;
        }
    }
}
