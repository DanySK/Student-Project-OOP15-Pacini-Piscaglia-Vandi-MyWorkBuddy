package it.unibo.oop.myworkoutbuddy.view.factory;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * Utility class to create JavaFx windows using pattern SimpleFactory.
 *
 */
public final class FxWindowFactory {

    private static String cssStyle;

    /**
     * Set window style.
     */
    public FxWindowFactory() {
        cssStyle = StyleSingleton.getCssStyle();
    }

    /**
     * Load a new window. If inside is set, the method return the root of the
     * new scene.
     * 
     * @param fxmlPath.
     * 
     * @param inside.
     * 
     * @return root.
     */
    public BorderPane openWindow(final String fxmlPath, final boolean inside) {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("../structure/" + fxmlPath));
            final BorderPane root = (BorderPane) loader.load();
            // final AccessHandler access = loader.getController();
            if (inside) {
                return root;
            }
            final Stage stage = new Stage();
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(cssStyle).toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
