package it.unibo.oop.myworkoutbuddy;

import java.io.IOException;

import it.unibo.oop.myworkoutbuddy.view.handlers.AccessHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Access.fxml"));
        final BorderPane root = (BorderPane) loader.load();
        final Scene scene = new Scene(root);
        final AccessHandler access = loader.getController();
        scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * 
     * Launches the application.
     * 
     * @param args
     */
    public static void main(final String... args) {
        launch(args);
    }

}
