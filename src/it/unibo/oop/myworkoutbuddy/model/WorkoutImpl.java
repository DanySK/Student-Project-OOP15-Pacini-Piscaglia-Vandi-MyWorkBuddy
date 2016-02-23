package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
/**
 User's data of a single workout session.

     date : session date
     time : hour of start session
     state : session state (done/to do)
     routine : WorkoutRoutine used
     scoreList : list of got scores with card in session.
*/
public class WorkoutImpl implements Workout {

    private static final Double SCORE_NULL = 0.00;
    private static final int PERCENTAGE = 100;

    private LocalDate date;
    private LocalTime time;
    private boolean state;
    private Optional<Routine> routine;

    private Map<Exercise, Integer> scoreMap;

    /**
     * @param routine Routine
     * @param date LocalDate
     * @param time LocalTime
     * @param state boolean
     */
    public WorkoutImpl(final Routine routine, final LocalDate date, final LocalTime time, final boolean state) {
        this.date = date;
        this.time = time;
        this.state = false;
        this.routine = Optional.of(routine);
        this.state = state;
        this.scoreMap = new HashMap<>();
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
     * @param indExercise
     * @param score
     * 
     * @throws NullPointerException exception for check about notNullValue
     * @throws IllegalArgumentException exception for check about notNegativeValue
     */
   @Override
   public void addScore(final Integer index, final Integer score) {
       if (this.isRoutine()) {
           final Exercise exerc = this.getRoutine().getExerciseList().get(index);
           final GymTool gymTool = exerc.getGymTool();
           final Integer min = gymTool.getMinValue();
           final Integer max = gymTool.getMaxValue();
           // check score to be in : [min max]
           final Integer newScore = (score < min) ? min : (score > max) ? max : score;

           this.scoreMap.put(exerc, newScore);
       }
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

    /**
     * 
     * @return scoreMap
     */
    @Override
    public Map<Exercise, Integer> getScoreMap() {
        return this.scoreMap;
    }

    @Override
    public List<Integer> getScoreList() {
        if (this.isRoutine()) {
            return this.getRoutine().getExerciseList().
                    stream().map(i -> this.scoreMap.get(i)).
                    collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    /**
     * 
     * @return
     */
    @Override
    public Routine getRoutine() {
        return this.routine.get();
    }

    /**
     * return the average value of all scores got in the workout routines.
     * @return
     */
    @Override
    public Double getWorkoutScore() {
        if (this.isRoutine()) {
            final List<Double> listScore = this.getRoutine().getExerciseList().
                    stream().map(i -> this.normalizedScore(i)).
                    collect(Collectors.toList());

            return listScore.stream().mapToDouble(i->i.doubleValue()).average().getAsDouble();
        }

        return SCORE_NULL;
    }

    /**
     * return a map <BodyPart, Score> of all scores got by each body part(muscle).
     */
    @Override
    public Map<String, Double> getPercentuageParts() {
        final Map<String, Double> scoreMap = new HashMap<>();
        final Map<String, Integer> timesMap = new HashMap<>();
        if (!this.isRoutine()) {
            return scoreMap;
        }
        final List<Exercise> listExercise = this.getRoutine().getExerciseList();
        listExercise.forEach(i -> {
            final Double score = this.normalizedScore(i); //this.normalizedScore(listExercise, i);

            final Map<String, Double> percentageMap = i.getGymTool().getBodyMap();
            this.percentageMapping(scoreMap, percentageMap, score);
            this.countMap(timesMap, percentageMap);
        });
        this.midMap(scoreMap, timesMap);
        return scoreMap;
    }

    /**
     * return a map of workTime for each single body part(muscle).
     * @return timeMap
     */
    @Override
    public Map<String, Double> getTimeParts() {
        final Map<String, Double> timeMap = new HashMap<>();
        if (!this.isRoutine()) {
            return timeMap;
        }
        this.getRoutine().getExerciseList().forEach(i -> {
            final Double minutes = this.timeExercise(i);
            final Map<String, Double> percentageMap = i.getGymTool().getBodyMap();
            this.percentageMapping(timeMap, percentageMap, minutes);
        });
        return timeMap;
    }

    @Override
    public Map<String, Double> getTimeTools() {
        final Map<String, Double> timeMap = new HashMap<>();
        if (!this.isRoutine()) {
            return timeMap;
        }
        this.getRoutine().getExerciseList().forEach(i -> {
            final String code = i.getGymTool().getCode();
            final Double minutes = this.timeExercise(i);
            this.mergeMap(timeMap, code, minutes, (d1, d2) -> {
                return timeMap.get(code) + minutes;
            });
        });
        return timeMap;
    }

    /**
     * 
     * @return map of GymToolScore
     */
    @Override
    public Map<String, Double> getScoreTools() {
        final Map<String, Double> scoreMap = new HashMap<>();
        if (!this.isRoutine()) {
            return scoreMap;
        }
        this.getRoutine().getExerciseList().forEach(i -> {
            final String code = i.getGymTool().getCode();
            final Double score = this.normalizedScore(i);
            this.mergeMap(scoreMap, code, score, (d1, d2) -> {
                return scoreMap.get(code) + score;
            });
        });
        return scoreMap;
    }

    private void percentageMapping(final Map<String, Double> valueMap, final Map<String, Double> percentageMap, final Double value) {
        percentageMap.keySet().forEach(t-> {
            final double valuePerc = (percentageMap.get(t) * value) / PERCENTAGE;
            this.mergeMap(valueMap, t, valuePerc, (d1, d2) -> {
                return valueMap.get(t) + valuePerc;
            });
        });
    }

    private void countMap(final Map<String, Integer>timesMap, final Map<String, Double>percentageMap) {
    percentageMap.keySet().forEach(i-> {
        this.mergeMap(timesMap, i, 1, (i1, i2) -> {
            return timesMap.get(i) + 1;
        });
    });
    }

    private void midMap(final Map<String, Double>scoreMap, final Map<String, Integer>timesMap) {
        timesMap.keySet().forEach(i -> {
            final Integer num = timesMap.get(i);
            this.mergeMap(scoreMap, i, 0.00, (d1, d2)-> {
                return scoreMap.get(i) / num;
            });
        });
    }

    private <X, Y> void mergeMap(final Map<X, Y> mapMerge, final X source, final Y data, final BiFunction<Y, Y, Y> function) {
        mapMerge.merge(source, data, function);
    }

    private Double normalizedScore(final Exercise exerc) {
        final Integer score = this.scoreMap.get(exerc);

        if (!this.checkScore(score)) { // fare check score
            return SCORE_NULL;
        }

        return exerc.getNormalizedScore((double) score);
    }

    /**
     * give the time of an exercise calculated like a single exercise duration multiplied for number of its repetition.
     * @param exe Exercise
     * @return calculation time for an exercise
     */
    private Double timeExercise(final Exercise exe) {
        return Double.valueOf((double) exe.getTime() * exe.getRepetition()); //score=time*numRipetizioni
    }

    private boolean isRoutine() {
        return this.routine.isPresent();
    }

    private boolean checkScore(final Integer score) {
        return score != null && score > 0;
    }

    @Override
    public String toString() {
        return "\n\n WorkoutImpl [date = " + date + ", time = " + time + ", state = " + state 
                + "\n Routine = " + this.getRoutine().getCode()
                + "\n RoutineScore = " + this.getScoreList()
                + "\n WorkoutScore = " + this.getWorkoutScore()
                + "\n WorkoutParts = " + this.getPercentuageParts()
                + "\n TimeParts = " + this.getTimeParts()
                + "\n TimeTools = " + this.getTimeTools()
                + "\n ScoreTools = " + this.getScoreTools()
                + "]";
    }

}
