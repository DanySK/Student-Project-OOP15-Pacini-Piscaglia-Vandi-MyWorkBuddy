package it.unibo.oop.myworkoutbuddy.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import it.unibo.oop.myworkoutbuddy.model.MyWorkoutBuddyModel;
import it.unibo.oop.myworkoutbuddy.model.MyWorkoutBuddyModelImpl;

/*
 * CHECKSTYLE:OFF to better comprehension
 */

/**
 * 
 *
 */
public class MainJUnitTest {

    private static final MyWorkoutBuddyModel MODEL = new MyWorkoutBuddyModelImpl();

    /**
     * first test.
     */
    @Before
    public void testLoadUsers() {

        final String z1 = "LEG";
        final String z2 = "ARM";
        final String z3 = "CHEST";

        final String m11 = "HAMSTRINGS";
        final String m12 = "QUADRICEPS";
        final String m13 = "CALVES";

        final String m21 = "BICEPS";
        final String m22 = "FORE_ARM";

        final String m31 = "PECTORALIS_MAJOR";
        final String m32 = "PECTORALIS_MINOR";
        final String m33 = "ABDOMINALS";

        final Set<String> setMusclesTest = new HashSet<>();
        MODEL.resetBody(); // new body

        assertEquals(MODEL.getApplicationBody().getBodyMap().keySet(), new HashSet<>());

        MODEL.setBody(m11, z1); // mapping: part -> zone
        MODEL.setBody(m12);
        MODEL.setBody(m13);

        MODEL.setBody(m21, z2); // mapping: part -> zone
        MODEL.setBody(m22);

        MODEL.setBody(m31, z3); // mapping: part -> zone
        MODEL.setBody(m32);
        MODEL.setBody(m33);

        setMusclesTest.add(m11);
        setMusclesTest.add(m12);
        setMusclesTest.add(m13);

        assertTrue(MODEL.getApplicationBody().getBodyMap().keySet().size() == 3);
        assertEquals(MODEL.getApplicationBody().getBodyMap().keySet(), new HashSet<>(Arrays.asList(z1, z2, z3)));
        assertEquals(setMusclesTest, MODEL.getApplicationBody().getBodyMap().get(z1)); //

        // description, num, valueMin, valueMax
        MODEL.addGymTool("T1", "Tapis Roulant", 10, 1, 10);
        MODEL.addGymTool("T2", "Cyclette", 10, 1, 5);
        MODEL.addGymTool("T3", "Hand Weight", 10, 1, 20);

        assertEquals(MODEL.getGymToolList().get(1).getCode(), "T2");
        assertTrue(MODEL.getGymToolList().get(1).getNumTools() == 10);
        assertTrue(MODEL.getGymToolList().get(1).getMinValue() == 1);
        assertTrue(MODEL.getGymToolList().get(1).getMaxValue() == 5);
        assertTrue(MODEL.getGymToolList().size() == 3);

        // setting body parts for each GymTool
        /*
         * If you have defined before :
         * 1) the body parts(muscles) to add;
         * 2) the tools that use the body parts specified;
         * You may add a new percentage value for a set of body parts in the specified tools
         */
        MODEL.addBodyPart("T1", m11, 20.00);
        MODEL.addBodyPart("T1", m12, 80.00);

        MODEL.addBodyPart("T2", m11, 30.00);
        MODEL.addBodyPart("T2", m13, 50.00);
        MODEL.addBodyPart("T2", m21, 20.00);

        MODEL.addBodyPart("T3", m21, 50.00);
        MODEL.addBodyPart("T3", m31, 30.00);
        MODEL.addBodyPart("T3", m32, 20.00);

        final Map<String, Double> percentageTryMap = new HashMap<>();
        percentageTryMap.put(m11, 20.00);
        percentageTryMap.put(m12, 80.00);
        assertEquals(MODEL.getGymToolList().get(0).getBodyMap(), percentageTryMap);

        MODEL.addBodyPart("Txx", m21, 20.00); // test add Part with error of toolCode
        assertFalse(MODEL.getGymToolList().size() == 4);

       /*
        *  USER: User (Account, Person)  make User Test data
        *       Account : UserName, Password, image, Person 
        *       Person  : First Name, Last Name, age, email 
        * 
        * When you add a new User with appropriated Account you automatically set the current User
        */
        final int age = 20;
        MODEL.addAccount("account1", "password1");
        MODEL.addUser("Paolo", "Rossi", age, "paolo.rossi@studio.unibo.it");

        MODEL.addAccount("account2", "password2");
        MODEL.addUser("Gino", "Bianchi", 25, "gino.bianchi@studio.unibo.it");

        MODEL.addAccount("account3", "password3");
        MODEL.addUser("Mario", "Verdi", 30, "mario.verdi@studio.unibo.it");

        final int age1 = 30;
        MODEL.addAccount("account3", "password3");
        MODEL.addUser("Giulio", "Fumagalli", age1, "giulio.fumagalli@studio.unibo.it");

        assertTrue(MODEL.getUserList().size() == 3);
        assertFalse(MODEL.getUserList().size() == 4);
        assertEquals(MODEL.getCurrentUserName().get(), "account3");
    }

