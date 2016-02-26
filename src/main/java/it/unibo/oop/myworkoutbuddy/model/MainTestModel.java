package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class for testing.
 * Here an example of using model utilities.
 */
public final class MainTestModel {

    /*
     * value of try
     */
    private static final Integer AGE_TRY_1 = 20;
    private static final Integer AGE_TRY_2 = 25;
    private static final Integer AGE_TRY_3 = 30;
    private static final Double VAL_MIN_DOUBLE_TRY = 20.00;
    private static final Double VAL_MIN1_DOUBLE_TRY = 30.00;
    private static final Double VAL_MID_DOUBLE_TRY = 50.00;
    private static final Double VAL_MAX_DOUBLE_TRY = 80.00;

    private static final Map<String, Double> TIME_MAP = new HashMap<>();

    private MainTestModel() {
    }

    /**
     * 
     * @param args parameters for the main
     * @throws NullPointerException exception for nullPointer
     * @throws IllegalArgumentException exception for not supported values
     */
    public static void main(final String[] args) throws NullPointerException, IllegalArgumentException {

        /*
         * declaration of Model Interface
         */
        final MyWorkoutBuddyModel model;

        System.out.println("\n Start Model");
        /*
         * a new Model object
         */
        model = new MyWorkoutBuddyModelImpl();

        /* --- LOAD DATA ------*/
        System.out.println("\n Load Data");
        testLoadData(model);

        /* --- PRINTS ALL APPLICATION DATA OF ALL USERS ------*/

        System.out.println("\n ==== GYM TOOL LIST ==== ");
        System.out.println(model.getGymToolList());

        System.out.println("\n ==== USERS LIST ==== ");
        System.out.println(model.getUserList());

        final int num = model.getUserList().size();

        for (int i = 1; i <= num; i++) { // Cycle to test all accounts

        /* --- LOGIN USER ------*/
        /*
         * When you login the current user is the person who has logged in
         */
        System.out.println("\n ==== LOGIN USER ==== ");
        model.loginUser("account" + i, "password" + i);

        System.out.println("\n currentAccount = " + model.getCurrentNameAccount());

        // WORKOUT DATE LOGIN USER: cycle of generation test data for login User
        testLoginUser(model);

        /* --- PRINTS USER DATA AND STATISTICS ------*/
        System.out.println("\n MeasureList = " + model.getMeasureList());

        System.out.println("\n ==== ROUTINE LIST  ==== ");
        System.out.println(" Routine List = " + model.getRoutineList());

        System.out.println("\n ==== WORKOUT LIST  ==== ");
        System.out.println(" WorkoutList = " + model.getWorkoutList());

        // statisticMap(final String nameStatistic)

        System.out.println("\n ==== STATISTICS SCORES : ");
        System.out.println(" ScoreWorkout = " + model.scoreWorkout());
        System.out.println(" ScoreBodyPart = " + model.scoreBodyPart());
        System.out.println(" ScoreBodyZone = " + model.scoreBodyZone());
        System.out.println(" ScoreGymTool = " + model.scoreGymTool());

        System.out.println("\n ==== STATISTICS TIME : ");
        System.out.println(" TimeBodyPart = " + model.timeBodyPart());
        System.out.println(" TimeBodyZone = " + model.timeBodyZone());
        System.out.println(" TimeGymTool = " + model.timeGymTool());

        System.out.println("\n ==== STATISTICS BODY : ");
        System.out.println(" TrendBodyBMI = " + model.trendBodyBMI() + " ");
        System.out.println(" TrendBodyBMR = " + model.trendBodyBMR() + " [kcal/day]");

        final Map<String, Double> mapGymTool = model.timeGymTool();
        mergeMap(mapGymTool);

        System.out.println("\n ==== LOGOUT USER : ");
        model.logoutUser();
        }

        System.out.println("\n ==== GLOBAL STATISTICS : ");
        System.out.println("\n TimeGymTool = " + TIME_MAP);
}

