package it.unibo.oop.myworkoutbuddy.model;

import java.util.ArrayList;
import java.util.List;

import it.unibo.oop.myworkoutbuddy.model.Body.Target;
/**
 * Training card : composed by an exercises list (Exercise list).
 * -------------------------------------------------------------
 */
 
public class WorkoutRoutineImpl implements WorkoutRoutine {

    private String name; // name/code of a Workout
    private Target target; // scope of Workout
    private List<DayInWeek> dayAweek; // list of days in a week dedicated to the Workout
    private List<Exercise> exerciseList;

    /**
     * 
     * @param name String
     * @param target BodyTarget
     */
    public WorkoutRoutineImpl(final String name, final Target target) {
        this.name = name;
        this.target = target;
        this.dayAweek = new ArrayList<>();
        this.exerciseList = new ArrayList<>();
    }

    /**
     * 
     * @param day DayInWeek
     */
    @Override
    public void addDay(final DayInWeek day) {
        this.dayAweek.add(day);
    }
    /**
     * 
     * @param exercise GymExercise
     */
    @Override
    public void addGymExcercise(final Exercise exercise) {
        this.exerciseList.add(exercise);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Target getTarget() {
        return this.target;
    }

    @Override
    public List<DayInWeek> getDayAweek() {
        return this.dayAweek;
    }

    @Override
    public List<Exercise> getExerciseList() {
        return this.exerciseList;
    }
    /**
     * temporanea funazione x stampa indicizzata.
     * @return
     */
    @Override
    public String getNumExercise() {
        String returnNumString = "";

        /*
         * obiettivo terziario : usare metodo append x efficienza to String
         */
        this.exerciseList.forEach(i-> {
            final String temp = "" + this.exerciseList.indexOf(i) + "" + i.toString() + " ";
            returnNumString.concat(temp);
        });

        return returnNumString;
    }

    @Override
    public String toString() {
        return "WorkoutRoutineImpl [name=" + name + " target=" + target + " dayAweek=" + dayAweek
                + "\n\n ExerciseList=" + this.getExerciseList() + "]";
    }
}
