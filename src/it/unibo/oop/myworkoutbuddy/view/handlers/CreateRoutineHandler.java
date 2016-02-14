package it.unibo.oop.myworkoutbuddy.view.handlers;

import java.util.List;
import java.util.Map;

import it.unibo.oop.myworkoutbuddy.util.MutableTriple;
import it.unibo.oop.myworkoutbuddy.util.Triple;
import it.unibo.oop.myworkoutbuddy.view.CreateRoutineView;
import it.unibo.oop.myworkoutbuddy.view.ViewsObserver;
import it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * 
 * Handler of the CreateRoutineView. It collects user routine data.
 */
public final class CreateRoutineHandler implements CreateRoutineView {

    @FXML
    private Button btnAddWorkout;

    @FXML
    private Button btnAddExercise;

    @FXML
    private TabPane exercisePane;

    @FXML
    private VBox workoutBox;

    @FXML
    private TextField txtDescription;

    private char lastLetter = 'A';

    private int createdWorkouts = 0;

    private static final int MAX_WORKOUTS = 4;

    private static final int MAX_EXERCISES = 6;

    private VBox lastAnchor;

    private final EventHandler<MouseEvent> selectHandler = i -> {
        lastAnchor = (VBox) i.getSource();
    };

    private ViewsObserver observer = ViewsHandler.getObserver();

    @FXML
    private void saveRoutine() {
        // observer.saveRoutine();
        FxWindowFactory.showDialog("Routine saved!", "Your routine has been saved!");
    }

    @FXML
    private void addWorkout() {
        if (createdWorkouts < MAX_WORKOUTS) {
            lastAnchor = new VBox();
            final TitledPane newWorkout = new TitledPane("Workout " + lastLetter, lastAnchor);
            lastAnchor.addEventHandler(MouseEvent.MOUSE_CLICKED, selectHandler);
            workoutBox.getChildren().add(newWorkout);
            lastLetter += 1;
            createdWorkouts++;
            if (createdWorkouts == MAX_WORKOUTS) {
                btnAddWorkout.setDisable(true);
                FxWindowFactory.showDialog("Limit reached", "Max creatable workouts limit reached");
            }
        } // else {
          // FxWindowFactory.showDialog("Error", "Max creatable workouts limit
          // reached");
          // }
    }

    @FXML
    private void addExercise() {
        if (lastAnchor != null) {
            lastAnchor.getChildren().add(new Label("exercise"));
        }
    }

    @Override
    public Triple<Integer, String, Map<String, Map<String, List<Integer>>>> getRoutine() {

        final int routineIndex = 0;
        final String routineDescription = txtDescription.getText();
        final Map<String, List<Integer>> exercises;
        final Map<String, Map<String, List<Integer>>> workouts = null;

        return new MutableTriple<>(routineIndex, routineDescription, workouts);
    }

}
