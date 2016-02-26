package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * principal class of model.
 *
 */
public class MyWorkoutBuddyModelImpl implements MyWorkoutBuddyModel {

    private final ManageWorkout manager;

    /**
     * 
     */
    public MyWorkoutBuddyModelImpl() {
        manager = new ManageWorkout();
    }

    @Override
    public void addAccount(final String userName, final String password, final String avatar) {
        manager.addAccount(userName, password, avatar);
    }

    @Override
    public void addUser(final String firstName, final String secondName, final int age, final String email) {
        manager.addUser(firstName, secondName, age, email);
    }

    @Override
    public void loginUser(final String name, final String password) {
        manager.loginUser(name, password);
    }

    @Override
    public Optional<String> getCurrentNameAccount() {
        return manager.getCurrentNameAccount();
    }

    @Override
    public void logoutUser() {
        manager.logoutUser();
    }

    @Override
    public void addRoutine(final String code, final String nameRoutine, final String target) {
        manager.addRoutine(code, nameRoutine, target);
    }

    @Override
    public void addGymExcercise(final String nameRoutine, final String target, 
            final String nameTool, final int settingValue, final int repetition,
            final int time, final int numSession, final int pause) {
        manager.addGymExcercise(nameRoutine, target, nameTool, settingValue, repetition, time, numSession, pause);
    }

    @Override
    public void addWorkout(final String nameRoutine, 
            final LocalDate localDate, final LocalTime localTime, final boolean state) {
        manager.addWorkout(nameRoutine, localDate, localTime, state);
    }

    @Override
    public void addExerciseScore(final List<Integer> scoreList) {
        manager.addExerciseScore(scoreList);
    }

    @Override
    public void body() {
        manager.body();
    }

    @Override
    public void body(final String bodyPart, final String bodyZone) {
        manager.body(bodyPart, bodyZone);
    }

    @Override
    public void body(final String bodyPart) {
        manager.body(bodyPart);
    }

    @Override
    public void addDataMeasure(final LocalDate localDate) {
        manager.addDataMeasure(localDate);
    }

    @Override
    public void addBodyMeasure(final String measureBodyZone, final Double measure, final boolean firstTime) {
        manager.addBodyMeasure(measureBodyZone, measure, firstTime);
    }

    @Override
    public void addGymTool(final String description, final String nameTool, final String nameImage, 
            final int num, final int valueMin, final int valueMax) {
        manager.addGymTool(description, nameTool, nameImage, num, valueMin, valueMax);
    }

    @Override
    public void addBodyPart(final String toolCode, final String bodyPart, final Double percentage) {
        manager.addBodyPart(toolCode, bodyPart, percentage);
    }

    @Override
    public List<GymTool> getGymToolList() {
        return manager.getGymToolList();
    }

    @Override
    public List<User> getUserList() {
        return manager.getUserList();
    }

    @Override
    public int getNumExercise() {
        return manager.getNumExercise();
    }

    @Override
    public List<BodyData> getMeasureList() {
        return manager.getMeasureList();
    }

    @Override
    public List<Routine> getRoutineList() {
        return manager.getRoutineList();
    }

    @Override
    public List<Workout> getWorkoutList() {
        return manager.getWorkoutList();
    }

    @Override
    public List<Double> scoreWorkout() {
        return manager.scoreWorkout();
    }

    @Override
    public Map<String, Double> scoreBodyPart() {
        return manager.statisticMap(MethodKey.SCORE_PART.toString());
    }

    @Override
    public Map<String, Double> scoreBodyZone() {
        return manager.statisticMap(MethodKey.SCORE_ZONE.toString());
    }

    @Override
    public Map<String, Double> scoreGymTool() {
        return manager.statisticMap(MethodKey.SCORE_TOOL.toString());
    }

    @Override
    public Map<String, Double> timeBodyPart() {
        return manager.statisticMap(MethodKey.TIME_PART.toString());
    }

    @Override
    public Map<String, Double> timeBodyZone() {
        return manager.statisticMap(MethodKey.TIME_ZONE.toString());
    }

    @Override
    public Map<String, Double> timeGymTool() {
        return manager.statisticMap(MethodKey.TIME_TOOL.toString());
    }

    @Override
    public List<Double> trendBodyBMR() {
        return manager.trendList(MethodKey.TREND_BMR.toString());
    }

    @Override
    public List<Double> trendBodyBMI() {
        return manager.trendList(MethodKey.TREND_BMI.toString());
    }

}
