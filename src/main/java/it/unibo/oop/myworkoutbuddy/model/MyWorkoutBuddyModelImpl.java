package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 Application data.

     listUser : list of all memorized users
     listAccount : list of all memorized accounts
     listGymTool : list of all memorized GymTool

     mapGymTool : map <name of GymTool, GymTool>

     loginUser : current User
     currentAccount : current Account
*/
public class MyWorkoutBuddyModelImpl implements MyWorkoutBuddyModel {

    private List<User> listUser;
    private List<Account> listAccount;
    private List<GymTool> listGymTool;

    private Map<String, GymTool> mapGymTool;

    private Body body;

    private Optional<User> currentUser = Optional.empty();
    private Optional<Account> currentAccount = Optional.empty();
    // private Optional<Routine> currentRoutine = Optional.empty();
    private Optional<Workout> currentWorkout = Optional.empty();
    private Optional<BodyData> currentBodyData = Optional.empty();

    /**
     * 
     */
    public MyWorkoutBuddyModelImpl() {
        this.listUser = new ArrayList<>();
        this.listAccount = new ArrayList<>();
        this.listGymTool = new ArrayList<>();

        this.mapGymTool = new HashMap<>();

        this.body = new Body();
    }

    /**
     * 
     * @param indice int
     * @return the Account at indice pos of listAccount
     */
    @Override
    public Account getAccount(final int indice) {
        return this.listAccount.get(indice);
    }

    /**
     * 
     */
    @Override
    public void addGymTool(final String description, final String nameTool, final String nameImage, final int num, final int valueMin, final int valueMax) {
        final GymTool newTool = new GymToolImpl(description, nameTool, nameImage, num, valueMin, valueMax);
        this.listGymTool.add(newTool);
        this.mapGymTool.put(newTool.getCode(), newTool);
    }

    /**
     * @param toolCode String
     * @param bodyPart String
     * @param percentage Double
     */
    @Override
    public void addBodyPart(final String toolCode, final String bodyPart, final Double percentage) {
        final GymTool gymTool = this.getGymTool(toolCode).get();
        gymTool.addBodyPart(bodyPart, percentage);
    }

    /**
     * @param userName String
     * @param password String
     * @param avatar String
     */
    // final String userName, final String password, final String avatar
    @Override
    public void addAccount(final String userName, final String password, final String avatar) {
        final Account accountToAdd = new AccountImpl(userName, password, avatar);
        if (this.isAccount(accountToAdd)) {
            return;
        }
        this.currentAccount = Optional.of(accountToAdd);
        this.listAccount.add(this.currentAccount.get());
    }

    /**
     * add new User associated with the current account.
     * @param firstName String
     * @param secondName String
     * @param age Integer
     * @param email String
     */
    // model.addUser("Paolo", "Rossi", 20, "paolo.rossi@studio.unibo.it");
    @Override
    public void addUser(final String firstName, final String secondName, final int age, final String email) {
        final Person userData = new PersonImpl(firstName, secondName, age, email);
        this.addUser(this.currentAccount.get(), userData);
    }

    /**
     * 
     * @param account Account
     * @param userData Person
     */
    private void addUser(final Account account, final Person person) {

        if (!this.isAccount(account)) {
            this.listAccount.add(account);
        }

        this.currentAccount = Optional.of(account);
        this.listUser.add(new UserImpl(account, person, this.body)); // add this user to list user
    }

    /**
     * 
     * @param userName String
     * @param password String
     */
    @Override
    public void loginUser(final String userName, final String password) {
        this.getUserList().forEach(i -> {
            final Account account = i.getAccount();
            if (account.getUserName().equals(userName) && account.getPassword().equals(password)) {
                this.currentUser = Optional.of(i);
            }
        });
    }

    /**
     * @param code String
     * @param nameRoutine String
     * @param target String
     */
    @Override
    public void addRoutine(final String code, final String nameRoutine, final String target) {
        final Routine newRoutine = new RoutineImpl(code, nameRoutine, target);
        this.currentUser.get().addRoutine(newRoutine);
        //this.currentRoutine = Optional.of(newRoutine);
    }

    /**
     * 
     * @param code String
     * @param target String
     * @param nameTool String
     * @param settingValue Integer
     * @param repetition Integer
     * @param time Integer
     * @param numSession Integer
     * @param pause Integer
     */
    public void addGymExcercise(final String code, final String target, final String nameTool, 
            final int settingValue, final int repetition, final int time, final int numSession, final int pause) {

        final Exercise exercToadd = new ExerciseImpl(target, this.getGymTool(nameTool).get(), settingValue, repetition, time, numSession, pause);
        final Routine routine = this.getRoutine(this.currentUser.get(), code);
        routine.addGymExcercise(exercToadd);
    }

    /**
     * @param codeRoutine String
     * @param localDate LocalDate
     * @param localTime LocalTime
     * @param state boolean
     */
    @Override
    public void addWorkout(final String codeRoutine, final LocalDate localDate, final LocalTime localTime, final boolean state) {
        final Routine routine = this.getRoutine(this.currentUser.get(), codeRoutine);
        final Workout newWorkout = new WorkoutImpl(routine, localDate, localTime, state);
        this.currentUser.get().addWorkout(newWorkout);
        this.currentWorkout = Optional.of(newWorkout);
    }

