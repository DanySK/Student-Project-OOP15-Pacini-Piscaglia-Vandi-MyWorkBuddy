package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * User's data of a single training session. (WorkOut)
 * -------------------------------------------------------------
 */
 
public class WorkoutImpl implements Workout {
    private LocalDate date;     // session date
    private LocalTime time;  // hour of start session
    private boolean state;  // session state (done/to do)
    private WorkoutRoutine card;  // training card used
    private List<Integer> scoreList;    // list of got scores with card in session

    /**
     * 
     * @param date LocalDate
     * @param time LocalTime
     * @param card WorkoutRoutine
     */
    public WorkoutImpl(final LocalDate date, final LocalTime time, final WorkoutRoutine card) {
        this.setDate(date);
        this.setTime(time);
        this.setCard(card);
        this.scoreList = new ArrayList<>();
    }

    private void setDate(final LocalDate date) {
        this.date = date;
    }

    private void setTime(final LocalTime time) {
        this.time = time;
    }

    /**
     * 
     * @param state boolean
     */
    public void setState(final boolean state) {
        this.state = state;
    }

    private void setCard(final WorkoutRoutine card) {
        this.card = card;
    }
    /**
     * 
     * @param scoreTraining Integer
     */
    @Override
    public void addScore(final int indExercise, final Integer scoreTraining) {
        this.scoreList.add(indExercise, scoreTraining);
    }

    @Override
    public LocalDate getDate() {
        // TODO Auto-generated method stub
        return this.date;
    }

    @Override
    public LocalTime getTime() {
        return this.time;
    }

    @Override
    public boolean getState() {
        // TODO Auto-generated method stub
        return this.state;
    }

    @Override
    public List<Integer> getScoreList() {
        // TODO Auto-generated method stub
        return this.scoreList;
    }

    @Override
    public WorkoutRoutine getCard() {
        // TODO Auto-generated method stub
        return this.card;
    }

}
