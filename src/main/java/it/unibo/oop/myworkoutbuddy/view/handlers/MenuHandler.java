package it.unibo.oop.myworkoutbuddy.view.handlers;

import it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private final EventHandler<MouseEvent> enteredAnimation = i -> {
        final Button btn = (Button) i.getSource();
        if (lastPressed != btn) {
            btn.setId("movedInMenuButton");
        }
    };

    private final EventHandler<MouseEvent> exitedAnimation = i -> {
        final Button btn = (Button) i.getSource();
        if (lastPressed != btn) {
            btn.setId("menuButton");
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
            switch (btnPressed.getText()) {
            case "Create Routine":
                labelToSet = "Create your routine";
                fxmlToLoad = "CreateRoutine.fxml";
                break;

            case "Select Routine":
                labelToSet = "Select your routine";
                fxmlToLoad = "SelectRoutine.fxml";
                break;

            case "Statistics":
                labelToSet = "Statistics";
                fxmlToLoad = "Statistics.fxml";
                break;

            case "Settings":
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
            btnPressed.setId("selectedMenuButton");
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
        ViewsHandler.getObserver().logoutUser();
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
        btnContainer.getChildren().stream().map(i -> (Button) i).forEach(btn -> setButtonImages(btn));
    }

    private void setButtonImages(final Button btn) {
        Image image = null;
        switch (btn.getText()) {

        case "Create Routine":
            image = new Image(
                    "https://chakraos.org/wiki/images/thumb/0/0d/Address-book-new.png/48px-Address-book-new.png");
            break;

        case "Select Routine":
            image = new Image(
                    "http://files.softicons.com/download/application-icons/minicons-icons-by-kyo-tux/png/48/Forward.png");
            break;

        case "Statistics":
            image = new Image("http://www.fancyicons.com/free-icons/103/office/png/48/chart_48.png");
            break;

        case "Settings":
            image = new Image("http://www.mytechlogy.com/view/images/Settings.png");
            break;

        default:
            break;
        }
        btn.setGraphic(new ImageView(image));
    }

    /**
     * Set the initial menu button style.
     */
    private void resetButtonStyle(final Button btn) {
        if (btn != null) {
            btn.setId("menuButton");
        }
    }

}
