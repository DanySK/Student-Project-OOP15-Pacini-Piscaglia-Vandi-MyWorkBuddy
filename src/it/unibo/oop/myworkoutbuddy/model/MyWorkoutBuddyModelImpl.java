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
        if (checkCurrentAccount()) {
            final Person userData = new PersonImpl(firstName, secondName, age, email);
            this.addUser(this.currentAccount.get(), userData);
        }
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
     * exit currentUser,
     * exit currentAccount,
     * exit currentWorkout.
     */
    @Override
    public void logoutUser() {
        this.currentUser = Optional.empty();
        this.currentAccount = Optional.empty();
        this.currentWorkout = Optional.empty();
    }

    /**
     * @param code String
     * @param nameRoutine String
     * @param target String
     */
    @Override
    public void addRoutine(final String code, final String nameRoutine, final String target) {
        if (this.checkCurrentUser()) {
            final Routine newRoutine = new RoutineImpl(code, nameRoutine, target);
            this.currentUser.get().addRoutine(newRoutine);
        }
    }

    /**
     * 
     * @param codeRoutine String
     * @param target String
     * @param codeTool String
     * @param settingValue Integer
     * @param repetition Integer
     * @param time Integer
     * @param numSession Integer
     * @param pause Integer
     */
    public void addGymExcercise(final String codeRoutine, final String target, final String codeTool, 
            final int settingValue, final int repetition, final int time, final int numSession, final int pause) {

        final Optional<GymTool> optGymTool = this.getGymTool(codeTool);
        if (this.checkGymTool(optGymTool, codeTool)) {
            final Exercise exercToadd = new ExerciseImpl(target, optGymTool.get(), settingValue, repetition, time, numSession, pause);

            final Optional<Routine> optRoutine = this.getRoutine(this.currentUser.get(), codeRoutine); // check valid routine
            if (checkRoutine(optRoutine, codeRoutine)) {
                optRoutine.get().addGymExcercise(exercToadd);
            }
        }
    }

    /**
     * @param codeRoutine String
     * @param localDate LocalDate
     * @param localTime LocalTime
     * @param state boolean
     */
    @Override
    public void addWorkout(final String codeRoutine, final LocalDate localDate, final LocalTime localTime, final boolean state) {
        final Optional<Routine> optRoutine = this.getRoutine(this.currentUser.get(), codeRoutine);
        final Workout newWorkout;

        if (checkRoutine(optRoutine, codeRoutine)) {
            newWorkout = new WorkoutImpl(optRoutine.get(), localDate, localTime, state);
            this.currentUser.get().addWorkout(newWorkout);
            this.currentWorkout = Optional.of(newWorkout);
        }
    }

    /**
     * 
     * @param numEx Integer
     * @param score Integer
     */
    public void addExerciseScore(final Integer numEx, final Integer score) {
        if (this.checkCurrentWorkout()) {
            this.currentWorkout.get().addScore(numEx, score);
        }
    }

    /**
     * 
     * @param localDate LocalDate
     */
    @Override
    public void addDataMeasure(final LocalDate localDate) {
        if (this.checkCurrentUser()) {
            final BodyData bodyData = new BodyData(localDate);
            this.currentUser.get().getMeasureList().add(bodyData);
            this.currentBodyData = Optional.of(bodyData);
        }
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
     * @param measureBody String
     * @param measure Double
     */
    @Override
    public void addBodyMeasure(final String measureBody, final Double measure, final boolean firstTime) {
        final Optional<BodyData> bodyData = this.currentBodyData;
        if (this.checkBodyData()) {
            if (firstTime) {
                this.body.addMeasureData(measureBody);
            }
            if (this.body.getMeasure(measureBody).isPresent()) {
                bodyData.get().addBodyMeasure(measureBody, measure);
            }
        }
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
        final GymTool gymTool = this.mapGymTool.get(code);
        if (gymTool != null) {
            return Optional.of(gymTool);
        }
        return Optional.empty();
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
        final Optional<Routine> optRoutine = this.getRoutine(this.currentUser.get(), codeRoutine);

        return checkRoutine(optRoutine, codeRoutine) ? optRoutine.get().getExerciseList().size() : 0;
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
     * @return measure list of user
     */
    @Override
    public List<BodyData> getMeasureList() {
        if (!checkCurrentUser()) {
            return new ArrayList<>();
        }
        return this.currentUser.get().getMeasureList();
    }

    @Override
    public List<Routine> getRoutineList() {
        if (!checkCurrentUser()) {
            return new ArrayList<>();
        }
        return this.currentUser.get().getRoutineList();
    }

    @Override
    public List<Workout> getWorkoutList() {
        if (!this.checkCurrentUser()) {
            return new ArrayList<>();
        }
        return this.currentUser.get().getWorkoutList();
    }

    @Override
    public List<Double> scoreWorkout() {
        if (!this.checkCurrentWorkout()) {
            return new ArrayList<>();
        }
        return this.currentUser.get().scoreWorkout();
    }

    /**
     * 
     * @return a map of muscles and relative scores obtained
     */
    @Override
    public Map<String, Double> scoreBodyPart() {
        if (!checkCurrentUser()) {
            return new HashMap<>();
        }
        return this.currentUser.get().scoreBodyPart();
    }

    /**
     * @return a map of body zones and relative scores obtained
     */
    @Override
    public Map<String, Double> scoreBodyZone() {
        if (!checkCurrentUser()) {
            return new HashMap<>();
        }
        return this.currentUser.get().scoreBodyZone();
    }

    /**
     * @return a map of gymTool names and relative scores obtained
     */
    @Override
    public Map<String, Double> scoreGymTool() {
        if (!checkCurrentUser()) {
            return new HashMap<>();
        }
        return this.currentUser.get().scoreGymTool();
    }

    /**
     * @return a map of bodyParts and relative times obtained
     */
    @Override
    public Map<String, Double> timeBodyPart() {
        if (!checkCurrentUser()) {
            return new HashMap<>();
        }
        return this.currentUser.get().timeBodyPart();
    }

    /**
     * @return a map of bodyZones and relative times obtained
     */
    @Override
    public Map<String, Double> timeBodyZone() {
        if (!checkCurrentUser()) {
            return new HashMap<>();
        }
        return this.currentUser.get().timeBodyZone();
    }

    /**
     * @return a map of gymTool names and relative times obtained
     */
    @Override
    public Map<String, Double> timeGymTool() {
        if (!checkCurrentUser()) {
            return new HashMap<>();
        }
        return this.currentUser.get().timeGymTool();
    }

    @Override
    public List<Double> trendBodyMass() {
        if (!checkCurrentUser()) {
            return new ArrayList<>();
        }
        return this.currentUser.get().trendBodyMass();
    }

    @Override
    public List<Double> trendBodyBMI() {
        if (!checkCurrentUser()) {
            return new ArrayList<>();
        }
        return this.currentUser.get().trendBodyBMI();
    }

    private boolean isAccount(final Account account) {
        return !this.listAccount.stream().noneMatch(i->i.equals(account));
    }

    private Optional<Routine> getRoutine(final User user, final String code) {
        return user.getRoutineList().stream().filter(i -> i.getCode().equals(code)).findAny();
    }

    private boolean checkRoutine(final Optional<Routine> optRoutine, final String codeRoutine) {
        final boolean ok = optRoutine.isPresent();
        if (!ok) {
            System.out.println("\n Routine Code not present : " + codeRoutine);
        }
        return ok;
    }

    private boolean checkCurrentAccount() {
        final boolean ok = this.currentAccount.isPresent();
        if (!ok) {
            System.out.println("\n Account not set");
        }

        return ok;
    }

    private boolean checkCurrentUser() {
        final boolean okUser = this.isLoginUser();
        if (!okUser) {
            System.out.println("\n User not set");
        }

        return okUser;
    }

    private boolean checkCurrentWorkout() {
        final boolean ok = this.checkCurrentUser() && this.currentWorkout.isPresent();
        if (!ok) {
            System.out.println("\n Workout not set");
        }

        return ok;
    }

    private boolean checkBodyData() {
        final boolean ok = this.checkCurrentUser() && this.currentBodyData.isPresent();
        if (!ok) {
            System.out.println("\n Body data not set");
        }

        return ok;
    }

    /**
     * 
     * @param optGymTool Optional<GymTool>
     * @return
     */
    private boolean checkGymTool(final Optional<GymTool> optGymTool, final String code) {
        final boolean ok = optGymTool.isPresent();
        if (!ok) {
            System.out.println("\n GymTool not present code = " + code);
        }

        return ok;
    }
}


enum ErrorEnum {
    ERR_USER, ERR_ACCOUNT, ERR_WORKOUT, ERR_ROUTINE, ERR_MEASURE;
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

class ErrorModel extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = -6352669332423280257L;

    ErrorModel(final ErrorEnum error) {
        if (error == ErrorEnum.ERR_USER) {
            System.out.println("Error User " + error.ordinal());
        }
        else if (error == ErrorEnum.ERR_ACCOUNT) {
            System.out.println("Error Account " + error.ordinal());
        }
        else if (error == ErrorEnum.ERR_WORKOUT) {
            System.out.println("Error Workout " + error.ordinal());
        }
        else if (error == ErrorEnum.ERR_ROUTINE) {
            System.out.println("Error Routine " + error.ordinal());
        }
        else if (error == ErrorEnum.ERR_MEASURE) {
            System.out.println("Error Routine " + error.ordinal());
        }
        else {
            System.out.println("Error Model " + error.ordinal());
        }
    }
}
