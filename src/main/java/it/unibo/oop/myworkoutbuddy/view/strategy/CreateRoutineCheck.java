package it.unibo.oop.myworkoutbuddy.view.strategy;

import static it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory.showDialog;
import static it.unibo.oop.myworkoutbuddy.view.handlers.ViewHandler.getObserver;

import java.util.Optional;

import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Implementation of the check of the possible actions performed by user when it
 * creates a routine.
 *
 */
public final class CreateRoutineCheck implements CreateRoutineCheckStrategy {

    private static final int MAX_WORKOUTS = 7;

    private static final int MAX_EXERCISES = 6;

    @Override
    public boolean canAddExercise(final Optional<VBox> workoutSelected, final Optional<Label> exerciseSelected) {
        if (workoutSelected.isPresent() && childrenCount(workoutSelected.get()) < MAX_EXERCISES
                && exerciseSelected.isPresent()
                && !isExAlreadyInserted(exerciseSelected.get().getText(), workoutSelected)) {
            return true;

        } else if (!workoutSelected.isPresent()) {
            showDialog("Error adding workout", "You have to had a workout first!", Optional.empty(), AlertType.ERROR);

        } else if (childrenCount(workoutSelected.get()) >= MAX_EXERCISES) {
            showDialog("Limit reached", "Max addable exercises limit reached", Optional.empty(), AlertType.ERROR);
        } else if (!exerciseSelected.isPresent()) {
            showDialog("No exercise selected", "Please select an exercise from the list", Optional.empty(),
                    AlertType.ERROR);
        } else if (isExAlreadyInserted(exerciseSelected.get().getText(), workoutSelected)) {
            showDialog("Exercise is already added", "You can't add a same exercise twice in the same workout",
                    Optional.empty(), AlertType.ERROR);
        }
        return false;
    }

    @Override
    public boolean canAddWorkout(final VBox workoutBox) {
        if (childrenCount(workoutBox) < MAX_WORKOUTS) {
            return true;
        }
        if (childrenCount(workoutBox) == MAX_WORKOUTS) {
            showDialog("Limit reached", "Max addable workouts limit reached", Optional.empty(), AlertType.ERROR);
        }
        return false;
    }

    @Override
    public boolean canDeleteExercise(final Optional<VBox> workoutSelected, final Optional<Label> exerciseSelected) {

        if (workoutSelected.isPresent() && exerciseSelected.isPresent()
                && isExAlreadyInserted(exerciseSelected.get().getText(), workoutSelected)) {
            return true;
        }

        if (!workoutSelected.isPresent()) {
            showDialog("Error deleting exercise", "You haven't selected any workout", Optional.empty(),
                    AlertType.ERROR);
        } else if (!exerciseSelected.isPresent()
                || isExAlreadyInserted(exerciseSelected.get().getText(), workoutSelected)) {
            showDialog("Error", "You have to select an exercise first!", Optional.empty(), AlertType.ERROR);
        }
        return false;
    }

    @Override
    public boolean canDeleteWorkout(final VBox workoutBox, final Optional<VBox> workoutSelected) {

        // the double call to getParent() select first VBox -> AnchorPane->
        // and then the wanted AnchorPane -> TitlePane to remove

        if (childrenCount(workoutBox) > 0 && workoutSelected.isPresent()) {
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
    public boolean isWorkoutToBeSet(final Optional<VBox> workoutSelected, final MouseEvent event) {
        return (workoutSelected.isPresent() && workoutSelected.get() != event.getSource())
                || !workoutSelected.isPresent();
    }

    @Override
    public boolean hasExBeenChanged(final Optional<Label> exerciseSelected, final MouseEvent event) {
        return exerciseSelected.isPresent() && exerciseSelected.get() != event.getSource();
    }

    @Override
    public boolean hasRoutineBeenSaved() {
        if (getObserver().saveRoutine()) {
            showDialog("Routine saved!", "Your routine has been saved!", Optional.empty(), AlertType.INFORMATION);
            return true;
        } else {
            showDialog("Error saving routine", "You have inserted wrong data! Remember to insert positive numbers.",
                    Optional.empty(), AlertType.ERROR);
        }
        return false;
    }

    @Override
    public boolean canRoutineBeenSaved(final VBox workoutBox, final TextField routineName) {
        if (!workoutBox.getChildren().isEmpty() && !routineName.getText().isEmpty()) {
            return true;
        } else if (workoutBox.getChildren().isEmpty()) {
            showDialog("Error saving routine", "You can't save an empty routine", Optional.empty(), AlertType.ERROR);
        } else if (routineName.getText().isEmpty()) {
            showDialog("Error saving routine", "You have to insert a routine name", Optional.empty(), AlertType.ERROR);
        }
        return false;
    }

    @Override
    public boolean isWorkoutAlreadyAdded(final String workoutName, final VBox workoutBox) {
        if (workoutBox.getChildren().stream().map(work -> (TitledPane) work)
                .anyMatch(work -> work.getText().equals(workoutName))) {
            showDialog("Please select another name", "A workout with the same is already added!", Optional.empty(),
                    AlertType.ERROR);
            return true;
        }
        return false;
    }

    private boolean isExAlreadyInserted(final String exercise, final Optional<VBox> workoutSelected) {
        return workoutSelected.get().getChildren().stream().map(exBox -> (HBox) exBox)
                .map(ex -> (Label) ex.getChildren().get(0)).map(exLabel -> exLabel.getText())
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
