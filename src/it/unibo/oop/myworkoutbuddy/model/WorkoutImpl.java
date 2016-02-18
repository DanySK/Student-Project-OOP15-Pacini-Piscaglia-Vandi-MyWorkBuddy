package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import it.unibo.oop.myworkoutbuddy.model.Body.BodyPart;

/**
 * 
 * User's data of a single training session. (WorkOut)
 * -------------------------------------------------------------
 */
 
public class WorkoutImpl implements Workout {

    private static final int PERCENTAGE = 100;

    private LocalDate date;     // session date
    private LocalTime time;  // hour of start session
    private boolean state;  // session state (done/to do)
    private WorkoutRoutine routine;  // workout routine used
    private List<Integer> scoreList;    // list of got scores with card in session

    /**
     * 
     * @param date LocalDate
     * @param time LocalTime
     * @param routine WorkoutRoutine
     */
    public WorkoutImpl(final LocalDate date, final LocalTime time, final WorkoutRoutine routine) {
        this.date = date;
        this.time = time;
        this.state = false;
        this.routine = routine;
        this.scoreList = new ArrayList<>();
    }

    /**
     * 
     * @param state boolean
     */
    @Override
    public void modifyState(final boolean state) {
        this.state = state;
    }

    /**
     * 
     * @param score Integer
     */
    @Override
    public void addScore(final int indExercise, final Integer score) {
        final GymTool temp = this.routine.getExerciseList().get(indExercise).getGymTool();
        final Integer min = temp.getMinValue();
        final Integer max = temp.getMaxValue();
        // check score to be in : [min max]
        final Integer newScore = (score < min) ? min : (score > max) ? max : score;

        this.scoreList.add(indExercise, newScore);
    }

    @Override
    public LocalDate getDate() {
        return this.date;
    }

    @Override
    public LocalTime getTime() {
        return this.time;
    }

    @Override
    public boolean getState() {
        return this.state;
    }

    @Override
    public List<Integer> getScoreList() {
        return this.scoreList;
    }

    @Override
    public WorkoutRoutine getRoutine() {
        return this.routine;
    }

    /**
     * return the average value of all scores got in the workout routines.
     * @return
     */
    @Override
    public Double getWorkoutScore() {
        final List<Double> normalizedList = new ArrayList<>();
        int index = 0;

        for (Exercise exr : routine.getExerciseList()) {
            final Double score = exr.getNormalizedScore((double) this.getScoreList().get(index++));
            normalizedList.add(score);
        }

        return normalizedList.stream().mapToDouble(i->i.doubleValue()).average().getAsDouble();
    }

    /**
     * return a map <BodyPart, Score> of all scores got by each body part(muscle).
     */
    @Override
    public Map<BodyPart, Double> getPercentuageParts() {
        final Map<BodyPart, Double> scoreMap = new HashMap<>();
        final Map<BodyPart, Integer> timesMap = new HashMap<>();
        int index = 0;

        for (Exercise exr : routine.getExerciseList()) {
            final Double score = exr.getNormalizedScore((double) this.getScoreList().get(index++));
            final Map<BodyPart, Double> percentageMap = exr.getGymTool().getBodyMap();

            this.percentageMapping(scoreMap, percentageMap, score);
            this.countMap(timesMap, percentageMap);
        }

        this.midMap(scoreMap, timesMap);

        return scoreMap;
    }

    /**
     * return a map of workTime for each single body part(muscle).
     * @return timeMap
     */
    @Override
    public Map<BodyPart, Double> getTimeParts() {
        final Map<BodyPart, Double> timeMap = new HashMap<>();
        this.routine.getExerciseList().forEach(i -> {
            final Double minutes = this.timeExercise(i);
            final Map<BodyPart, Double> percentageMap = i.getGymTool().getBodyMap();

            this.percentageMapping(timeMap, percentageMap, minutes);
        });

        return timeMap;
    }

    @Override
    public Map<String, Double> getTimeTools() {
        final Map<String, Double> timeMap = new HashMap<>();
        this.routine.getExerciseList().forEach(i -> {
            final Double minutes = this.timeExercise(i);
            final String code = i.getGymTool().getCode();

            this.mergeMap(timeMap, code, minutes, (d1, d2) -> {
                return timeMap.get(code) + minutes;
            });
        });

        return timeMap;
    }

    private void percentageMapping(final Map<BodyPart, Double> valueMap, final Map<BodyPart, Double> percentageMap, final Double value) {
        percentageMap.keySet().forEach(t-> {
            final double valuePerc = (percentageMap.get(t) * value) / PERCENTAGE;
            this.mergeMap(valueMap, t, valuePerc, (d1, d2) -> {
                return valueMap.get(t) + valuePerc;
            });
        });
    }

    private void countMap(final Map<BodyPart, Integer>timesMap, final Map<BodyPart, Double>percentageMap) {
    percentageMap.keySet().forEach(i-> {
        this.mergeMap(timesMap, i, 1, (i1, i2) -> {
            return timesMap.get(i) + 1;
        });
    });
    }

    private void midMap(final Map<BodyPart, Double>scoreMap, final Map<BodyPart, Integer>timesMap) {
        timesMap.keySet().forEach(i -> {
            final int num = timesMap.get(i);
            final Double mid = (num > 0) ? scoreMap.get(i) / num : 0.00;
            scoreMap.put(i, mid);
        });
    }

    private <X, Y> void mergeMap(final Map<X, Y> mapMerge, final X source, final Y data, final BiFunction<Y, Y, Y> function) {
        mapMerge.merge(source, data, function);
    }

    /**
     * give the time of an exercise calculated like a single exercise duration multiplied for number of its repetition.
     * @param exe Exercise
     * @return calculation time for an exercise
     */
    private Double timeExercise(final Exercise exe) {
        return Double.valueOf((double) exe.getTime() * exe.getRepetition()); //score=time*numRipetizioni
    }

    @Override
    public String toString() {
        return "\n\n WorkoutImpl [date = " + date + ", time = " + time + ", state = " + state 
                + "\n Routine = " + routine.getName() 
                + "\n ScoreList = " + scoreList
                + "\n WorkoutScore = " + this.getWorkoutScore()
                + "\n WorkoutParts = " + this.getPercentuageParts()
                + "\n TimeParts = " + this.getTimeParts() 
                + "]";
    }

}
