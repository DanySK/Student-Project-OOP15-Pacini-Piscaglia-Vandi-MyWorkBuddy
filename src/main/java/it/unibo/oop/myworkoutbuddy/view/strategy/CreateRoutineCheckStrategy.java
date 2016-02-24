package it.unibo.oop.myworkoutbuddy.view.strategy;

import java.util.Optional;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * 
 *
 */
public interface CreateRoutineCheckStrategy {

    /**
     * 
     * @param workoutSelected
     * @param exerciseSelected
     * @return true if exercise can be added, false otherwise.
     */
    boolean canAddExercise(Optional<VBox> workoutSelected, Optional<Label> exerciseSelected);

    /**
     * 
     * @param workoutBox
     * @return
     */
    boolean canAddWorkout(VBox workoutBox);

    /**
     * 
     * @param workoutSelected
     * @param exerciseSelected
     * @return
     */
    boolean canDeleteExercise(Optional<VBox> workoutSelected, Optional<Label> exerciseSelected);

    /**
     * 
     * @param workoutBox
     * @param workoutSelected
     * @return
     */
    boolean canDeleteWorkout(VBox workoutBox, Optional<VBox> workoutSelected);

    /**
     * 
     * @param exerciseSelected
     * @return
     */
    boolean canShowExercise(Optional<Label> exerciseSelected);

    /**
     * 
     * @param workoutSelected
     * @param event
     * @return
     */
    boolean isWorkoutToBeSet(Optional<VBox> workoutSelected, MouseEvent event);

    /**
     * 
     * @param exerciseSelected
     * @return
     */
    boolean hasExBeenChanged(Optional<Label> exerciseSelected, MouseEvent event);

    /**
     * 
     * @return
     */
    boolean hasRoutineBeenSaved();

    /**
     * 
     * @param workoutBox
     * @return
     */
    boolean canRoutineBeenSaved(VBox workoutBox);

    /**
     * 
     * @param workoutName
     * @param workoutBox
     * @return
     */
    boolean isWorkoutAlreadyAdded(String workoutName, VBox workoutBox);
}
