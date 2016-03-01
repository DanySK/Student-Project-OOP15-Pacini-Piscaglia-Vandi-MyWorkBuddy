package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 
 *
 */
public class ManageWorkout extends ManageUser {

    private static final int ERR_ROUTINE_UNSET = 40;
    private static final int ERR_WORKOUT_UNSET = 51;
    private static final int ERR_WORKOUT_CODE = 50;
    private static final int ERR_GYMTOOL_CODE = 60;
    private static final int ERR_MEASURE_UNSET = 71;
    private static final int ERR_BODY_UNSET = 81;

    private static final boolean ERR_MSG = false; // with true it gives the possibility to print relative errors

    private static final int TIME_SESSION = 2; // value used to estimate time of exercise: time == number session per time sessions

    private List<GymTool> listGymTool;
    private Map<String, GymTool> mapGymTool;

    private Optional<Routine> currentRoutine = Optional.empty();
    private Optional<Workout> currentWorkout = Optional.empty();
    private Optional<BodyData> currentBodyData = Optional.empty();

    /**
     * 
     */
    public ManageWorkout() {
        super();
        this.listGymTool = new ArrayList<>();
        this.mapGymTool = new HashMap<>();
    }

    /**
     * the current user's logout.
     */
    public void logoutUser() {
        this.setCurrentAccount(Optional.empty());
        this.setCurrentUser(Optional.empty());
        this.currentRoutine = Optional.empty();
        this.currentWorkout = Optional.empty();
    }

    /**
     * add a new workout for current user.
     * @param codeWorkout String
     * @param nameWorkout String
     * @param target String
     */
    public void addWorkout(final String codeWorkout, final String nameWorkout, final String target) {
        if (this.checkCurrentUser()) {
            final Workout newWorkout = new WorkoutImpl(codeWorkout, nameWorkout, target);
            this.getCurrentUser().addWorkout(newWorkout);
            this.currentWorkout = Optional.of(newWorkout);
        }
    }

    /**
     * it removes the Workout with specified code.
     * @param codeWorkout String
     */
    public void removeWorkout(final String codeWorkout) {
        if (this.checkCurrentUser()) {
            if (this.checkCurrentWorkout() && this.currentWorkout.get().getCode().equals(codeWorkout)) { // delete current workout
                this.currentWorkout = Optional.empty();
            }
            this.getCurrentUser().removeWorkout(codeWorkout);
        }
    }

    /**
     * add a new Exercise.
     * @param codeWorkout String
     * @param target String
     * @param codeTool String
     * @param sessions List<Integer> sessions number for each repetition
     */
    public void addGymExcercise(final String codeWorkout, final String target, final String codeTool, 
            final List<Integer> sessions) {
        if (this.checkCurrentUser()) {
            final Optional<GymTool> optGymTool = this.getGymTool(codeTool);
            if (this.checkGymTool(optGymTool, codeTool)) {
                final int repetitions = sessions.size();
                final int numSession = sessions.stream().mapToInt(Integer::intValue).sum();
                final int time = numSession * TIME_SESSION; // in lack of time input, i calculate an estimate time duration
                final Exercise newExerc = new ExerciseImpl.Builder()
                        .description(target)
                        .gymTool(optGymTool.get())
                        .repetition(repetitions)
                        .sessions(numSession)
                        .time(time)
                        .build();
                final Optional<Workout> optWorkout = this.getWorkout(this.getCurrentUser(), codeWorkout); // check valid routine
                if (checkWorkout(optWorkout, codeWorkout)) {
                    optWorkout.get().addGymExcercise(newExerc);
                }
            }
        }
    }

    /**
     * add a new Routine for current User.
     * @param idRoutine Integer
     * @param codeWorkout String
     * @param localDate LocalDate
     * @param state boolean
     */
    public void addRoutine(final int idRoutine, final String codeWorkout, final LocalDate localDate, final boolean state) {
        if (this.checkCurrentUser()) {
            final Optional<Workout> optWorkout = this.getWorkout(this.getCurrentUser(), codeWorkout);
            if (checkWorkout(optWorkout, codeWorkout)) {
                final Routine newRoutine = new RoutineImpl(idRoutine, optWorkout.get(), localDate, state);
                this.getCurrentUser().addRoutine(newRoutine);
                this.currentRoutine = Optional.of(newRoutine);
                this.currentWorkout = optWorkout;
            }
        }
    }

    /**
     * function that removes a generic Routine.
     * @param idRoutine Integer
     */
    public void removeRoutine(final int idRoutine) {
        if (this.checkCurrentUser()) {
            if (this.checkCurrentRoutine() && this.currentRoutine.get().getIdRoutine() == idRoutine) { // delete current routine
                this.currentRoutine = Optional.empty();
            }
            this.getCurrentUser().removeRoutine(idRoutine);
        }
    }

    /**
     * 
     * function that removes the current Routine.
     */
    public void removeRoutine() {
        if (this.checkCurrentRoutine()) {
            this.removeRoutine(this.currentRoutine.get().getIdRoutine());
        }
    }

