package it.unibo.oop.myworkoutbuddy.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * Utility class to open JavaFx windows.
 *
 */
public final class FxStageWindow {

    /**
     * Load a new window. If inside is set, the method return the root of the new scene.
     * 
     * @param fxmlPath
     * @param cssPath
     */
    public BorderPane openWindow(final String fxmlPath, final String cssPath, boolean inside) {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            final Stage stage = new Stage();
            final BorderPane root = (BorderPane) loader.load();
            final Scene scene = new Scene(root);
            // final AccessHandler access = loader.getController();
            if (inside) {
                return root;
            }
            scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
