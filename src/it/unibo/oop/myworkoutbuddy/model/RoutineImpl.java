package it.unibo.oop.myworkoutbuddy.model;

import java.util.ArrayList;
import java.util.List;
/**
 WorkoutRoutine : routine composed by an exercises list (Exercise list).

     name : name/code
     target : scope
     dayAweek : list of days in a week dedicated
     exerciseList : list of exercises
*/
public class RoutineImpl implements Routine {

    private String code;
    private String name;
    private String target;
    private List<DayInWeek> dayAweek;
    private List<Exercise> exerciseList;

    /**
     * @param code String
     * @param name String
     * @param targetName String
     */
    public RoutineImpl(final String code, final String name, final String targetName) {
        this.code = code;
        this.name = name;
        this.target = targetName;
        this.dayAweek = new ArrayList<>();
        this.exerciseList = new ArrayList<>();
    }

    /**
     * 
     * @param day DayInWeek
     * 
     * @throws IllegalArgumentException exception for check not empty value
     */
    @Override
    public void addDay(final DayInWeek day) throws NullPointerException {
        this.checkNotNull(day);
        this.dayAweek.add(day);
    }

    /**
     * 
     * @param exercise GymExercise
     * 
     * @throws IllegalArgumentException exception for check not empty value
     */
    @Override
    public void addGymExcercise(final Exercise exercise) throws NullPointerException {

        this.checkNotNull(exercise);
        this.exerciseList.add(exercise);
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getTarget() {
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

    private void checkNotNull(final Object obj) throws NullPointerException {
        if (obj == null) {
            throw new NullPointerException();
        }
    }

    @Override
    public String toString() {
        return "RoutineImpl [name=" + this.getName() + " target=" + this.getTarget() + " dayAweek=" + this.getDayAweek()
                + "\n\n ExerciseList=" + this.getExerciseList() + "]";
    }
}
