package it.unibo.oop.myworkoutbuddy.model;

import java.util.ArrayList;
import java.util.List;

//import it.unibo.oop.myworkoutbuddy.model.Body.Target;
/**
 WorkoutRoutine : routine composed by an exercises list (Exercise list).

     name : name/code
     target : scope
     dayAweek : list of days in a week dedicated
     exerciseList : list of exercises
*/
public class WorkoutRoutineImpl implements WorkoutRoutine {

    private String name;
    //private Target target;
    private List<DayInWeek> dayAweek;
    private List<Exercise> exerciseList;

    /**
     * 
     * @param name String
     * @param target BodyTarget
     */
   // public WorkoutRoutineImpl(final String name, final Target target) {
   //     this.name = name;
      //  this.target = target;
   //     this.dayAweek = new ArrayList<>();
   //     this.exerciseList = new ArrayList<>();
  //  }

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
    public String getName() {
        return this.name;
    }

  //  @Override
  //  public Target getTarget() {
  //      return this.target;
  //  }

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

 //   @Override
 //   public String toString() {
  //      return "WorkoutRoutineImpl [name=" + name + " target=" + target + " dayAweek=" + dayAweek
  //              + "\n\n ExerciseList=" + this.getExerciseList() + "]";
  //  }
}
