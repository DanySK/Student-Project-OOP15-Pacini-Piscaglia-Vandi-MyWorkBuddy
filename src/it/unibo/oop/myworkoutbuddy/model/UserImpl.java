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

    private Account account;
    private Person person;

    private Body body;

    private List<BodyData> measureList;     // list of body periodic measure
    private List<Workout> workoutList;    // list of training sessions done/to do
    private List<Routine> routineList;    // list of available Routine

    /**
     * 
     */
    public UserImpl() {
    }

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

    /**
     * 
     */
    @Override
    public Account getAccount() {
        return this.account;
    }

    /**
     * 
     */
    @Override
    public Person getPerson() {
        return this.person;
    }

    /**
     * 
     */
    @Override
    public List<BodyData> getMeasureList() {
        return this.measureList;
    }

    /**
     * 
     */
    @Override
    public List<Workout> getWorkoutList() {
        return this.workoutList;
    }

    /**
     * 
     */
    @Override
    public List<Routine> getRoutineList() {
        return this.routineList;
    }

    /**
     * 
     * @param localDate LocalDate
     * @param measureBodyZone String
     * @param measure Double
     * @throws NullPointerException
     */
    @Override
    public void addMesure(final LocalDate localDate, final String measureBodyZone, final Double measure) throws NullPointerException {
        this.checkNotNull(localDate);
        this.checkNotNull(measureBodyZone);
        this.checkNotNull(measure);
        final BodyData bodyData = new BodyData(localDate);
        bodyData.addBodyMeasure(measureBodyZone, measure);
        this.measureList.add(bodyData);
    }

    /**
     * 
     */
    @Override
    public void addWorkout(final Workout workout) throws NullPointerException {
        this.checkNotNull(workout);
        this.workoutList.add(workout);
    }

    /**
     * 
     */
    @Override
    public void addRoutine(final Routine routine) throws NullPointerException {
        this.checkNotNull(routine);
        this.routineList.add(routine);
    }

    /**
     * 
     * @return list of BMI values 
     */
    @Override
    public List<Double> trendBodyBMI() {
        /*
        final List<Double> listBMI = new ArrayList<>();
        this.getMeasureList().forEach(i -> {
            listBMI.add(i.getBodyBMI(this.getPerson().getAge()));
        });
        */
        //return listBMI;
        return this.getMeasureList().stream().map(i->i.getBodyBMI(this.getPerson().getAge())).collect(Collectors.toList());
    }

    /**
     * 
     * @return list of performance scores of all workouts
     */
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

    /**
     * @return a map <BodyPart, Score> of all workout scores mapped in all BodyParts
     */
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

    /**
     * @return a map <BodyPart, Time> of all workout times mapped in all BodyParts
     */
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

    /**
     * @return a map <BodyZone, Score> of all workout scores mapped in all BodyZones
     */
    @Override
    public Map<String, Double> scoreBodyZone() {
        return this.mapBodyZone(new HashMap<String, Double>(), this.scoreBodyPart());
    }

    /**
     * @return a map <BodyZone, Time> of all workout times mapped in all BodyZones
     */
    @Override
    public Map<String, Double> timeBodyZone() {
        return this.mapBodyZone(new HashMap<String, Double>(), this.timeBodyPart());
    }

    /**
     * 
     * @return the trending of a human body
     */
    @Override
    public List<Double> trendBodyMass() throws NullPointerException, IllegalArgumentException {
        return this.getMeasureList().stream().map(BodyData::getBodyMass).collect(Collectors.toList());
    }

    /**
     * 
     * @return a map made of associations between a codeTool and its relative increment/decrement of use
     */
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

    /**
     * 
     * @return a map made of associations between a codeTool and its relative time of use
     */
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

    /**
     * function of merge of mapBodyZone2 and mapBodyZone
     * @param mapBodyZone Map<String, Double>
     * @param mapBodyPart Map<String, Double>
     * @return mapBodyZone
     */
    private Map<String, Double> mapBodyZone(final Map<String, Double> mapBodyZone, final Map<String, Double> mapBodyPart) {
        mapBodyPart.keySet().forEach(i -> {
            final String bodyZone = body.getPartZone(i).get();
            final Double oldValue = mapBodyZone.get(bodyZone); //mapBodyZone.get(i.getBodyZone());
            final Double value = mapBodyPart.get(i);

            mapBodyZone.merge(bodyZone, value, (d0, d1) -> {
                return value + oldValue;
            });
        });

        return mapBodyZone;
    }

    private void checkNotNull(final Object obj) throws NullPointerException {
        if (obj == null) {
            throw new NullPointerException();
        }
    }

    @Override
    public String toString() {
        return "\n USER ["
                + "\n account = " + this.account 
                + "\n Person = " + this.person
                + "]";
    }
}
