package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
     -------------------------------------------------------------
*/
public class MyWorkoutBuddyModelImpl implements MyWorkoutBuddyModel {

    private List<User> listUser;
    private List<Account> listAccount;
    private List<GymTool> listGymTool;

    private Map<String, GymTool> mapGymTool;

    private Body body;
    private String currentBodyZone;

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

    @Override
    public Account getAccount(final int indice) {
        return this.listAccount.get(indice);
    }

    @Override
    public void addGymTool(final String description, final String nameTool, final String nameImage, final int num, final int valueMin, final int valueMax) {
        final GymTool newTool = new GymToolImpl.Builder().code(description).name(nameTool).
                imageFile(nameImage).numTools(num).valueMin(valueMin).valueMax(valueMax).build();
        this.listGymTool.add(newTool);
        this.mapGymTool.put(newTool.getCode(), newTool);
    }

    @Override
    public void addBodyPart(final String toolCode, final String bodyPart, final Double percentage) {
        final Optional<GymTool> optGymTool = this.getGymTool(toolCode);
        if (this.checkGymTool(optGymTool, toolCode) && this.checkBodyPart(bodyPart)) {
            optGymTool.get().addBodyPart(bodyPart, percentage);
        }
    }

    @Override
    public void addAccount(final String userName, final String password, final String avatar) {
        final Account accountToAdd = new AccountImpl(userName, password, avatar);
        if (!this.isAccount(accountToAdd)) {
            this.currentAccount = Optional.of(accountToAdd);
            this.listAccount.add(getCurrentAccount());
        }
    }

    @Override
    public void addUser(final String firstName, final String secondName, final int age, final String email) {
        if (checkCurrentAccount()) {
            final Person userData = new PersonImpl(firstName, secondName, age, email);
            this.addUser(getCurrentAccount(), userData);
        }
    }

    @Override
    public void loginUser(final String userName, final String password) {
        this.getUserList().forEach(i -> {
            final Account account = i.getAccount();
            if (account.getUserName().equals(userName) && account.getPassword().equals(password)) {
                this.currentUser = Optional.of(i);
                System.out.println("currentUser = " + this.currentUser.get().getPerson().getFirstName());
            }
        });
    }

    @Override
    public void logoutUser() {
        this.currentUser = Optional.empty();
        this.currentAccount = Optional.empty();
        this.currentWorkout = Optional.empty();
    }

    @Override
    public void addRoutine(final String code, final String nameRoutine, final String target) {
        if (this.checkCurrentUser()) {
            final Routine newRoutine = new RoutineImpl(code, nameRoutine, target);
            this.getCurrentUser().addRoutine(newRoutine);
        }
    }

    @Override
    public void addGymExcercise(final String codeRoutine, final String target, final String codeTool, 
            final int settingValue, final int repetition, final int time, final int numSession, final int pause) {

        final Optional<GymTool> optGymTool = this.getGymTool(codeTool);
        if (this.checkGymTool(optGymTool, codeTool)) {
            final Exercise exercToadd = new ExerciseImpl.Builder().description(target).gymTool(optGymTool.get()).
                    settingValue(settingValue).repetition(repetition).time(time).numSession(numSession).pause(pause).
                    build();
            final Optional<Routine> optRoutine = this.getRoutine(this.getCurrentUser(), codeRoutine); // check valid routine
            if (checkRoutine(optRoutine, codeRoutine)) {
                optRoutine.get().addGymExcercise(exercToadd);
            }
        }
    }

    @Override
    public void addWorkout(final String codeRoutine, final LocalDate localDate, final LocalTime localTime, final boolean state) {
        final Optional<Routine> optRoutine = this.getRoutine(this.getCurrentUser(), codeRoutine);
        final Workout newWorkout;

        if (checkRoutine(optRoutine, codeRoutine)) {
            newWorkout = new WorkoutImpl(optRoutine.get(), localDate, localTime, state);
            this.getCurrentUser().addWorkout(newWorkout);
            this.currentWorkout = Optional.of(newWorkout);
        }
    }

    @Override
    public void addExerciseScore(final Integer numEx, final Integer score) {
        if (this.checkCurrentWorkout()) {
            this.currentWorkout.get().addScore(numEx, score);
        }
    }

