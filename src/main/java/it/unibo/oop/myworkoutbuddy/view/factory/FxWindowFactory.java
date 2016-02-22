package it.unibo.oop.myworkoutbuddy.view.factory;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
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
            scene.getStylesheets().add(FxWindowFactory.class.getResource(cssSheetPath).toExternalForm());
            stage.setTitle("MyWorkoutBuddy");
            stage.getIcons().add(new Image("http://clementbatifoulier.com/here/wp-content/uploads/Icon-Workout.png"));
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
     * @param alertType
     *            to select the type of dialog.
     */
    public static void showDialog(final String title, final String message, final Optional<String> imagePath,
            final AlertType alertType) {
        final Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        imagePath.ifPresent(i -> {
            final ImageView imageView = new ImageView(new Image(i));
            alert.setGraphic(imageView);
        });
        alert.showAndWait();
    }

    /**
     * 
     * @param title
     *            of dialog window.
     * @param message
     *            to user.
     * @param inputText
     *            to show in input text field.
     * @return input string
     *         written by user.
     */
    public static String createInputDialog(final String title, final String message, final String inputText) {
        final TextInputDialog dialog = new TextInputDialog(inputText);
        dialog.setTitle(title);
        dialog.setHeaderText("You have to input the requested data!");
        dialog.setContentText(message);

        final Optional<String> result = dialog.showAndWait();
        String input = "";
        if (result.isPresent()) {
            input = result.get();
        } else {
            createInputDialog(title, message, inputText);
        }
        return input;
    }

}
