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
    private TrainingCard card;  // training card used
    private List<Integer> scoreList;    // list of got scores with card in session

    /**
     * 
     * @param date LocalDate
     * @param time LocalTime
     * @param card TrainingCard
     */
    public TrainingImpl(final LocalDate date, final LocalTime time, final TrainingCard card) {
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

    private void setCard(final TrainingCard card) {
        this.card = card;
    }
    /**
     * 
     * @param scoreTraining Integer
     */
    public void addScore(final Integer scoreTraining) {
        this.scoreList.add(scoreTraining);
    }

    @Override
    public LocalDate getData() {
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
    public TrainingCard getCard() {
        // TODO Auto-generated method stub
        return this.card;
    }

}
