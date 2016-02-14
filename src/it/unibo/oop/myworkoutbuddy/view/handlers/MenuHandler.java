package it.unibo.oop.myworkoutbuddy.view.handlers;

import it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * Handler of the view menu, it allows user to switch view.
 *
 */
public final class MenuHandler {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Label menuTitle;

    @FXML
    private VBox btnContainer;

    @FXML
    private Button btnQuit;

    @FXML
    private Button btnLogout;

    private Button lastPressed;

    private static final double HIDE_MENU_DELTA_WIDTH = 100.0;

    private static final double SHOW_MENU_DELTA_WIDTH = 25;

    private static final int EXIT_MENU_TIME_DURATION = 2000;

    private static final String CSS_SELECT_STYLE = "-fx-background-color: lightBlue; -fx-font: bold 14px 'Serif';";

    private final EventHandler<MouseEvent> enteredAnimation = i -> {
        final Button btn = (Button) i.getSource();
        if (lastPressed != btn) {
            btn.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-color:DarkOrange;");
        }
    };

    private final EventHandler<MouseEvent> exitedAnimation = i -> {
        final Button btn = (Button) i.getSource();
        if (lastPressed != btn) {
            btn.setStyle("-fx-font: 13px 'Serif'; -fx-padding: 10;");
        }
    };

    /**
     * Factored code of scene switch and button management.
     */
    private final EventHandler<MouseEvent> setView = i -> {
        final Button btnPressed = (Button) i.getSource();
        String fxmlToLoad = "";
        String labelToSet = "";
        if (lastPressed != btnPressed) {
            switch (btnPressed.getId()) {
            case "btnCreate":
                labelToSet = "Create your routine";
                fxmlToLoad = "CreateRoutine.fxml";
                break;

            case "btnSelect":
                labelToSet = "Select your routine";
                fxmlToLoad = "SelectRoutine.fxml";
                break;

            case "btnStatistics":
                labelToSet = "Statistics";
                fxmlToLoad = "Statistics.fxml";
                break;

            case "btnSettings":
                labelToSet = "User Settings";
                fxmlToLoad = "UserSettings.fxml";
                break;

            default:
                new IllegalStateException();
                break;
            }
            mainPane.setCenter(FxWindowFactory.openWindow(fxmlToLoad, true));
            menuTitle.setText(labelToSet);
            resetButtonStyle(lastPressed);
            btnPressed.setStyle(CSS_SELECT_STYLE);
            lastPressed = btnPressed;
        }
    };

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
        FxWindowFactory.replaceWindow("Access.fxml", btnLogout.getScene());
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     */
    public void initialize() {
        btnContainer.getChildren().forEach(i -> {
            /* Animation when user move mouse on a button */
            i.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, enteredAnimation);
            /* Animation when user move mouse on a button */
            i.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, exitedAnimation);
            if (i != btnLogout && i != btnQuit) {
                i.addEventHandler(MouseEvent.MOUSE_CLICKED, setView);
            }
        });
    }

    @FXML
    private void openMenu() {
        btnContainer.setTranslateX(SHOW_MENU_DELTA_WIDTH);
    }

    @FXML
    private void hideMenu() {
        new Agent().start();
    }

    /**
     * Set the initial style.
     */
    private void resetButtonStyle(final Button btn) {
        if (btn != null) {
            btn.setStyle("-fx-font: 13px 'Serif'; -fx-padding: 10;");
        }
    }

    /**
     * Models a background thread created to handle the exit animation in the
     * menu bar.
     */
    public class Agent extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(EXIT_MENU_TIME_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            btnContainer.setTranslateX(-HIDE_MENU_DELTA_WIDTH);
        }
    }

}