    /**
     * second test.
     */
    @Test
    public void dataWorkoutUser() {

        MODEL.loginUser("account1", "password1");

        /*Add a measure body: true(init) = new Measure*/
        MODEL.addDataMeasure(LocalDate.now());

        /*
         * you may add all the measure you want, which refer to the corresponding human feature you want to give, if the current user is set
         */

        final String m1 = "HEIGHT";
        final String m2 = "WEIGHT";
        final String m3 = "UPPER_BODY";
        final String m4 = "LOWER_BODY";

        final Double height = 1.80;
        final Double weight = 70.00;
        final Double upperBody = 80.00;
        final Double lowerBody = 60.00;
        MODEL.addBodyMeasure(m1, height, true);
        MODEL.addBodyMeasure(m2, weight, true);
        MODEL.addBodyMeasure(m3, upperBody, true);
        MODEL.addBodyMeasure(m4, lowerBody, true);

        final double bmi = MODEL.trendBodyBMI().get(0);
        final double bmiTest = weight / (height * height);

        assertTrue(bmi == bmiTest);

        final double sum;

        final Map<String, Double> measureMap = MODEL.getMeasureList().get(0).getBodyMeasure();

        sum = measureMap.values().stream().mapToDouble(i -> i.doubleValue()).sum();

        assertTrue(sum == (height + weight + upperBody + lowerBody));

        /* 
         * WORKOUT: load Workouts for Current User
         * You may add a new workout if the current user is set
         */
        /* Workout: code , name, target */
        MODEL.addWorkout("W1", "Workout1", "BODY_BUILDING");

        /*
         * add a new GymExercise to current workout
         */
        /* Exercise data for Workout: codeWorkout(identifier code), description(what the routine must do?), 
         * codeGymTool(what's its name ?)*/

        MODEL.addGymExcercise("W1", "Warming", "T1", new ArrayList<>(Arrays.asList(1, 2, 3)));
        MODEL.addGymExcercise("W1", "Running", "T1", new ArrayList<>(Arrays.asList(3, 4, 3)));
        MODEL.addGymExcercise("W1", "Tonifing", "T2", new ArrayList<>(Arrays.asList(4, 7, 8)));
        MODEL.addGymExcercise("W1", "Swimming", "T3", new ArrayList<>(Arrays.asList(1, 2, 3)));
        MODEL.addGymExcercise("W1", "Swimming", "T2", new ArrayList<>(Arrays.asList(1, 2, 3)));

        MODEL.addGymExcercise("Wx", "Swimming", "T2", new ArrayList<>(Arrays.asList(1, 2, 3))); // test error workout code (Wx)
        MODEL.addGymExcercise("W1", "Swimming", "Tx", new ArrayList<>(Arrays.asList(1, 2, 3))); // test error tools code (Tx)

        assertTrue(MODEL.getNumExercise() == 5);
        assertEquals(MODEL.getWorkoutList().get(0).getExerciseList().get(0).getDescription(), "Warming");

        /* 
         * ROUTINE: Routine Cycle of current User
         * Here an example of exercise scores input
         */
        final Integer numTryCycle = 5;
        for (int k = 0; k < numTryCycle; k++) {

            /*Routine : codeWorkout, date*/
            MODEL.addRoutine(k, "W1", LocalDate.now()); //

            /* set values */
            final List<Integer> valueList = new ArrayList<>(); // new List of values (it's an example)
            for (int i = 0; i < MODEL.getNumExercise(); i++) { //
                /*exerciseValue : numExercise, valueExercise*/
                final int value = 1 + (k + i);
                valueList.add(value); // add a new score to temporary list
            }
            MODEL.addExerciseValue(valueList); // add all temporary list values
        }

        final int firstDim = MODEL.getRoutineList().size();
        assertTrue(firstDim == numTryCycle);

        final int numRoutineDel = 1;
        MODEL.removeRoutine(2); // remove the routine with the specified id

        final int secondDim = MODEL.getRoutineList().size();

        assertFalse(firstDim == secondDim);

        /*Add a new measure body*/
        MODEL.addDataMeasure(LocalDate.now());

        /*
         * With false option, you may add a new value for a human feature if and only if the human feature exists.
         * The human feature exists is the previous declaration of it
         */
        final Double heightNew = 1.80;
        final Double weightNew = 65.00;
        final Double upperBodyNew = 82.00;
        final Double lowerBodyNew = 63.00;

        MODEL.addBodyMeasure(m1, heightNew, false);
        MODEL.addBodyMeasure(m2, weightNew, false);
        MODEL.addBodyMeasure(m3, upperBodyNew, false);
        MODEL.addBodyMeasure(m4, lowerBodyNew, false);

        final double bmiNew = MODEL.trendBodyBMI().get(1);
        final double bmiTestNew = weightNew / (heightNew * heightNew);

        assertTrue(bmiNew == bmiTestNew);
        assertEquals(MODEL.getMeasureList().get(1).getBodyMeasure().keySet(), new HashSet<>(Arrays.asList(m1, m2, m3, m4)));

        /*
         * final delete
         */
        final int dimW1 = MODEL.getWorkoutList().size();
        final int dimR1 = MODEL.getRoutineList().size();
        MODEL.removeWorkout("W1");
        final int dimW2 = MODEL.getWorkoutList().size();
        final int dimR2 = MODEL.getRoutineList().size();
        assertTrue((dimW1 - dimW2) == 1);

        assertTrue(dimR1 == (numTryCycle - numRoutineDel));
        assertTrue(dimR2 == 0);

        MODEL.logoutUser();
        assertEquals(MODEL.getCurrentUserName(), Optional.empty());
        assertEquals(MODEL.getRoutineList(), new ArrayList<>());
        assertEquals(MODEL.getWorkoutList(), new ArrayList<>());
 }
}
