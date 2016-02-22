package it.unibo.oop.myworkoutbuddy.view.handlers;

import static it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory.showDialog;
import static it.unibo.oop.myworkoutbuddy.view.handlers.ViewsHandler.getObserver;

import java.util.Optional;

import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 *
 */
public class CreateRoutineCheck implements CreateRoutineCheckStrategy {

    private static final int MAX_WORKOUTS = 4;

    private static final int MAX_EXERCISES = 6;

    @Override
    public boolean canAddExercise(final Optional<VBox> workoutSelected, final Optional<Label> exerciseSelected,
            final Button btnAddExercise) {
        if (workoutSelected.isPresent() && childrenCount(workoutSelected.get()) < MAX_EXERCISES
                && exerciseSelected.isPresent()
                && !isAlreadyInserted(exerciseSelected.get().getText(), workoutSelected)) {
            return true;

        } else if (!workoutSelected.isPresent()) {
            showDialog("Error adding workout", "You have to had a workout first!", Optional.empty(),
                    AlertType.ERROR);

        } else if (childrenCount(workoutSelected.get()) >= MAX_EXERCISES) {
            btnAddExercise.setDisable(true);
            showDialog("Limit reached", "Max addable exercises limit reached", Optional.empty(),
                    AlertType.ERROR);
        } else if (!exerciseSelected.isPresent()) {
            showDialog("No exercise selected", "Please select an exercise from the list", Optional.empty(),
                    AlertType.ERROR);
        } else if (isAlreadyInserted(exerciseSelected.get().getText(), workoutSelected)) {
            showDialog("Exercise is already added", "You can't add a same exercise twice in the same workout",
                    Optional.empty(), AlertType.ERROR);
        }
        return false;
    }

    @Override
    public boolean canAddWorkout(final VBox workoutBox, final Button btnAddWorkout) {
        if (childrenCount(workoutBox) < MAX_WORKOUTS) {
            return true;
        }
        if (childrenCount(workoutBox) == MAX_WORKOUTS) {
            btnAddWorkout.setDisable(true);
            showDialog("Limit reached", "Max addable workouts limit reached", Optional.empty(),
                    AlertType.ERROR);
        }
        return false;
    }

    @Override
    public boolean canDeleteExercise(final Optional<VBox> workoutSelected, final Optional<Label> exerciseSelected,
            final Button btnAddExercise) {

        if (!exerciseSelected.isPresent()) {
            showDialog("Error", "You have to select an exercise first!", Optional.empty(),
                    AlertType.ERROR);
        }
        if (workoutSelected.isPresent() && exerciseSelected.isPresent()) {
            btnAddExercise.setDisable(!btnAddExercise.isDisabled());
            return true;

        }
        return false;
    }

    @Override
    public boolean canDeleteWorkout(final VBox workoutBox, final Optional<VBox> workoutSelected,
            final Button btnAddWorkout) {

        // the double call to getParent() select first VBox -> AnchorPane->
        // and then the wanted AnchorPane -> TitlePane to remove

        if (childrenCount(workoutBox) > 0 && workoutSelected.isPresent()) {
            btnAddWorkout.setDisable(false);
            return true;

        } else if (childrenCount(workoutBox) == 0) {
            showDialog("Error", "There aren't workouts added!", Optional.empty(), AlertType.ERROR);

        } else if (!workoutSelected.isPresent()) {
            showDialog("Error", "No workout selected!", Optional.empty(), AlertType.ERROR);
        }
        return false;
    }

    @Override
    public boolean canShowExercise(final Optional<Label> exerciseSelected) {
        if (exerciseSelected.isPresent()) {
            return true;
        } else {
            showDialog("No exercise selected", "Please select an exercise to show from the list", Optional.empty(),
                    AlertType.ERROR);
        }
        return false;
    }

    @Override
    public boolean isWorkoutToBeSet(final Optional<VBox> workoutSelected, final Button btnAddExercise,
            final MouseEvent event) {
        if ((workoutSelected.isPresent() && workoutSelected.get() != event.getSource())
                || !workoutSelected.isPresent()) {
            return true;
        }
        btnAddExercise.setDisable(childrenCount(workoutSelected.get()) == MAX_EXERCISES);
        return false;
    }

    @Override
    public boolean hasExBeenChanged(final Optional<Label> exerciseSelected, final MouseEvent event) {
        return exerciseSelected.isPresent() && exerciseSelected.get() != event.getSource();
    }

    @Override
    public boolean hasRoutineBeenSaved() {
        if (getObserver().saveRoutine()) {
            return true;
        } else {
            showDialog("Error saving routine", "You have inserted wrong data!", Optional.empty(), AlertType.ERROR);
        }
        return false;
    }

    private boolean isAlreadyInserted(final String exercise, final Optional<VBox> workoutSelected) {
        return workoutSelected.get().getChildren().stream()
                .map(exBox -> (HBox) exBox)
                .map(ex -> (Label) ex.getChildren().get(0))
                .map(exLabel -> exLabel.getText())
                .anyMatch(exName -> exName.equals(exercise));
    }

    /**
     * 
     * @param parent
     *            node.
     * @return the number of children.
     */
    private int childrenCount(final Parent parent) {
        return parent.getChildrenUnmodifiable().size();
    }

}
