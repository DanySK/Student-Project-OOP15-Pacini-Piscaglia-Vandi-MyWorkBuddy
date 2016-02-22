package it.unibo.oop.myworkoutbuddy;

import java.io.IOException;

import it.unibo.oop.myworkoutbuddy.controller.Controller;
import it.unibo.oop.myworkoutbuddy.model.MyWorkoutBuddyModelImpl;
import it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory;
import it.unibo.oop.myworkoutbuddy.view.handlers.ViewsHandler;
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
        FxWindowFactory.openWindow("Access.fxml", false);
    }

    /**
     * Launches the application.
     * 
     * @param args
     *            not used.
     */
    public static void main(final String... args) {
        new Controller(new MyWorkoutBuddyModelImpl(), new ViewsHandler());
        launch(args);
    }

}
