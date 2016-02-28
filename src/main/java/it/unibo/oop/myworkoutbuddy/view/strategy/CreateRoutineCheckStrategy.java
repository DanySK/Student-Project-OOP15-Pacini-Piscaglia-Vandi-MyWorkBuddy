package it.unibo.oop.myworkoutbuddy.view.strategy;

import java.util.Optional;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * It encapsulates the check strategy of the user operable actions.
 *
 */
public interface CreateRoutineCheckStrategy {

    /**
     * 
     * @param workoutSelected
     *            represented by an optional VBox area.
     * @param exerciseSelected
     *            represented by an optional label.
     * @return true if exercise can be added, false otherwise. An exercise can
     *         be added if a workout is selected, the number of exercises in the
     *         selected workout is lower then the max exercises limit, an
     *         exercises is selected and the same exercise isn't already added
     *         in the same workout.
     */
    boolean canAddExercise(Optional<VBox> workoutSelected, Optional<Label> exerciseSelected);

    /**
     * 
     * @param workoutBox
     *            to control if there are too much workout children.
     * @return true if workout can be added. A workout can be added if the
     *         workout children number is lower than workout limit.
     */
    boolean canAddWorkout(VBox workoutBox);

    /**
     * 
     * @param workoutSelected
     *            to control.
     * @param exerciseSelected
     *            to control.
     * @return true if the exercise selected can be deleted. An exercise can be
     *         deleted if a workout is selected, an exercise is selected and the
     *         selected exercise was added previously.
     */
    boolean canDeleteExercise(Optional<VBox> workoutSelected, Optional<Label> exerciseSelected);

    /**
     * 
     * @param workoutBox
     *            to control.
     * @param workoutSelected
     *            to control.
     * @return true if the workout can be deleted. A workout can be deleted if
     *         there are workout added and a workout is selected.
     */
    boolean canDeleteWorkout(VBox workoutBox, Optional<VBox> workoutSelected);

    /**
     * 
     * @param exerciseSelected
     *            to control.
     * @return true if the exercise can be showed. An exercise can be showed if
     *         it is selected.
     */
    boolean canShowExercise(Optional<Label> exerciseSelected);

    /**
     * 
     * @param workoutSelected
     *            to control.
     * @param event
     *            performed by the mouse click in a workout box.
     * @return true if a workout is present and the workout selected previously
     *         is different from the selected one.
     */
    boolean isWorkoutToBeSet(Optional<VBox> workoutSelected, MouseEvent event);

    /**
     * 
     * @param exerciseSelected
     *            to control.
     * @param event
     *            performed by the mouse click in a exercise label.
     * @return true if the new selected exercise is different from the
     *         previously chosen one.
     */
    boolean hasExBeenChanged(Optional<Label> exerciseSelected, MouseEvent event);

    /**
     * 
     * @return true if the routine has been successfully saved.
     */
    boolean hasRoutineBeenSaved();

    /**
     * 
     * @param workoutBox
     *            to control.
     * @return true if at least one workout has been added and routine has a name.
     */
    boolean canRoutineBeenSaved(VBox workoutBox, TextField routineName);

    /**
     * 
     * @param workoutName
     *            to control.
     * @param workoutBox
     *            to control.
     * @return true if the passed workout is already added in the workout box,
     *         false otherwise.
     */
    boolean isWorkoutAlreadyAdded(String workoutName, VBox workoutBox);
}
