package it.unibo.oop.myworkoutbuddy.view.handlers;

import java.util.Optional;

import javafx.scene.control.Button;
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
     * @return
     */
    boolean canAddExercise(Optional<VBox> workoutSelected, Optional<Label> exerciseSelected, Button btnAddExercise);

    /**
     * 
     * @param workoutBox
     * @return
     */
    boolean canAddWorkout(VBox workoutBox, Button btnAddWorkout);

    /**
     * 
     * @param workoutSelected
     * @param exerciseSelected
     * @return
     */
    boolean canDeleteExercise(Optional<VBox> workoutSelected, Optional<Label> exerciseSelected, Button btnAddExercise);

    /**
     * 
     * @param workoutBox
     * @param workoutSelected
     * @param btnAddWorkout
     * @return
     */
    boolean canDeleteWorkout(VBox workoutBox, Optional<VBox> workoutSelected, Button btnAddWorkout);

    /**
     * 
     * @param exerciseSelected
     * @return
     */
    boolean canShowExercise(Optional<Label> exerciseSelected);
    
    /**
     * 
     * @param workoutSelected
     * @return
     */
    boolean isWorkoutToBeSet(Optional<VBox> workoutSelected, Button btnAddExercise, MouseEvent event);

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
}
