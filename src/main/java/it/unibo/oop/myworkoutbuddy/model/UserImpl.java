package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
/**
 Informations about User's activities :
 Account, Data, Measure, Training, TrainingCard.

    account : account
    person : user's general data

    measureList : list of body periodic measure
    workoutList : list of training sessions done/to do
    routineList : list of available Routine
*/
public class UserImpl implements User {

    private final Account account;
    private final Person person;

    private Body body;

    private List<BodyData> measureList;     // list of body periodic measure
    private List<Workout> workoutList;    // list of training sessions done/to do
    private List<Routine> routineList;    // list of available Routine

    /**
     * @param account Account
     * @param person Person
     * @param body Body
     */
    public UserImpl(final Account account, final Person person, final Body body) {
        this.account = account;
        this.person = person;

        this.body = body;

        this.measureList = new ArrayList<>();
        this.workoutList = new ArrayList<>();
        this.routineList = new ArrayList<>();
    }

    @Override
    public Account getAccount() {
        return this.account;
    }

    @Override
    public Person getPerson() {
        return this.person;
    }

    @Override
    public List<BodyData> getMeasureList() {
        return this.measureList;
    }

    @Override
    public List<Workout> getWorkoutList() {
        return this.workoutList;
    }

    @Override
    public List<Routine> getRoutineList() {
        return this.routineList;
    }

    @Override
    public void addMesure(final LocalDate localDate, final String measureBodyZone, final Double measure) {
        final BodyData bodyData = new BodyData(localDate);
        bodyData.addBodyMeasure(measureBodyZone, measure);
        this.measureList.add(bodyData);
    }

    @Override
    public void addWorkout(final Workout workout) {
        this.workoutList.add(workout);
    }

    @Override
    public void addRoutine(final Routine routine) {
        this.routineList.add(routine);
    }

    @Override
    public List<Double> trendBodyBMR() {
        return this.getMeasureList().stream().map(i->i.getBodyBMR(this.getPerson().getAge())).collect(Collectors.toList());
    }

    @Override
    public List<Double> trendBodyBMI() {
        return this.getMeasureList().stream().map(BodyData::getBodyBMI).collect(Collectors.toList());
    }

    @Override
    public List<Double> scoreWorkout() {
    return this.getWorkoutList().stream().map(Workout::getWorkoutScore).collect(Collectors.toList());
        /*
        final List<Double> newList = new ArrayList<>();
        this.getWorkoutList().forEach(i-> {
            newList.add(i.getWorkoutScore());
        });
        */
    }

    @Override
    public Map<String, Double> scoreBodyPart() {
        final Map<String, Double> scoreMap = new HashMap<>();
        this.getWorkoutList().forEach(i-> {
            final Map<String, Double> tempMap = i.getPercentuageParts();
            this.mapSumGen(scoreMap, tempMap, (d1, d2) -> {
                return d1 + d2;
            });
        });

        return scoreMap;
    }

    @Override
    public Map<String, Double> timeBodyPart() {
        final Map<String, Double> timeMap = new HashMap<>();
        this.getWorkoutList().forEach(i-> {
            final Map<String, Double> tempMap = i.getTimeParts();
            this.mapSumGen(timeMap, tempMap, (d1, d2) -> {
                return d1 + d2;
            });
        });

        return timeMap;
    }

    @Override
    public Map<String, Double> scoreBodyZone() {
        return this.mapBodyZone(new HashMap<String, Double>(), this.scoreBodyPart());
    }

    @Override
    public Map<String, Double> timeBodyZone() {
        return this.mapBodyZone(new HashMap<String, Double>(), this.timeBodyPart());
    }

    @Override
    public Map<String, Double> scoreGymTool() {
        final Map<String, Double> scoreMap = new HashMap<>();
        this.getWorkoutList().forEach(i-> {
            final Map<String, Double> tempMap = i.getScoreTools();
            this.mapSumGen(scoreMap, tempMap, (d1, d2) -> {
                return d1 + d2;
            });
        });

        return scoreMap;
    }

    @Override
    public Map<String, Double> timeGymTool() {
        final Map<String, Double> timeMap = new HashMap<>();
        this.getWorkoutList().forEach(i-> {
            final Map<String, Double> tempMap = i.getTimeTools();
            this.mapSumGen(timeMap, tempMap, (d1, d2) -> {
                return d1 + d2;
            });
        });

        return timeMap;
    }

    /**
     * It maps for each body zone the sum of all values of the body zone parts
     * @param mapBodyZone Map<String, Double>
     * @param mapBodyPart Map<String, Double>
     * @return a Map<String, Double>
     */
    private Map<String, Double> mapBodyZone(final Map<String, Double> mapBodyZone, final Map<String, Double> mapBodyPart) {
        mapBodyPart.keySet().forEach(i -> {
            final String bodyZone = this.body.getPartZone(i).get();
            final Double oldValue = mapBodyZone.get(bodyZone);
            final Double value = mapBodyPart.get(i);

            mapBodyZone.merge(bodyZone, value, (d0, d1) -> {
                return value + oldValue;
            });
        });

        return mapBodyZone;
    }

    /**
     * 
     * Sum between two Maps : destMap = destMap + sourceMap
     * @param <Y>
     * @param <X>
     * @param destMap
     * @param sourceMap
     */
    private <X, Y> void mapSumGen(final Map<X, Y> destMap, final Map<X, Y> sourceMap, final BiFunction<Y, Y, Y> function) {
        sourceMap.keySet().forEach(t -> {
            final Y oldValue = destMap.get(t);
            final Y newValue = sourceMap.get(t);
            destMap.merge(t, newValue, (val1, val2) -> {
                return function.apply(newValue, oldValue);
            });
        });
    }

    @Override
    public String toString() {
        return "\n USER ["
                + "\n account = " + this.account 
                + "\n Person = " + this.person
                + "]";
    }
}
