package it.unibo.oop.myworkoutbuddy.view.factory;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Utility class to create JavaFx windows using pattern Static Factory.
 */
public final class FxWindowFactory {

    private static String cssSheetPath = "original.css";

    private static FXMLLoader loader;

    private FxWindowFactory() {
    }

    /**
     * 
     * @return reference to view handler.
     * @param <T>
     *            type of the handler
     */
    public static <T> T getHandler() {
        return loader == null
                ? null
                : loader.getController();
    }

    /**
     * 
     * @param sheetPath
     *            set the path of cssSheet.
     */
    public static void setCssStyle(final String sheetPath) {
        cssSheetPath = sheetPath;
    }

    /**
     * Load a new window. If it is contained in a menu, the method return the
     * root
     * of the new scene.
     * 
     * @param fxmlPath
     *            path of the GUI structure file FXML.
     * 
     * @param isContained
     *            true if the window is set in a other container.
     * 
     * @return root.
     */
    public static BorderPane openWindow(final String fxmlPath, final boolean isContained) {
        try {
            loader = new FXMLLoader(FxWindowFactory.class.getResource(fxmlPath));
            final BorderPane root = (BorderPane) loader.load();
            if (isContained) {
                return root;
            }
            final Stage stage = new Stage();
            final Scene scene = new Scene(root);
            stage.setResizable(false);
            scene.getStylesheets().add(FxWindowFactory.class.getResource(cssSheetPath).toExternalForm());
            stage.setTitle("MyWorkoutBuddy");
            stage.getIcons().add(new Image("/it/unibo/oop/myworkoutbuddy/view/icons/workoutIcon.png"));
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Close a JavaFx window.
     * 
     * @param sceneToClose
     *            link to the window to close.
     */
    public static void closeWindow(final Scene sceneToClose) {
        final Stage sceneStage = (Stage) sceneToClose.getWindow();
        sceneStage.close();
    }

    /**
     * Replace a old window with a new one.
     * 
     * @param fxmlPath
     *            path of the GUI structure file FXML to open.
     * 
     * @param sceneToClose
     *            link to the window to close.
     */
    public static void replaceWindow(final String fxmlPath, final Scene sceneToClose) {
        FxWindowFactory.openWindow(fxmlPath, false);
        FxWindowFactory.closeWindow(sceneToClose);
    }

    /**
     * Show a simple info dialog with a optional image.
     * 
     * @param title
     *            header of the show dialog.
     * @param message
     *            content of the dialog.
     * @param imagePath
     *            to load the image.
     */
    public static void showDialog(final String title, final String message, final Optional<String> imagePath) {
        final Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        if (imagePath.isPresent()) {
            final ImageView imageView = new ImageView(new Image(imagePath.get()));
            alert.setGraphic(imageView);
        }
        alert.showAndWait();
    }

}
