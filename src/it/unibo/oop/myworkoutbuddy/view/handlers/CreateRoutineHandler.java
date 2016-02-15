package it.unibo.oop.myworkoutbuddy.view.handlers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unibo.oop.myworkoutbuddy.util.MutableTriple;
import it.unibo.oop.myworkoutbuddy.util.Triple;
import it.unibo.oop.myworkoutbuddy.view.CreateRoutineView;
import it.unibo.oop.myworkoutbuddy.view.ViewsObserver;
import it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
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

    private Optional<VBox> workoutSelected = Optional.empty();

    private Optional<Label> exerciseSelected = Optional.empty();

    private final EventHandler<MouseEvent> selectWorkoutHandler = i -> {
        if (workoutSelected.get() != i.getSource()) {
            workoutSelected = Optional.of((VBox) i.getSource());
        }
    };

    private final EventHandler<MouseEvent> selectExerciseHandler = i -> {

        /*
         * if (exerciseSelected.isPresent()) {
         * 
         * if (exerciseSelected.get() == i.getSource()) {
         * exerciseSelected.get().setStyle(null);
         * exerciseSelected = Optional.empty();
         * } else {
         * if (exerciseSelected.get() != i.getSource()) {
         * exerciseSelected.get().setStyle(null);
         * }
         * exerciseSelected = Optional.of((Label) i.getSource());
         * exerciseSelected.get().setStyle("-fx-font-weight:bold");
         * }
         * }
         */

        if (!exerciseSelected.isPresent()) {
            exerciseSelected = Optional.of((Label) i.getSource());
            exerciseSelected.get().setStyle("-fx-font-weight:bold");
        } else {
            if (exerciseSelected.get() != i.getSource()) {
                exerciseSelected.get().setStyle(null);
                exerciseSelected = Optional.of((Label) i.getSource());
                exerciseSelected.get().setStyle("-fx-font-weight:bold");

            } else if (exerciseSelected.get() == i.getSource()) {
                exerciseSelected.get().setStyle(null);
                exerciseSelected = Optional.empty();
            }
        }
    };

    private ViewsObserver observer = ViewsHandler.getObserver();

    @FXML
    private void saveRoutine() {
        // observer.saveRoutine();
        FxWindowFactory.showDialog("Routine saved!", "Your routine has been saved!", Optional.empty());
    }

    @FXML
    private void addWorkout() {
        if (createdWorkouts < MAX_WORKOUTS) {
            workoutSelected = Optional.of(new VBox());
            final TitledPane newWorkout = new TitledPane("Workout " + lastLetter, workoutSelected.get());
            workoutSelected.get().addEventHandler(MouseEvent.MOUSE_CLICKED, selectWorkoutHandler);
            workoutBox.getChildren().add(newWorkout);
            lastLetter += 1;
            createdWorkouts++;
        }
        if (createdWorkouts == MAX_WORKOUTS) {
            btnAddWorkout.setDisable(true);
            FxWindowFactory.showDialog("Limit reached", "Max addable workouts limit reached", Optional.empty());
        }
        // else {
        // FxWindowFactory.showDialog("Error", "Max creatable workouts limit
        // reached");
        // }

    }

    @FXML
    private void addExercise() {
        if (workoutSelected.isPresent() && childrenCount(workoutSelected.get()) < MAX_EXERCISES) {
            final Label newExercise = new Label("exercise");
            newExercise.addEventHandler(MouseEvent.MOUSE_CLICKED, selectExerciseHandler);
            workoutSelected.get().getChildren().add(newExercise);
        } else if (!workoutSelected.isPresent()) {
            FxWindowFactory.showDialog("Error", "You have to had a workout first!", Optional.empty());
        } else if (childrenCount(workoutSelected.get()) >= MAX_EXERCISES) {
            FxWindowFactory.showDialog("Limit reached", "Max addable exercises limit reached", Optional.empty());
        }
    }

    @FXML
    private void showExercise() {
        FxWindowFactory.showDialog("Exercise", "Exercise description", Optional
                .of("http://workouts.menshealth.com/sites/workouts.menshealth.com/files/back-and-biceps-builder.jpg"));
    }

    @FXML
    private void deleteWorkout() {

        // the double call to getParent() select first TitlePane->AnchorPane
        // and then the wanted AnchorPane->VBox to remove

        if (createdWorkouts > 0 && workoutSelected.isPresent()) {
            workoutBox.getChildren().remove(workoutSelected.get().getParent().getParent());
            createdWorkouts--;
            if (btnAddWorkout.isDisabled()) {
                btnAddWorkout.setDisable(false);
            }
        } else if (createdWorkouts == 0) {
            FxWindowFactory.showDialog("Error", "There aren't workouts added!", Optional.empty());
        } else if (!workoutSelected.isPresent()) {
            FxWindowFactory.showDialog("Error", "No workout selected!", Optional.empty());
        }
    }

    @FXML
    private void deleteExercise() {
        if (workoutSelected.isPresent() && exerciseSelected.isPresent()) {
            workoutSelected.get().getChildren().remove(exerciseSelected.get());
        }

        if (!exerciseSelected.isPresent()) {
            FxWindowFactory.showDialog("Error", "You have to select an exercise first!", Optional.empty());
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

    private int childrenCount(final Parent parent) {
        return (int) parent.getChildrenUnmodifiable().stream().count();
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     */
    public void initialize() {

    }

}
