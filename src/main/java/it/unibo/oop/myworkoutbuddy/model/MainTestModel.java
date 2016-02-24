package it.unibo.oop.myworkoutbuddy.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * 
 *
 */
public class MainTestModel {

    //private final Integer SETTING_VALUE = 0;
    private static final Double VAL_MIN_DOUBLE = 20.00;
    private static final Double VAL_MIN1_DOUBLE = 30.00;
    private static final Double VAL_MAX_DOUBLE = 80.00;

    private static final Map<String, Double> TIME_MAP = new HashMap<>();

    /**
     * 
     * @param args parameters for the main
     * @throws NullPointerException exception for nullPointer
     * @throws IllegalArgumentException exception for not supported values
     */
    public static void main(final String[] args) throws NullPointerException, IllegalArgumentException {

        final MyWorkoutBuddyModel model;

        System.out.println("\n Start Model");
        model = new MyWorkoutBuddyModelImpl();

        /* --- LOAD DATA ------*/
        System.out.println("\n Load Data");
        testLoadData(model);

        /* --- PRINTS DATA ------*/
        System.out.println("\n ==== GYM TOOL LIST ==== ");
        System.out.println(model.getGymToolList());

        System.out.println("\n ==== USERS LIST ==== ");
        System.out.println(model.getUserList());

        for (int i = 1; i <= 3; i++) {

        /* --- LOGIN USER ------*/
        System.out.println("\n ==== LOGIN USER ==== ");
        model.loginUser("account" + i, "password" + i);

        // WORKOUT DATE LOGIN USER: cycle of generation test data for login User
        testLoginUser(model);

        /* --- PRINTS USER DATA AND STATISTICS ------*/
        System.out.println("\n MeasureList = " + model.getMeasureList());

        System.out.println("\n ==== ROUTINE LIST  ==== ");
        System.out.println(" Routine List = " + model.getRoutineList());

        System.out.println("\n ==== WORKOUT LIST  ==== ");
        System.out.println(" WorkoutList = " + model.getWorkoutList());

        System.out.println("\n ==== STATISTICS SCORES : ");
        System.out.println(" ScoreWorkout = " + model.scoreWorkout());
        System.out.println(" ScoreBodyPart = " + model.scoreBodyPart());
        System.out.println(" ScoreBodyZone = " + model.scoreBodyZone());
        System.out.println(" ScoreGymTool = " + model.scoreGymTool());

        System.out.println("\n ==== STATISTICS TIME : ");
        System.out.println(" TimeBodyPart = " + model.timeBodyPart());
        System.out.println(" TimeBodyZone = " + model.timeBodyZone());
        System.out.println(" TimeGymTool = " + model.timeGymTool());

        System.out.println("\n ==== BODY STATISTICS : ");
        System.out.println(" TrendBodyMass = " + model.trendBodyMass());
        System.out.println(" TrendBodyBMI = " + model.trendBodyBMI());

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
         * BODY: body definition (zone, part)
         * 
         */
        model.body(); // new body

        model.body("HAMSTRINGS", "LEG"); // mapping: part -> zone
        model.body("QUADRICEPS");
        model.body("CALVES");

        model.body("BICEPS", "ARM"); // mapping: part -> zone
        model.body("FORE_ARM");

        model.body("PECTORALIS_MAJOR", "CHEST"); // mapping: part -> zone
        model.body("PECTORALIS_MINOR");
        model.body("ABDOMINALS");
        // end Body definition

        /* 
         * GYMTOOLS: load GymTool informations
         * 
         */
        // description, image, num, valueMin, valueMax
        model.addGymTool("T1", "Tapis Roulant", "image1.png", 10, 1, 10);
        model.addGymTool("T2", "Cyclette", "image2.png", 10, 1, 10);
        model.addGymTool("T3", "Hand Weight", "image3.png", 10, 1, 10);

        // setting body parts for each GymTool
        model.addBodyPart("T1", "HAMSTRINGS", VAL_MIN_DOUBLE);
        model.addBodyPart("T1", "QUADRICEPS", VAL_MAX_DOUBLE);

        model.addBodyPart("T2", "HAMSTRINGS", VAL_MIN1_DOUBLE);
        model.addBodyPart("T2", "CALVES", 50.00);
        model.addBodyPart("T2", "BICEPS", VAL_MIN_DOUBLE);

        model.addBodyPart("T3", "BICEPS", 50.00);
        model.addBodyPart("T3", "PECTORALIS_MINOR", VAL_MIN_DOUBLE);
        model.addBodyPart("T3", "PECTORALIS_MAJOR", VAL_MIN1_DOUBLE);

       /*
        *  USER: User (Account, Person)  make User Test data
        *       Account : UserName, Password, Avatar image Person 
        *       Person  : First Name, Last Name, age, email 
        * 
        */
        model.addAccount("account1", "password1", "avatar1.png");
        model.addUser("Paolo", "Rossi", 20, "paolo.rossi@studio.unibo.it");

        model.addAccount("account2", "password2", "avatar2.png");
        model.addUser("Gino", "Bianchi", 25, "gino.bianchi@studio.unibo.it");

        model.addAccount("account3", "password3", "avatar3.png");
        model.addUser("Mario", "Verdi", 30, "mario.verdi@studio.unibo.it");
        /*
         * ...
         */
    }

    /**
     * test method for try data loading.
     * @param model MyWorkoutBuddyModel
     */
    //@Test
    public static void testLoginUser(final MyWorkoutBuddyModel model) {

        /*Add a init measure body : true(init) = new Measure*/
        model.addDataMeasure(LocalDate.now());

        model.addBodyMeasure("HEIGHT", 1.80, true);
        model.addBodyMeasure("WEIGHT", 70.00, true);
        model.addBodyMeasure("UPPER_BODY", VAL_MAX_DOUBLE, true);
        model.addBodyMeasure("LOWER_BODY", 60.00, true);

        /* 
         * ROUTINE: load Routines for Current User
         * 
         */
        /* Routine: code , name, target */
        model.addRoutine("R1", "Routine1", "BODY_BUILDING");

        /* Exercise data for Routine: codeRoutine, description, codeGymTool, settingValue, repetition, time, numSession, pause */
        model.addGymExcercise("R1", "Warming", "T1", 5, 10, 3, 10, 2);
        model.addGymExcercise("R1", "Running", "T1", 5, 10, 3, 10, 2);
        model.addGymExcercise("R1", "Tonifing", "T2", 5, 10, 3, 10, 2);
        model.addGymExcercise("R1", "Swimming", "T3", 5, 10, 3, 10, 2);
        model.addGymExcercise("R1", "Swimming", "T2", 5, 10, 3, 10, 2);

        /* 
         * WORKOUT: Workout Cycle of current User
         * 
         */
        for (int k = 0; k < 5; k++) {

            /*Workout : codeRoutine, date, hour, state*/
            model.addWorkout("R1", LocalDate.now(), LocalTime.now(), true);

            /* set scores */
            for (int i = 0; i < model.getNumExercise("R1"); i++) {
                /*exerciseScore : numExercise, scoreExercise*/
                final int score = 1 + k + i;
                model.addExerciseScore(i, score);
            }
        }

        /*Add a new measure body*/
        model.addDataMeasure(LocalDate.now());

        model.addBodyMeasure("HEIGHT", 1.80, false);
        model.addBodyMeasure("WEIGHT", 65.00, false);
        model.addBodyMeasure("UPPER_BODY", 82.00, false);
        model.addBodyMeasure("LOWER_BODY", 63.00, false);
}
    
    private static void mergeMap(final Map<String, Double> mapGymTool) {
        mapGymTool.keySet().stream().forEach(i -> {
            final Double newValue = mapGymTool.get(i);
            TIME_MAP.merge(i, newValue, (d1, d2) -> {
                return newValue + TIME_MAP.get(i);
            });
        });
    }
}