package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class for testing.
 * Here an example of using model utilities.
 */
public final class MainTestModel {

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

        System.out.println("\n currentAccount = " + model.getCurrentUserName());

        // WORKOUT DATE LOGIN USER: cycle of generation test data for login User
        testLoginUser(model); //

        /* --- PRINTS USER DATA AND STATISTICS ------*/
        System.out.println("\n MeasureList = " + model.getMeasureList());

        System.out.println("\n ==== ROUTINE LIST  ==== ");
        System.out.println(" Routine List = " + model.getRoutineList());

        System.out.println("\n ==== WORKOUT LIST  ==== ");
        System.out.println(" WorkoutList = " + model.getWorkoutList());

        // statisticMap(final String nameStatistic)

        System.out.println("\n ==== STATISTICS SCORES : ");
        System.out.println(" ScoreRoutine = " + model.scoreRoutine());
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
        model.resetBody(); // new body

        /*
         * Here it is an example how to personalize your body
         */
        model.setBody("HAMSTRINGS", "LEG"); // mapping: part -> zone
        model.setBody("QUADRICEPS");
        model.setBody("CALVES");

        model.setBody("BICEPS", "ARM"); // mapping: part -> zone
        model.setBody("FORE_ARM");

        model.setBody("PECTORALIS_MAJOR", "CHEST"); // mapping: part -> zone
        model.setBody("PECTORALIS_MINOR");
        model.setBody("ABDOMINALS");
        // end Body definition

        /* GYMTOOLS: load GymTool informations
         * At any time you may add a new Gym Tool with : description, name, route image file and integer values required by it
         */
        // description, num, valueMin, valueMax
        model.addGymTool("T1", "Tapis Roulant", 10, 1, 10);
        model.addGymTool("T2", "Cyclette", 10, 1, 10);
        model.addGymTool("T3", "Hand Weight", 10, 1, 10);

        // setting body parts for each GymTool
        /*
         * If you have defined before :
         * 1) the body parts(muscles) to add;
         * 2) the tools that use the body parts specified;
         * You may add a new percentage value for a set of body parts in the specified tools
         */
        model.addBodyPart("T1", "HAMSTRINGS", 20.00);
        model.addBodyPart("T1", "QUADRICEPS", 80.00);

        model.addBodyPart("T2", "HAMSTRINGS", 30.00);
        model.addBodyPart("T2", "CALVES", 50.00);
        model.addBodyPart("T2", "BICEPS", 20.00);

        model.addBodyPart("T3", "BICEPS", 50.00);
        model.addBodyPart("T3", "PECTORALIS_MINOR", 20.00);
        model.addBodyPart("T3", "PECTORALIS_MAJOR", 30.00);

       /*
        *  USER: User (Account, Person)  make User Test data
        *       Account : UserName, Password, Avatar image Person 
        *       Person  : First Name, Last Name, age, email 
        * 
        * When you add a new User with appropriated Account you automatically set the current User
        */
        model.addAccount("account1", "password1");
        model.addUser("Paolo", "Rossi", 20, "paolo.rossi@studio.unibo.it");

        model.addAccount("account2", "password2");
        model.addUser("Gino", "Bianchi", 25, "gino.bianchi@studio.unibo.it");

        model.addAccount("account3", "password3");
        model.addUser("Mario", "Verdi", 30, "mario.verdi@studio.unibo.it");

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
        final Double weight = 70.00;
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
        /* Workout: code , name, target(aim of routine) */
        model.addWorkout("W1", "Workout1", "BODY_BUILDING");

        /*
         * add a new GymExercise to current workout
         */
        /* Exercise data for Workout: codeWorkout(identifier code), description(what the routine must do?), 
         * codeGymTool(what's its name ?)*/

        model.addGymExcercise("W1", "Warming", "T1", new ArrayList<>(Arrays.asList(1, 2, 3)));
        model.addGymExcercise("W1", "Running", "T1", new ArrayList<>(Arrays.asList(3, 4, 3)));
        model.addGymExcercise("W1", "Tonifing", "T2", new ArrayList<>(Arrays.asList(4, 7, 8)));
        model.addGymExcercise("W1", "Swimming", "T3", new ArrayList<>(Arrays.asList(1, 2, 3)));
        model.addGymExcercise("W1", "Swimming", "T2", new ArrayList<>(Arrays.asList(1, 2, 3)));

        /* 
         * WORKOUT: Workout Cycle of current User
         * Here an example of exercise scores input
         */
        final Integer numTryCycle = 5;
        for (int k = 0; k < numTryCycle; k++) {
            /*Routine : codeWorkout, date*/
            model.addRoutine("W1", LocalDate.now()); //

            /* set scores */
            final List<Integer> valueList = new ArrayList<>(); // new List of scores (it's an example)
            for (int i = 0; i < model.getNumExercise(); i++) { //
                /*exerciseScore : numExercise, scoreExercise*/
                final int value = 1 + (k + i);
                valueList.add(value); // add a new score to temporary list
            }
            model.addExerciseValue(valueList); // add all temporary list scores
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
