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

    @Override
    public Account getAccount(final int indice) {
        return this.listAccount.get(indice);
    }

    @Override
    public void addGymTool(final String description, final String nameTool, final String nameImage, final int num, final int valueMin, final int valueMax) {
        final GymTool newTool = new GymToolImpl(description, nameTool, nameImage, num, valueMin, valueMax);
        this.listGymTool.add(newTool);
        this.mapGymTool.put(newTool.getCode(), newTool);
    }

    @Override
    public void addBodyPart(final String toolCode, final String bodyPart, final Double percentage) {
        final GymTool gymTool = this.getGymTool(toolCode).get();
        gymTool.addBodyPart(bodyPart, percentage);
    }

    @Override
    public void addAccount(final String userName, final String password, final String avatar) {
        final Account accountToAdd = new AccountImpl(userName, password, avatar);
        if (!this.isAccount(accountToAdd)) {
            this.currentAccount = Optional.of(accountToAdd);
            this.listAccount.add(this.currentAccount.get());
        }
    }

    @Override
    public void addUser(final String firstName, final String secondName, final int age, final String email) {
        if (checkCurrentAccount()) {
            final Person userData = new PersonImpl(firstName, secondName, age, email);
            this.addUser(this.currentAccount.get(), userData);
        }
    }

    private void addUser(final Account account, final Person person) {
        if (!this.isAccount(account)) {
            this.listAccount.add(account);
        }

        this.currentAccount = Optional.of(account);
        this.listUser.add(new UserImpl(account, person, this.body)); // add this user to list user
    }

    @Override
    public void loginUser(final String userName, final String password) {
        this.getUserList().forEach(i -> {
            final Account account = i.getAccount();
            if (account.getUserName().equals(userName) && account.getPassword().equals(password)) {
                this.currentUser = Optional.of(i);
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
            this.currentUser.get().addRoutine(newRoutine);
        }
    }

    @Override
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
            this.currentUser.get().getMeasureList().add(bodyData);
            this.currentBodyData = Optional.of(bodyData);
        }
    }

    @Override
    public void addMapZone(final String bodyZone, final Collection<String> bodyParts) {
        bodyParts.forEach(i -> {
            body.addMap(bodyZone, i);
        });
    }

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

    @Override
    public boolean isLoginUser() {
        return this.checkOptValue(this.currentUser);
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
        final Optional<Routine> optRoutine = this.getRoutine(this.currentUser.get(), codeRoutine);

        return checkRoutine(optRoutine, codeRoutine) ? optRoutine.get().getExerciseList().size() : 0;
    }

    @Override
    public Body getBody() {
        return this.body;
    }

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

    @Override
    public Map<String, Double> scoreBodyPart() {
        if (!checkCurrentUser()) {
            return new HashMap<>();
        }
        return this.currentUser.get().scoreBodyPart();
    }

    @Override
    public Map<String, Double> scoreBodyZone() {
        if (!checkCurrentUser()) {
            return new HashMap<>();
        }
        return this.currentUser.get().scoreBodyZone();
    }

    @Override
    public Map<String, Double> scoreGymTool() {
        if (!checkCurrentUser()) {
            return new HashMap<>();
        }
        return this.currentUser.get().scoreGymTool();
    }

    @Override
    public Map<String, Double> timeBodyPart() {
        if (!checkCurrentUser()) {
            return new HashMap<>();
        }
        return this.currentUser.get().timeBodyPart();
    }

    @Override
    public Map<String, Double> timeBodyZone() {
        if (!checkCurrentUser()) {
            return new HashMap<>();
        }
        return this.currentUser.get().timeBodyZone();
    }

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
        /*
        final boolean ok = optRoutine.isPresent();
        if (!ok) {
            System.out.println("\n Routine Code not present : " + codeRoutine);
        }
        return ok;
        */
       return checkDoubleOptValue(optRoutine, codeRoutine);
    }

    /**
     * true if optGymTool exists and has name equal to code
     * @param optGymTool Optional<GymTool>
     * @param code String
     * @return a boolean
     */
    private boolean checkGymTool(final Optional<GymTool> optGymTool, final String code) {
        /*
        final boolean ok = optGymTool.isPresent();
        if (!ok) {
            System.out.println("\n GymTool not present code = " + code);
        }

        return ok;
        */
        return checkDoubleOptValue(optGymTool, code);
    }

    /**
     * true if current account exists
     * @return boolean
     */
    private boolean checkCurrentAccount() {
        /*
        final boolean ok = this.currentAccount.isPresent();
        if (!ok) {
            System.out.println("\n Account not set");
        }

        return ok;
        */
        return this.checkOptValue(this.currentAccount);
    }

    /**
     * true if current user exists
     * @return a boolean
     */
    private boolean checkCurrentUser() {
        /*
        final boolean okUser = this.isLoginUser();
        if (!okUser) {
            System.out.println("\n User not set");
        }
        return okUser;
          */
        return this.checkOptValue(this.currentUser);
    }

    /**
     * true if it is set current Workout
     * @return a boolean
     */
    private boolean checkCurrentWorkout() {
        /*
        final boolean ok = this.checkCurrentUser() && this.currentWorkout.isPresent();
        if (!ok) {
            System.out.println("\n Workout not set");
        }

        return ok;
        */
        return this.checkDoubleOptValue(this.currentUser, this.currentWorkout);
    }

    /**
     * true if it is set the currentBodyData
     * @return a boolean
     */
    private boolean checkBodyData() {
        /*
        final boolean ok = this.checkCurrentUser() && this.currentBodyData.isPresent();
        if (!ok) {
            System.out.println("\n Body data not set");
        }

        return ok;
        */
        return this.checkDoubleOptValue(this.currentUser, this.currentBodyData);
    }

    /**
     * 
     * @param optvalue
     * @param code
     * @return
     */
    private<X, Y> boolean checkDoubleOptValue(final Optional<X> optvalue, final String code) {
        final boolean ok = this.checkOptValue(optvalue);

        if (!ok) {
            System.out.print("code = " + code);
        }
        return ok;
    }

    /**
     * 
     * @param optvalue
     * @param optvalue2
     * @return
     */
    private<X, Y> boolean checkDoubleOptValue(final Optional<X> optvalue, final Optional<Y> optvalue2) {
        final boolean ok = this.checkOptValue(optvalue) && this.checkOptValue(optvalue2);
        if (!ok) {
            System.out.println("\n Values Not set");
        }

        return ok;
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
