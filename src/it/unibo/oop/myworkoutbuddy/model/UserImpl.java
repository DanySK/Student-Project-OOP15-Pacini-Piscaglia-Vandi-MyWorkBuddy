package it.unibo.oop.myworkoutbuddy.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import it.unibo.oop.myworkoutbuddy.model.Body.BodyData;
import it.unibo.oop.myworkoutbuddy.model.Body.BodyPart;
import it.unibo.oop.myworkoutbuddy.model.Body.BodyZone;

/**
 * 
 * Informations about User's activities :
 * Account, Data, Measure, Training, TrainingCard.
 * -------------------------------------------------------------
 */
public class UserImpl implements User {

    private Account account;
    private Person person;

    private List<BodyData> measureList;     // list of body periodic measure
    private List<Workout> workoutList;    // list of training sessions done/to do
    private List<WorkoutRoutine> routineList;    // list of available Routine
    /**
     * 
     */
    public UserImpl() {
    }

    /**
     * 
     * @param account Account
     * @param person Person
     */
    public UserImpl(final Account account, final Person person) {
        this.account = account;
        this.person = person;

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
    public List<WorkoutRoutine> getRoutineList() {
        return this.routineList;
    }

    @Override
    public void addMesure(final BodyData bodyMeasure) {
        this.measureList.add(bodyMeasure);
    }
    @Override
    public void addWorkout(final Workout workout) {
        this.workoutList.add(workout);
    }
    @Override
    public void addRoutine(final WorkoutRoutine routine) {
        this.routineList.add(routine);
    }
    @Override
    public void upDateStatus() {
    }
    /**
     * 
     * @return list of performance scores of all workouts
     */
    @Override
    public List<Double> performanceScore() {
    final List<Double> newList = this.getWorkoutList().stream().map(Workout::getWorkoutScore).collect(Collectors.toList());
        /*
        final List<Double> newList = new ArrayList<>();
        this.getWorkoutList().forEach(i-> {
            newList.add(i.getWorkoutScore());
        });
        */
        return newList;
    }

    /**
     * @return a map <BodyPart, Score> of all workout scores mapped in all BodyParts
     */
    @Override
    public Map<BodyPart, Double> performanceBodyPart() {
        final Map<BodyPart, Double> performanceMap = new HashMap<>();
        this.getWorkoutList().forEach(i-> {
            final Map<BodyPart, Double> tempMap = i.getPercentuageParts();
            this.mapSum(performanceMap, tempMap);
        });

        return performanceMap;
    }

    /**
     * @return a map <BodyZone, Score> of all workout scores mapped in all BodyZones
     */
    @Override
    public Map<BodyZone, Double> performanceBodyZone() {
        final Map<BodyZone, Double> mapBodyZone = new HashMap<>();
        final Map<BodyPart, Double> mapBodyPart = this.performanceBodyPart();
        this.mapPartZone(mapBodyZone, mapBodyPart);

        return mapBodyZone;
    }

    /**
     * @return a map <BodyPart, Time> of all workout times mapped in all BodyParts
     */
    @Override
    public Map<BodyPart, Double> timeBodyPart() {
        final Map<BodyPart, Double> timeMap = new HashMap<>();
        this.getWorkoutList().forEach(i-> {
            final Map<BodyPart, Double> tempMap = i.getTimeParts();
            this.mapSum(timeMap, tempMap);
        });

        return timeMap;
    }

    /**
     * @return a map <BodyZone, Time> of all workout times mapped in all BodyZones
     */
    @Override
    public Map<BodyZone, Double> timeBodyZone() {
        final Map<BodyZone, Double> mapBodyZone = new HashMap<>();
        final Map<BodyPart, Double> mapBodyPart = this.timeBodyPart();
        this.mapPartZone(mapBodyZone, mapBodyPart);

        return mapBodyZone;
    }

    //         System.out.println(tempUser.getMeasureList().get(0).getBodyMass());
    /**
     * 
     * @return the trending of a human body
     */
    @Override
    public List<Double> trendBodyMass() {
        final List<Double> bodyMassList = new ArrayList<>();

        this.getMeasureList().forEach(i -> {
            bodyMassList.add(i.getBodyMass());
        });
        return bodyMassList;
        //return this.getMeasureList().stream().mapToDouble(i -> i.getBodyMass()).toArray();
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
            tempMap.keySet().forEach(h -> {
                timeMap.merge(h, tempMap.get(h), (d1, d2) -> {
                    return timeMap.get(h) + tempMap.get(h);
                });
            });
        });
        return timeMap;
    }

    /**
     * Sum between two Maps : destMap = destMap + sourceMap
     * @param destMap
     * @param sourceMap
     */
    private void mapSum(final Map<BodyPart, Double> destMap, final Map<BodyPart, Double> sourceMap) {
        sourceMap.keySet().forEach(t -> {
            final Double newValue = sourceMap.get(t);
            final Double oldValue = destMap.get(t);
            destMap.merge(t, newValue, (d, d1) -> {
                return newValue + oldValue;
            });
        });
    }

    /**
     * map each BodyPart in the specific BodyZone
     * @param mapBodyZone
     * @param mapBodyPart
     */
    private void mapPartZone(final Map<BodyZone, Double> mapBodyZone, final Map<BodyPart, Double> mapBodyPart) {
        mapBodyPart.keySet().forEach(i -> {
        final BodyZone bodyZone = Body.getBodyZone(i.getName());
        final Double value = mapBodyPart.get(i);
        mapBodyZone.merge(bodyZone, value, (d, d1) -> { 
            return value + mapBodyZone.get(bodyZone); 
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

/**
 * 
 *
 */
class AccountImpl implements Account {

    private String userName;
    private String password;
    private String avatar;
    /**
     * 
     * @param userName String
     * @param password String
     * @param avatar String
     */
    AccountImpl(final String userName, final String password, final String avatar) {
        this.userName = userName;
        this.password = password;
        this.avatar = avatar;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getAvatar() {
        return this.avatar;
    }

    @Override
    public String toString() {
        return "AccountImpl [userName = " + this.userName + ", password = " + this.password + "]";
    }
}
