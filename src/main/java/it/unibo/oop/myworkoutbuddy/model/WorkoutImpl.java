package it.unibo.oop.myworkoutbuddy.model;

import java.util.ArrayList;
import java.util.List;
/**
 Workout : routine composed by an exercises list (Exercise list).

     name : name/code
     target : scope
     dayAweek : list of days in a week dedicated
     exerciseList : list of exercises
*/
public class WorkoutImpl implements Workout {

    private final String code;
    private final String name;
    private final String target;
    private final List<Exercise> exerciseList;

    /**
     * @param code String
     * @param name String
     * @param targetName String
     */
    public WorkoutImpl(final String code, final String name, final String targetName) {
        this.code = code;
        this.name = name;
        this.target = targetName;
        this.exerciseList = new ArrayList<>();
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
    public List<Exercise> getExerciseList() {
        return this.exerciseList;
    }

    @Override
    public void addGymExcercise(final Exercise exercise) {
        this.exerciseList.add(exercise);
    }

    @Override
    public String toString() {
        return "WorkoutImpl [name=" + this.getName() + " target=" + this.getTarget()
                + "\n\n ExerciseList=" + this.getExerciseList() + "]";
    }
}
