package it.unibo.oop.myworkoutbuddy.model;

import java.util.List;

import it.unibo.oop.myworkoutbuddy.model.Body.BodyTarget;
/**
 * Training card : composed by an exercises list (Exercise list).
 * -------------------------------------------------------------
 */
 
public class WorkoutRoutineImpl implements WorkoutRoutine {

    private String name; // name/code of a Workout
    private BodyTarget target; // scope of Workout
    private List<DayInWeek> dayAweek; // list of days in a week dedicated to the Workout
    private List<Exercise> exerciseList;
    /**
     * 
     * @param name String
     * @param target BodyTarget
     */
    public WorkoutRoutineImpl(final String name, final BodyTarget target) {
        this.setName(name);
        this.setTarget(target);
    }

    private void setName(final String name) {
        this.name = name;
    }

    private void setTarget(final BodyTarget target) {
        this.target = target;
    }
    /**
     * 
     * @param day DayInWeek
     */
    public void addDay(final DayInWeek day) {
        this.dayAweek.add(day);
    }
    /**
     * 
     * @param exercise GymExercise
     */
    public void addGymExcercise(final Exercise exercise) {
        this.exerciseList.add(exercise);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return this.name;
    }

    @Override
    public BodyTarget getTarget() {
        // TODO Auto-generated method stub
        return this.target;
    }

    @Override
    public List<DayInWeek> getDayAweek() {
        // TODO Auto-generated method stub
        return this.dayAweek;
    }

    @Override
    public List<Exercise> getExerciseList() {
        // TODO Auto-generated method stub
        return this.exerciseList;
    }
}