    @Override
    public void addDataMeasure(final LocalDate localDate) {
        if (this.checkCurrentUser()) {
            final BodyData bodyData = new BodyData(localDate);
            this.getCurrentUser().getMeasureList().add(bodyData);
            this.currentBodyData = Optional.of(bodyData);
        }
    }

    @Override
    public void body(final String bodyPart, final String bodyZone) {
        this.body.addMap(bodyZone, bodyPart);
        this.currentBodyZone = bodyZone;
    }

    @Override
    public void body(final String bodyPart) {
        this.body.addMap(this.currentBodyZone, bodyPart);
    }

    @Override
    public void body() {
        this.body = new Body();
    }

    @Override
    public void addBodyMeasure(final String measureBody, final Double measure, final boolean firstTime) {
        final Optional<BodyData> bodyData = this.currentBodyData;
        if (this.checkBodyData()) {
            if (firstTime) {
                this.body.addMeasureData(measureBody);
            }
            if (this.checkBodyMeasure(measureBody)) {
                bodyData.get().addBodyMeasure(measureBody, measure);
            }
        }
    }

    @Override
    public Optional<String> getCurrentIdName() {
        return (this.checkCurrentAccount()) ? Optional.of(this.getCurrentAccount().getUserName()) : Optional.empty();
    }

    @Override
    public List<GymTool> getGymToolList() {
        return this.listGymTool;
    }

    @Override
    public List<User> getUserList() {
        return this.listUser;
    }

    @Override
    public Map<String, GymTool> getMapGymTool() {
        return this.mapGymTool;
    }

    @Override
    public int getNumExercise(final String codeRoutine) {
        final Optional<Routine> optRoutine = this.getRoutine(this.getCurrentUser(), codeRoutine);

        return checkRoutine(optRoutine, codeRoutine) ? optRoutine.get().getExerciseList().size() : 0;
    }

    @Override
    public List<BodyData> getMeasureList() {
        if (!checkCurrentUser()) {
            return new ArrayList<>();
        }
        return this.getCurrentUser().getMeasureList();
    }

    @Override
    public List<Routine> getRoutineList() {
        if (!checkCurrentUser()) {
            return new ArrayList<>();
        }
        return this.getCurrentUser().getRoutineList();
    }

    @Override
    public List<Workout> getWorkoutList() {
        if (!this.checkCurrentUser()) {
            return new ArrayList<>();
        }
        return this.getCurrentUser().getWorkoutList();
    }

    @Override
    public List<Double> scoreWorkout() {
        if (!this.checkCurrentWorkout()) {
            return new ArrayList<>();
        }
        return this.getCurrentUser().scoreWorkout();
    }

    @Override
    public Map<String, Double> scoreBodyPart() {
        if (!checkCurrentUser()) {
            return new HashMap<>();
        }
        return this.getCurrentUser().scoreBodyPart();
    }

    @Override
    public Map<String, Double> scoreBodyZone() {
        if (!checkCurrentUser()) {
            return new HashMap<>();
        }
        return this.getCurrentUser().scoreBodyZone();
    }

    @Override
    public Map<String, Double> scoreGymTool() {
        if (!checkCurrentUser()) {
            return new HashMap<>();
        }
        return this.getCurrentUser().scoreGymTool();
    }

    @Override
    public Map<String, Double> timeBodyPart() {
        if (!checkCurrentUser()) {
            return new HashMap<>();
        }
        return this.getCurrentUser().timeBodyPart();
    }

    @Override
    public Map<String, Double> timeBodyZone() {
        if (!checkCurrentUser()) {
            return new HashMap<>();
        }
        return this.getCurrentUser().timeBodyZone();
    }

    @Override
    public Map<String, Double> timeGymTool() {
        if (!checkCurrentUser()) {
            return new HashMap<>();
        }
        return this.getCurrentUser().timeGymTool();
    }

    @Override
    public List<Double> trendBodyBMR() {
        if (!checkCurrentUser()) {
            return new ArrayList<>();
        }
        return this.getCurrentUser().trendBodyBMR();
    }

    @Override
    public List<Double> trendBodyBMI() {
        if (!checkCurrentUser()) {
            return new ArrayList<>();
        }
        return this.getCurrentUser().trendBodyBMI();
    }

    private void addUser(final Account account, final Person person) {
        if (!this.isAccount(account)) {
            this.listAccount.add(account);
        }

        this.currentAccount = Optional.of(account);
        this.listUser.add(new UserImpl(account, person, this.body)); // add this user to list user
    }

