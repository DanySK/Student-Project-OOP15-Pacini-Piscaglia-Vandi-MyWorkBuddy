package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
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
    public void addAccount(final String userName, final String password) {
        manager.addAccount(userName, password);
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
    public Optional<String> getCurrentUserName() {
        return manager.getCurrentNameAccount();
    }

    @Override
    public void logoutUser() {
        manager.logoutUser();
    }

    @Override
    public void addWorkout(final String code, final String nameWorkout, final String target) {
        manager.addWorkout(code, nameWorkout, target);
    }

    @Override
    public void removeWorkout(final String code) {
        manager.removeWorkout(code);
    }

    @Override
    public void addGymExcercise(final String nameRoutine, final String target, 
            final String nameTool, final List<Integer> numSessions) {
        manager.addGymExcercise(nameRoutine, target, nameTool, numSessions);
    }

    @Override
    public void addRoutine(final int idRoutine, final String codeWorkout, final LocalDate localDate) {
        manager.addRoutine(idRoutine, codeWorkout, localDate, true);
    }

    @Override
    public void removeRoutine(final int idRoutine) {
        manager.removeRoutine(idRoutine);
    }

    @Override
    public void removeRoutine() {
        manager.removeRoutine();
    }

    @Override
    public void addExerciseValue(final List<Integer> valueList) {
        manager.addExerciseValue(valueList);
    }

    @Override
    public void resetBody() {
        manager.body();
    }

    @Override
    public void setBody(final String bodyPart, final String bodyZone) {
        manager.body(bodyPart, bodyZone);
    }

    @Override
    public void setBody(final String bodyPart) {
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
    public void addGymTool(final String description, final String nameTool, final int num, final int valueMin, final int valueMax) {
        manager.addGymTool(description, nameTool, num, valueMin, valueMax); // ecc.
    }

    @Override
    public void addBodyPart(final String toolCode, final String bodyPart, final Double percentage) {
        manager.addBodyPart(toolCode, bodyPart, percentage);
    }

    @Override
    public Body getApplicationBody() {
        return manager.getBody();
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
    public List<Double> scoreRoutine() {
        return manager.scoreRoutine();
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