    /**
     * 
     * @param numEx Integer
     * @param score Integer
     */
    public void addExerciseScore(final Integer numEx, final Integer score) {
        this.currentWorkout.get().addScore(numEx, score);
    }

    /**
     * 
     * @param localDate LocalDate
     */
    @Override
    public void addDataMeasure(final LocalDate localDate) {
        final BodyData bodyData = new BodyData(localDate);
        this.currentUser.get().getMeasureList().add(bodyData);
        this.currentBodyData = Optional.of(bodyData);
    }

    /**
     * 
     * @param bodyZone String
     * @param bodyParts Collection<String>
     */
    @Override
    public void addMapZone(final String bodyZone, final Collection<String> bodyParts) {
        bodyParts.forEach(i -> {
            body.addMap(bodyZone, i);
        });
    }

    /**
     * 
     * @param measureBodyZone String
     * @param measure Double
     */
    @Override
    public void addBodyMeasure(final String measureBodyZone, final Double measure) {
        final Optional<BodyData> bodyData = this.currentBodyData;
        if (!bodyData.isPresent()) {
            //throw new ArgumentNotFoundException();
            System.out.println("ArgumentNotFoundException");
            return;
        }
        bodyData.get().addBodyMeasure(measureBodyZone, measure);
    }
    /**
     * 
     * @return true if current User has been set
     */
    @Override
    public boolean isLoginUser() {
        return this.currentUser.isPresent();
    }

    /**
     * 
     * @param code String
     * @return Optional version of a GymTool, mapped for the param code
     */
    private Optional<GymTool> getGymTool(final String code) {
        return Optional.of(this.mapGymTool.get(code));
    }

    /**
     * 
     * @return List<GymTool>
     */
    @Override
    public List<GymTool> getGymToolList() {
        return this.listGymTool;
    }

    /**
     * @return all users in the application
     */
    @Override
    public List<User> getUserList() {
        return this.listUser;
    }

    /**
     * 
     * @return map of Code Tool and relative Tool
     */
    public Map<String, GymTool> getMapGymTool() {
        return this.mapGymTool;
    }

    /**
     * 
     * @param codeRoutine String
     * @return dimension of currentUser's exercise list with codeRoutine
     */
    public int getNumExercise(final String codeRoutine) {
        final Routine routine = this.getRoutine(this.currentUser.get(), codeRoutine);
        return routine.getExerciseList().size();
    }

    /**
     * 
     * @return body of model Application
     */
    public Body getBody() {
        return this.body;
    }

    /**
     * 
     * @return true if exist the current user
     */
    @Override
    public boolean isCurrentUser() {
        return this.currentUser.isPresent();
    }

    /**
     * 
     * @return measure list of user
     */
    @Override
    public List<BodyData> getMeasureList() {
        return this.currentUser.get().getMeasureList();
    }

    @Override
    public List<Routine> getRoutineList() {
        return this.currentUser.get().getRoutineList();
    }

    @Override
    public List<Workout> getWorkoutList() {
        return this.currentUser.get().getWorkoutList();
    }

    @Override
    public List<Double> scoreWorkout() {
        return this.currentUser.get().scoreWorkout();
    }

    /**
     * 
     * @return a map of muscles and relative scores obtained
     */
    @Override
    public Map<String, Double> scoreBodyPart() {
        return this.currentUser.get().scoreBodyPart();
    }

    /**
     * @return a map of body zones and relative scores obtained
     */
    @Override
    public Map<String, Double> scoreBodyZone() {
        return this.currentUser.get().scoreBodyZone();
    }

    /**
     * @return a map of gymTool names and relative scores obtained
     */
    @Override
    public Map<String, Double> scoreGymTool() {
        return this.currentUser.get().scoreGymTool();
    }

    /**
     * @return a map of bodyParts and relative times obtained
     */
    @Override
    public Map<String, Double> timeBodyPart() {
        return this.currentUser.get().timeBodyPart();
    }

    /**
     * @return a map of bodyZones and relative times obtained
     */
    @Override
    public Map<String, Double> timeBodyZone() {
        return this.currentUser.get().timeBodyZone();
    }

    /**
     * @return a map of gymTool names and relative times obtained
     */
    @Override
    public Map<String, Double> timeGymTool() {
        return this.currentUser.get().timeGymTool();
    }

    @Override
    public List<Double> trendBodyMass() {
        return this.currentUser.get().trendBodyMass();
    }

    @Override
    public List<Double> trendBodyBMI() {
        return this.currentUser.get().trendBodyBMI();
    }

    private boolean isAccount(final Account account) {
        return !this.listAccount.stream().noneMatch(i->i.equals(account));
    }

    private Routine getRoutine(final User user, final String code) {
        return user.getRoutineList().stream().filter(i -> i.getCode().equals(code)).findAny().get();
    }

}

class ExistentAccount extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -6352669332423280257L;
}

class ErrorLogin extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -6352669332423280257L;
}
