package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
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
public class RoutineImpl implements Routine {

    private static final Double SCORE_NULL = 0.00;
    private static final int PERCENTAGE = 100;

    private int idRoutine;
    private LocalDate localDate;
    private Optional<Workout> workout;

    private Map<Exercise, Integer> valueMap;
    private boolean state;

    /**
     * @param idRoutine Integer
     * @param workout Workout
     * @param localDate LocalDate
     * @param state boolean
     */
    public RoutineImpl(final int idRoutine, final Workout workout, final LocalDate localDate, final boolean state) {
        this.idRoutine = idRoutine;
        this.localDate = localDate;
        this.workout = Optional.of(workout);
        this.valueMap = new HashMap<>();
        this.state = state;
    }

    @Override
    public int getIdRoutine() {
        return this.idRoutine;
    }

    @Override
    public LocalDate getDate() {
        return this.localDate;
    }


    @Override
    public Workout getWorkout() {
        return this.workout.get();
    }

    @Override
    public List<Integer> getValueList() {
        if (this.isWorkout()) {
            return this.getWorkout().getExerciseList().
                    stream().map(i -> this.valueMap.get(i)).
                    filter(t -> t != null).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public Double getRoutineScore() {
        if (this.isWorkout()) {
            final List<Double> listScore = this.getWorkout().getExerciseList().
                    stream().map(i -> this.scoreExercise(i)).
                    filter(t -> t != null).collect(Collectors.toList());

            return listScore.stream().mapToDouble(i->i.doubleValue()).average().getAsDouble();
        }
        return SCORE_NULL;
    }

    @Override
    public Map<Exercise, Integer> getScoreMap() {
        return this.valueMap;
    }

    @Override
    public Map<String, Double> getPercentuageParts() {
        final Map<String, Double> scoreMap = new HashMap<>();
        final Map<String, Integer> timesMap = new HashMap<>();
        if (!this.isWorkout()) {
            return scoreMap;
        }
        final List<Exercise> listExercise = this.getWorkout().getExerciseList();
        listExercise.forEach(i -> {
            final Double score = this.scoreExercise(i);
            final Map<String, Double> percentageMap = i.getGymTool().getBodyMap();
            this.percentageMapping(scoreMap, percentageMap, score);
            this.countMap(timesMap, percentageMap);
        });
        this.midMap(scoreMap, timesMap);
        return scoreMap;
    }

    @Override
    public Map<String, Double> getTimeParts() {
        final Map<String, Double> timeMap = new HashMap<>();
        if (!this.isWorkout()) {
            return timeMap;
        }
        final List<Exercise> listExercise = this.getWorkout().getExerciseList();
        listExercise.forEach(i -> {
            final Double minutes = this.timeExercise(i);
            final Map<String, Double> percentageMap = i.getGymTool().getBodyMap();
            this.percentageMapping(timeMap, percentageMap, minutes);
        });
        return timeMap;
    }

    @Override
    public Map<String, Double> getTimeTools() {
        final Map<String, Double> timeMap = new HashMap<>();
        if (!this.isWorkout()) {
            return timeMap;
        }
        this.getWorkout().getExerciseList().forEach(i -> {
            final String code = i.getGymTool().getCode();
            final Double minutes = this.timeExercise(i);
            this.mergeMap(timeMap, code, minutes, (d1, d2) -> {
                return timeMap.get(code) + minutes;
            });
        });
        return timeMap;
    }

    @Override
    public Map<String, Double> getScoreTools() {
        final Map<String, Double> scoreMap = new HashMap<>();
        if (!this.isWorkout()) {
            return scoreMap;
        }
        this.getWorkout().getExerciseList().forEach(i -> {
            final String code = i.getGymTool().getCode();
            final Double score = this.scoreExercise(i);
            this.mergeMap(scoreMap, code, score, (d1, d2) -> {
                return scoreMap.get(code) + score;
            });
        });
        return scoreMap;
    }

    @Override
    public void addValue(final List<Integer> valueList) {
        if (this.isWorkout()) {
            final Integer dim = valueList.size();
            valueList.stream().filter(i -> valueList.indexOf(i) < dim).forEach(t -> {
                final Integer pos = valueList.indexOf(t);
                this.addValueIndex(pos, t);
            });
        }
    }

    @Override
    public boolean isState() {
        return state;
    }

   private void addValueIndex(final Integer index, final Integer value) {
           final Exercise exerc = this.getWorkout().getExerciseList().get(index);
           final GymTool gymTool = exerc.getGymTool();
           final Integer min = gymTool.getMinValue();
           final Integer max = gymTool.getMaxValue();
           // check score to be in : [min max]
           final Integer newValue = (value < min) ? min : (value > max) ? max : value;
           this.valueMap.put(exerc, newValue);
   }

    /**
     * give the normalized score of the exercise
     * @param exerc an exercise
     * @return a Double
     */
    private Double scoreExercise(final Exercise exerc) {
        final Integer value = this.valueMap.get(exerc);

        if (!this.checkValue(value)) {
            return SCORE_NULL;
        }

        return exerc.getNormalizedScore((double) value);
    }

    /**
     * give the time of an exercise calculated like a single exercise duration multiplied for number of its repetition.
     * @param exe Exercise
     * @return calculation time for an exercise
     */
    private Double timeExercise(final Exercise exe) {
        return Double.valueOf((double) exe.getTime() * exe.getRepetition()); //score=time*numRipetizioni
    }

    /**
     * it calculates a percentageMap composed by a set of percentage values for each specific string key
     * @param valueMap
     * @param percentageMap
     * @param value
     */
    private void percentageMapping(final Map<String, Double> valueMap, final Map<String, Double> percentageMap, final Double value) {
        percentageMap.keySet().forEach(t-> {
            final double valuePerc = (percentageMap.get(t) * value) / PERCENTAGE;
            this.mergeMap(valueMap, t, valuePerc, (d1, d2) -> {
                return valueMap.get(t) + valuePerc;
            });
        });
    }

    /**
     * increment times of a timesMap with i key in for each of percentageMap keySet.
     * @param timesMap
     * @param percentageMap
     */
    private void countMap(final Map<String, Integer>timesMap, final Map<String, Double>percentageMap) {
    percentageMap.keySet().forEach(i-> {
        this.mergeMap(timesMap, i, 1, (i1, i2) -> {
            return timesMap.get(i) + 1;
        });
    });
    }

    /**
     * for each value of timesMap keySet it calculate the medium value for the scoreMap.
     * @param scoreMap
     * @param timesMap
     */
    private void midMap(final Map<String, Double>scoreMap, final Map<String, Integer>timesMap) {
        timesMap.keySet().forEach(i -> {
            final Integer num = timesMap.get(i);
            this.mergeMap(scoreMap, i, 0.00, (d1, d2)-> {
                return scoreMap.get(i) / num;
            });
        });
    }

    /**
     * make the merge for mapMerge
     * @param mapMerge Map<X, Y>
     * @param source  X
     * @param data Y
     * @param function BiFunction<Y, Y, Y>
     */
    private <X, Y> void mergeMap(final Map<X, Y> mapMerge, final X source, final Y data, final BiFunction<Y, Y, Y> function) {
        mapMerge.merge(source, data, function);
    }

    /**
     * true if routine value is present
     * @return a boolean
     */
    private boolean isWorkout() {
        return this.workout.isPresent();
    }

    /**
     * true if score is acceptable
     * @param score
     * @return a boolean
     */
    private boolean checkValue(final Integer value) {
        return value != null && value > 0;
    }

    @Override
    public String toString() {
        return "\n\n RoutineImpl [ id = " + this.getIdRoutine() + " date = " + this.getDate()
                + "\n Workout = " + this.getWorkout().getCode()
                + "\n RoutineValue = " + this.getValueList()
                + "\n RoutineScore = " + this.getRoutineScore()
                + "\n RoutineParts = " + this.getPercentuageParts()
                + "\n TimeParts = " + this.getTimeParts()
                + "\n TimeTools = " + this.getTimeTools()
                + "\n ScoreTools = " + this.getScoreTools()
                + "]";
    }
}
