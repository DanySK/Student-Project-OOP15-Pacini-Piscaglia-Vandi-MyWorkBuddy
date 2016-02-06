package it.unibo.oop.myworkoutbuddy;

import java.io.IOException;

import it.unibo.oop.myworkoutbuddy.view.FxStageWindow;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * Start of the Application, it loads the graphics of the stage using FXML file
 * for the
 * GUI structure and CSS sheet for the GUI style.
 *
 */
public class Main extends Application {

    @Override
    public void start(final Stage stage) throws IOException {
        final FxStageWindow window = new FxStageWindow();
        window.openWindow("Access.fxml", "application.css", false);
    }

    /**
     * Launches the application.
     * 
     * @param args
     */
    public static void main(final String... args) {
        launch(args);
    }

}
