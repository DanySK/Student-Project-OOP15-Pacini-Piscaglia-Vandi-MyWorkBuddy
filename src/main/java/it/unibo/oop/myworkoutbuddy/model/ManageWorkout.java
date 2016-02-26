package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;
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

    private List<GymTool> listGymTool;
    private Map<String, GymTool> mapGymTool;

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
        this.currentWorkout = Optional.empty();
    }

    /**
     * add a new routine for current user.
     * @param code String
     * @param nameRoutine String
     * @param target String
     */
    public void addRoutine(final String code, final String nameRoutine, final String target) {
        if (this.checkCurrentUser()) {
            final Routine newRoutine = new RoutineImpl(code, nameRoutine, target);
            this.getCurrentUser().addRoutine(newRoutine);
        }
    }

    /**
     * add a new Exercise.
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
        if (this.checkCurrentUser()) {
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
    }

    /**
     * add a new Workout for current User.
     * @param codeRoutine String
     * @param localDate LocalDate
     * @param localTime LocalTime
     * @param state boolean
     */
    public void addWorkout(final String codeRoutine, final LocalDate localDate, final LocalTime localTime, final boolean state) {
        if (this.checkCurrentUser()) {
            final Optional<Routine> optRoutine = this.getRoutine(this.getCurrentUser(), codeRoutine);
            if (checkRoutine(optRoutine, codeRoutine)) {
                final Workout newWorkout = new WorkoutImpl(optRoutine.get(), localDate, localTime, state);
                this.getCurrentUser().addWorkout(newWorkout);
                this.currentWorkout = Optional.of(newWorkout);
            }
        }
    }

    /**
     * add a new scoreList.
     * @param scoreList List<Integer>
     */
    public void addExerciseScore(final List<Integer> scoreList) {
        if (this.checkCurrentWorkout()) {
            this.currentWorkout.get().addScore(scoreList);
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
     * @param nameImage String
     * @param num Integer
     * @param valueMin Integer
     * @param valueMax Integer
     */
    public void addGymTool(final String description, final String nameTool, final String nameImage, final int num, final int valueMin, final int valueMax) {
        final GymTool newTool = new GymToolImpl.Builder().code(description).name(nameTool).
                imageFile(nameImage).numTools(num).valueMin(valueMin).valueMax(valueMax).build();
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
        if (!checkCurrentUser()) {
            return 0;
        }
        final String codeRoutine = this.currentWorkout.get().getRoutine().getCode();
        final Optional<Routine> optRoutine = this.getRoutine(this.getCurrentUser(), codeRoutine);
        return checkRoutine(optRoutine, codeRoutine) ? optRoutine.get().getExerciseList().size() : 0;
    }

    /**
     * give the current user's measure list.
     * @return a List<BodyData>
     */
    public List<BodyData> getMeasureList() {
        if (!checkCurrentUser()) {
            return new ArrayList<>();
        }
        return this.getCurrentUser().getMeasureList();
    }

    /**
     * give the current user's routine list.
     * @return a List<Routine>
     */
    public List<Routine> getRoutineList() {
        if (!checkCurrentUser()) {
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
    public List<Double> scoreWorkout() {
        if (!this.checkCurrentWorkout()) {
            return new ArrayList<>();
        }
        return this.getCurrentUser().scoreWorkout();
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
            }
        }
            return new ArrayList<>();
    }

    /**
     * it gives all names of statistic functions.
     * @return a String
     */
    public String getStatisticsName() {
        final String statisticsName = "";
        for (MethodKey key : MethodKey.values()) {
            statisticsName.concat(key.toString()).concat(" ");
        }
        return statisticsName;
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
     * 
     * @param code String
     * @return Optional version of a GymTool, mapped for the param code
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
            System.out.println("\n Body data not set");
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
            System.out.println("\n Body Measure not set : " + measureBody);
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
     * true if it is set current Workout
     * @return a boolean
     */
    private boolean checkCurrentWorkout() {
        final boolean ok = this.checkOptValue(this.currentWorkout);
        if (!ok) {
            System.out.println("\n Workout not set");
        }
        return ok;
    }
}

enum MethodKey {
    SCORE_PART, SCORE_ZONE, SCORE_TOOL,
    TIME_PART, TIME_ZONE, TIME_TOOL,
    TREND_BMR, TREND_BMI;

    public String toString() {
        return name().substring(0, 1).concat(name().substring(1).toLowerCase());
    }
}