    private boolean isAccount(final Account account) {
        return !this.listAccount.stream().noneMatch(i->i.equals(account));
    }

    /**
     * give the current account
     * @return a Account
     */
    private Account getCurrentAccount() {
        return this.currentAccount.get();
    }

    /**
     * give the current user
     * @return a User
     */
    private User getCurrentUser() {
        return this.currentUser.get();
    }

    /**
     * 
     * @param code String
     * @return Optional version of a GymTool, mapped for the param code
     */
    private Optional<GymTool> getGymTool(final String code) {
        return Optional.ofNullable(this.mapGymTool.get(code));
    }

    /**
     * find any routine with code equal to passed code and that belongs to user
     * @param user the passed user
     * @param code the passed routine code
     * @return a routine
     */
    private Optional<Routine> getRoutine(final User user, final String code) {
        return user.getRoutineList().stream().filter(i -> i.getCode().equals(code)).findAny();
    }

    /**
     * true if optRoutine exists and has name equal to codeRoutine
     * @param optRoutine a routine in optional form
     * @param codeRoutine name code of routine passed
     * @return a boolean
     */
    private boolean checkRoutine(final Optional<Routine> optRoutine, final String codeRoutine) {
        final boolean ok = checkOptValue(optRoutine);
        if (!ok) {
            System.out.println("\n Routine Code not present : " + codeRoutine);
        }
        return ok;
    }

    /**
     * true if optGymTool exists and has name equal to code
     * @param optGymTool Optional<GymTool>
     * @param code String
     * @return a boolean
     */
    private boolean checkGymTool(final Optional<GymTool> optGymTool, final String code) {
        final boolean ok = checkOptValue(optGymTool);
        if (!ok) {
            System.out.println("\n GymTool not present code = " + code);
        }
        return ok;
    }

    /**
     * true if current account exists
     * @return boolean
     */
    private boolean checkCurrentAccount() {
        final boolean ok = this.checkOptValue(this.currentAccount);
        if (!ok) {
            System.out.println("\n Account not set");
        }
        return ok;
    }

    /**
     * true if current user exists
     * @return a boolean
     */
    private boolean checkCurrentUser() {
        final boolean ok = this.checkOptValue(this.currentUser);
        if (!ok) {
            System.out.println("\n User not set");
        }
        return ok;
    }

    /**
     * true if it is set current Workout
     * @return a boolean
     */
    private boolean checkCurrentWorkout() {
        final boolean ok = this.checkPairOptValue(this.currentUser, this.currentWorkout);
        if (!ok) {
            System.out.println("\n Workout not set");
        }
        return ok;
    }

    /**
     * true if it is set the currentBodyData
     * @return a boolean
     */
    private boolean checkBodyData() {
        final boolean ok = this.checkPairOptValue(this.currentUser, this.currentBodyData);
        if (!ok) {
            System.out.println("\n Body data not set");
        }
        return ok;
    }

    /**
     * true if it is set the currentBodyData
     * @return a boolean
     */
    private boolean checkBodyMeasure(final String measureBody) {
        final boolean ok = this.checkOptValue(this.body.getMeasure(measureBody));
        if (!ok) {
            System.out.println("\n Body Measure not set : " + measureBody);
        }
        return ok;
    }

    /**
     * true if it is set the currentBodyData
     * @return a boolean
     */
    private boolean checkBodyPart(final String bodyPart) {
        final boolean ok = this.checkOptValue(this.body.getPartZone(bodyPart));
        if (!ok) {
            System.out.println("\n Body Part not present : " + bodyPart);
        }
        return ok;
    }

    /**
     * 
     * @param optvalue
     * @param optvalue2
     * @return
     */
    private<X, Y> boolean checkPairOptValue(final Optional<X> optvalue, final Optional<Y> optvalue2) {
        return this.checkOptValue(optvalue) && this.checkOptValue(optvalue2);
    }

    /**
     * 
     * @param optvalue
     * @return
     */
    private<X> boolean checkOptValue(final Optional<X> optvalue) {
        return optvalue.isPresent();
    }
}

/*
 * 
 *
class ExistentAccount extends Exception {
    private static final long serialVersionUID = -6352669332423280257L;
}
*/

/*
 * 
 * 
class ErrorLogin extends Exception {
    private static final long serialVersionUID = -6352669332423280257L;
}
 */