    /**
     * add a new list of gymTools setting.
     * @param valueList List<Integer>
     */
    public void addExerciseValue(final List<Integer> valueList) {
        if (this.checkCurrentRoutine()) {
            this.currentRoutine.get().addValue(valueList);
        }
    }

    /**
     * add a new data of measure.
     * @param localDate LocalDate
     */
    public void addDataMeasure(final LocalDate localDate) {
        if (this.checkCurrentUser()) {
            final BodyData bodyData = new BodyData(localDate);
            this.getCurrentUser().getMeasureList().add(bodyData);
            this.currentBodyData = Optional.of(bodyData);
        }
    }

    /**
     * add a new BodyMeasure for current User.
     * @param measureBody String
     * @param measure Double
     * @param firstTime boolean
     */
    public void addBodyMeasure(final String measureBody, final Double measure, final boolean firstTime) {
        final Optional<BodyData> bodyData = this.currentBodyData;
        if (this.checkCurrentUser() && this.checkBodyData()) {
            if (firstTime) {
                super.getBody().addMeasureData(measureBody); // fare f(x) in ManageUsersImpl
            }
            if (this.checkBodyMeasure(measureBody)) {
                bodyData.get().addBodyMeasure(measureBody, measure);
            }
        }
    }

    /**
     * add a new GymTool.
     * @param description String
     * @param nameTool String
     * @param num Integer
     * @param valueMin Integer
     * @param valueMax Integer
     */
    public void addGymTool(final String description, final String nameTool, final int num, final int valueMin, final int valueMax) {
        final GymTool newTool = new GymToolImpl.Builder().code(description).name(nameTool).
                numTools(num).valueMin(valueMin).valueMax(valueMax).build(); // ecc.
        this.listGymTool.add(newTool);
        this.mapGymTool.put(newTool.getCode(), newTool);
    }

    /**
     * add a new muscle with relative percentage measure in the tool specified by the toolCode.
     * @param toolCode String
     * @param bodyPart String
     * @param percentage Double
     */
    public void addBodyPart(final String toolCode, final String bodyPart, final Double percentage) {
        final Optional<GymTool> optGymTool = this.getGymTool(toolCode);
        if (this.checkGymTool(optGymTool, toolCode) && this.checkBodyPart(bodyPart)) {
            optGymTool.get().addBodyPart(bodyPart, percentage);
        }
    }

    /**
     * give the list of GymTool in an application.
     * @return a List<GymTool>
     */
    public List<GymTool> getGymToolList() {
        return this.listGymTool;
    }

    /**
     * give the dimension of an exercise list with codeRoutine.
     * @return an Integer
     */
    public int getNumExercise() {
        if (!this.checkCurrentUser() || !checkCurrentWorkout()) {
            return 0;
        }

        return this.currentWorkout.get().getExerciseList().size();
    }

    /**
     * give the current user's measure list.
     * @return a List<BodyData>
     */
    public List<BodyData> getMeasureList() {
        if (!this.checkCurrentUser()) {
            return new ArrayList<>();
        }
        return this.getCurrentUser().getMeasureList();
    }

    /**
     * give the current user's routine list.
     * @return a List<Routine>
     */
    public List<Routine> getRoutineList() {
        if (!this.checkCurrentUser()) {
            return new ArrayList<>();
        }
        return this.getCurrentUser().getRoutineList();
    }

    /**
     * give the current user's Workout list.
     * @return a List<Workout>
     */
    public List<Workout> getWorkoutList() {
        if (!this.checkCurrentUser()) {
            return new ArrayList<>();
        }
        return this.getCurrentUser().getWorkoutList();
    }

    /**
     * give the current user's Workout score list.
     * @return a List<Double>
     */
    public List<Double> scoreRoutine() {
        if (!this.checkCurrentRoutine()) {
            return new ArrayList<>();
        }
        return this.getCurrentUser().scoreRoutine();
    }

    /**
     * it calls current user' s different statistics functionalities about Map return types according to the given string.
     * @param nameStatistic String
     * @return a Map<String, Double>
     */
    public Map<String, Double> statisticMap(final String nameStatistic) {
        if (this.checkCurrentUser()) {
            if (nameStatistic.equals(MethodKey.SCORE_PART.toString())) {
                return this.getCurrentUser().scoreBodyPart();
            } else if (nameStatistic.equals(MethodKey.SCORE_ZONE.toString())) {
                return this.getCurrentUser().scoreBodyZone();
            } else if (nameStatistic.equals(MethodKey.SCORE_TOOL.toString())) {
                return this.getCurrentUser().scoreGymTool();
            } else if (nameStatistic.equals(MethodKey.TIME_PART.toString())) {
                return this.getCurrentUser().timeBodyPart();
            } else if (nameStatistic.equals(MethodKey.TIME_ZONE.toString())) {
                return this.getCurrentUser().timeBodyZone();
            } else if (nameStatistic.equals(MethodKey.TIME_TOOL.toString())) {
                return this.getCurrentUser().timeGymTool();
            }
        }
        return new HashMap<>();
    }

