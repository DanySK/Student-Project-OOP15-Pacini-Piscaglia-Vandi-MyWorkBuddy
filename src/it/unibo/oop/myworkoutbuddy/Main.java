package it.unibo.oop.myworkoutbuddy;

import java.io.IOException;

import it.unibo.oop.myworkoutbuddy.view.handlers.MainHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Main.fxml"));
        final BorderPane root = (BorderPane) loader.load();
        final Scene scene = new Scene(root);
        final MainHandler access = loader.getController();
        scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String... args) {
        launch(args);
    }

}