    /**
     * test method for try data building.
     * @param model MyWorkoutBuddyModel
     */
    public static void testLoadData(final MyWorkoutBuddyModel model) {

        /* 
         * You may create the body for the model also if the current user is not set
         */
        model.body(); // new body

        /*
         * Here it is an example how to personalize your body
         */
        model.body("HAMSTRINGS", "LEG"); // mapping: part -> zone
        model.body("QUADRICEPS");
        model.body("CALVES");

        model.body("BICEPS", "ARM"); // mapping: part -> zone
        model.body("FORE_ARM");

        model.body("PECTORALIS_MAJOR", "CHEST"); // mapping: part -> zone
        model.body("PECTORALIS_MINOR");
        model.body("ABDOMINALS");
        // end Body definition

        /* GYMTOOLS: load GymTool informations
         * At any time you may add a new Gym Tool with : description, name, route image file and integer values required by it
         */
        // description, image, num, valueMin, valueMax
        model.addGymTool("T1", "Tapis Roulant", "image1.png", 10, 1, 10);
        model.addGymTool("T2", "Cyclette", "image2.png", 10, 1, 10);
        model.addGymTool("T3", "Hand Weight", "image3.png", 10, 1, 10);

        // setting body parts for each GymTool
        /*
         * If you have defined before :
         * 1) the body parts(muscles) to add;
         * 2) the tools that use the body parts specified;
         * You may add a new percentage value for a set of body parts in the specified tools
         */
        model.addBodyPart("T1", "HAMSTRINGS", VAL_MIN_DOUBLE_TRY);
        model.addBodyPart("T1", "QUADRICEPS", VAL_MAX_DOUBLE_TRY);

        model.addBodyPart("T2", "HAMSTRINGS", VAL_MIN1_DOUBLE_TRY);
        model.addBodyPart("T2", "CALVES", VAL_MID_DOUBLE_TRY);
        model.addBodyPart("T2", "BICEPS", VAL_MIN_DOUBLE_TRY);

        model.addBodyPart("T3", "BICEPS", VAL_MID_DOUBLE_TRY);
        model.addBodyPart("T3", "PECTORALIS_MINOR", VAL_MIN_DOUBLE_TRY);
        model.addBodyPart("T3", "PECTORALIS_MAJOR", VAL_MIN1_DOUBLE_TRY);

       /*
        *  USER: User (Account, Person)  make User Test data
        *       Account : UserName, Password, Avatar image Person 
        *       Person  : First Name, Last Name, age, email 
        * 
        * When you add a new User with appropriated Account you automatically set the current User
        */
        model.addAccount("account1", "password1", "avatar1.png");
        model.addUser("Paolo", "Rossi", AGE_TRY_1, "paolo.rossi@studio.unibo.it");

        model.addAccount("account2", "password2", "avatar2.png");
        model.addUser("Gino", "Bianchi", AGE_TRY_2, "gino.bianchi@studio.unibo.it");

        model.addAccount("account3", "password3", "avatar3.png");
        model.addUser("Mario", "Verdi", AGE_TRY_3, "mario.verdi@studio.unibo.it");

        /*
         * ... loading other users
         */
    }

    /**
     * test method for try data loading.
     * @param model MyWorkoutBuddyModel
     */
    public static void testLoginUser(final MyWorkoutBuddyModel model) {

        /*Add a measure body: true(init) = new Measure*/
        model.addDataMeasure(LocalDate.now());

        /*
         * you may add all the measure you want, which refer to the corresponding human feature you want to give, if the current user is set
         */
        final Double height = 1.80;
        final Double weight = 65.00;
        final Double upperBody = 80.00;
        final Double lowerBody = 60.00;
        model.addBodyMeasure("HEIGHT", height, true);
        model.addBodyMeasure("WEIGHT", weight, true);
        model.addBodyMeasure("UPPER_BODY", upperBody, true);
        model.addBodyMeasure("LOWER_BODY", lowerBody, true);

        /* 
         * ROUTINE: load Routines for Current User
         * You may add a new routine if the current user is set
         */
        /* Routine: code , name, target(aim of routine) */
        model.addRoutine("R1", "Routine1", "BODY_BUILDING");

        /*
         * add a new GymExercise to current user
         */
        /* Exercise data for Routine: codeRoutine(identifier code), description(what the routine must do?), 
         * codeGymTool(what's its name ?), settingValue(difficulty grade), repetition(how much i must repeat an entire exercise?), 
         * time(how long does it last in a repetition?), numSession(how many minor cycles i have to do for complete a repetition?), pause
         * (i have right a pause of N minutes ended a Session) */
        final Integer settingValueTry = 5;
        model.addGymExcercise("R1", "Warming", "T1", settingValueTry, 10, 3, 10, 2);
        model.addGymExcercise("R1", "Running", "T1", settingValueTry, 10, 3, 10, 2);
        model.addGymExcercise("R1", "Tonifing", "T2", settingValueTry, 10, 3, 10, 2);
        model.addGymExcercise("R1", "Swimming", "T3", settingValueTry, 10, 3, 10, 2);
        model.addGymExcercise("R1", "Swimming", "T2", settingValueTry, 10, 3, 10, 2);

        /* 
         * WORKOUT: Workout Cycle of current User
         * Here an example of exercise scores input
         */
        final Integer numTryCycle = 5;
        for (int k = 0; k < numTryCycle; k++) {
            /*Workout : codeRoutine, date, hour, state*/
            model.addWorkout("R1", LocalDate.now(), LocalTime.now(), true);

            /* set scores */
            final List<Integer> scoreList = new ArrayList<>(); // new List of scores (it's an example)
            for (int i = 0; i < model.getNumExercise(); i++) {
                /*exerciseScore : numExercise, scoreExercise*/
                final int score = 1 + (k + i);
                scoreList.add(score); // add a new score to temporary list
            }
            model.addExerciseScore(scoreList); // add all temporary list scores
        }

        /*Add a new measure body*/
        model.addDataMeasure(LocalDate.now());

        /*
         * With false option, you may add a new value for a human feature if and only if the human feature exists.
         * The existence condition for a human feature is the previous declaration of it
         */
        final Double heightNew = 1.80;
        final Double weightNew = 65.00;
        final Double upperBodyNew = 82.00;
        final Double lowerBodyNew = 63.00;
        model.addBodyMeasure("HEIGHT", heightNew, false);
        model.addBodyMeasure("WEIGHT", weightNew, false);
        model.addBodyMeasure("UPPER_BODY", upperBodyNew, false);
        model.addBodyMeasure("LOWER_BODY", lowerBodyNew, false);

}
    /*
     * function visualization of all users' data with a common generic type
     */
    private static void mergeMap(final Map<String, Double> mapGymTool) {
        mapGymTool.keySet().stream().forEach(i -> {
            final Double newValue = mapGymTool.get(i);
            TIME_MAP.merge(i, newValue, (d1, d2) -> {
                return newValue + TIME_MAP.get(i);
            });
        });
    }
}
