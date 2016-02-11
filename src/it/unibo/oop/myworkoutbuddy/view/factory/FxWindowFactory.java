package it.unibo.oop.myworkoutbuddy.view.factory;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Utility class to create JavaFx windows using pattern Static Factory.
 */
public final class FxWindowFactory {

    private static String cssSheetPath = "original.css";

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
            final FXMLLoader loader = new FXMLLoader(FxWindowFactory.class.getResource("../structure/" + fxmlPath));
            final BorderPane root = (BorderPane) loader.load();
            if (isContained) {
                return root;
            }
            final Stage stage = new Stage();
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(FxWindowFactory.class.getResource(cssSheetPath).toExternalForm());
            stage.setTitle("MyWorkoutBuddy");
            stage.getIcons().add(new Image("file:res/it/unibo/oop/myworkoutbuddy/view/icons/workoutIcon.png"));
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

}