    /**
     * it calls current user' s different statistics functionalities about list return type according to the given string.
     * @param nameStatistic String
     * @return a List<Double>
     */
    public List<Double> trendList(final String nameStatistic) {
        if (this.checkCurrentUser()) {
            if (nameStatistic.equals(MethodKey.TREND_BMI.toString())) {
                return this.getCurrentUser().trendBodyBMI();
            } else if (nameStatistic.equals(MethodKey.TREND_BMR.toString())) {
                return this.getCurrentUser().trendBodyBMR();
            } else if (nameStatistic.equals(MethodKey.TREND_LBM.toString())) {
                return this.getCurrentUser().trendBodyLBM();
            }
        }
            return new ArrayList<>();
    }

    /**
     * it gives all names of statistic functions.
     * @return a String
     */
    public String getStatisticsName() {
        final StringBuilder sb = new StringBuilder();
        for (MethodKey key : MethodKey.values()) {
            sb.append(key.toString()).append(" ");
        }
        return sb.toString();
    }

    /**
     * find any workout with code equal to passed code and that belongs to user
     * @param user the passed user
     * @param code the passed workout code
     * @return a Optional<Workout>
     */
    private Optional<Workout> getWorkout(final User user, final String code) {
        return user.getWorkoutList().stream().filter(i -> i.getCode().equals(code)).findAny();
    }

    /**
     * it gives the version of a GymTool, mapped for the parameter code
     * @param code String
     * @return Optional<GymTool>
     */
    private Optional<GymTool> getGymTool(final String code) {
        return Optional.ofNullable(this.mapGymTool.get(code));
    }

    /**
     * true if it is set the currentBodyData
     * @return a boolean
     */
    private boolean checkBodyData() {
        final boolean ok = this.checkOptValue(this.currentBodyData);
        if (!ok) {
            this.outMsgError(ERR_BODY_UNSET, "");
        }
        return ok;
    }

    /**
     * true if it is set the currentBodyData
     * @return a boolean
     */
    private boolean checkBodyMeasure(final String measureBody) {
        final boolean ok = this.checkOptValue(super.getBody().getMeasure(measureBody)); // fare f(x) in ManageUsersImpl
        if (!ok) {
            this.outMsgError(ERR_MEASURE_UNSET, measureBody);
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
            this.outMsgError(ERR_GYMTOOL_CODE, code);
        }
        return ok;
    }

    /**
     * true if optWorkout exists and has name equal to codeWorkout
     * @param optWorkout a workout in optional form
     * @param codeWorkout name code of workout passed
     * @return a boolean
     */
    private boolean checkWorkout(final Optional<Workout> optWorkout, final String codeWorkout) {
        final boolean ok = checkOptValue(optWorkout);
        if (!ok) {
            this.outMsgError(ERR_WORKOUT_CODE, codeWorkout);
        }
        return ok;
    }

    /**
     * true if it is set current Routine
     * @return a boolean
     */
    private boolean checkCurrentRoutine() {
        final boolean ok = this.checkOptValue(this.currentRoutine);
        if (!ok) {
            this.outMsgError(ERR_ROUTINE_UNSET, "");
        }
        return ok;
    }

    /**
     * true if it is set current Routine
     * @return a boolean
     */
    private boolean checkCurrentWorkout() {
        final boolean ok = this.checkOptValue(this.currentWorkout);
        if (!ok) {
            this.outMsgError(ERR_WORKOUT_UNSET, "");
        }
        return ok;
    }

    private void outMsgError(final Integer errNum, final String msg) {
        if (!ERR_MSG) {
            return;
        }
        switch (errNum) {
        case ERR_ROUTINE_UNSET :
            System.out.println("ERROR: Routine not set " + msg);
            break;
        case ERR_WORKOUT_CODE :
            System.out.println("ERROR: Workout not present code = " + msg);
            break;
        case ERR_WORKOUT_UNSET :
            System.out.println("ERROR: Workout not set " + msg);
            break;
        case ERR_GYMTOOL_CODE :
            System.out.println("ERROR: GymTool not present code = " + msg);
            break;
        case ERR_MEASURE_UNSET :
            System.out.println("ERROR: Body Measure not set " + msg);
            break;
        case ERR_BODY_UNSET :
            System.out.println("ERROR: Body data not set " + msg);
            break;
        default :
            System.out.println("ERROR " + msg);
            break;
        }
    }
}

enum MethodKey {
    SCORE_PART, SCORE_ZONE, SCORE_TOOL,
    TIME_PART, TIME_ZONE, TIME_TOOL,
    TREND_BMR, TREND_BMI, TREND_LBM;

    public String toString() {
        return name().substring(0, 1).concat(name().substring(1).toLowerCase());
    }
}
