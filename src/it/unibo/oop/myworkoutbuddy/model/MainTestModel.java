package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

import it.unibo.oop.myworkoutbuddy.model.Body.BodyData;
import it.unibo.oop.myworkoutbuddy.model.Body.BodyPart;

public class MainTestModel {

    //private final Integer SETTING_VALUE = 0;

    public static void main(String[] args) {
        final MyWorkoutBuddyModel model;

        System.out.println("\n Start Model");
        model = new MyWorkoutBuddyModelImpl();

        System.out.println("\n Load Data");
        testLoadData(model);

        System.out.println("\n ==== GYM TOOL LIST ==== \n");
        System.out.println(model.getGymToolList());

        System.out.println("\n ==== USERS LIST ==== \n");
        System.out.println(model.getUserList());

        System.out.println("\n ==== LOGIN USER ==== \n");
        /*LOGIN: set current login user*/
        model.loginUser("account2", "password2");

        /*TEST LOGIN USER: generates test data for login User*/
        testLoginUser(model);

        /* --- PRINTS ------*/

        final User loginUser = model.getLoginUser();

        System.out.println("\n USER : " + loginUser);

        System.out.println("\n MeasureList = " + model.getMeasureList());

        System.out.println("\n ==== ROUTINE LIST  ==== ");
        System.out.println(" Routine List = " + loginUser.getRoutineList());

        System.out.println("\n ==== WORKOUT LIST  ==== ");
        System.out.println(" WorkoutList = " + loginUser.getWorkoutList());

        System.out.println("\n ==== STATISTICS SCORES : ");
        System.out.println(" PerformanceScore = " + model.performanceScore());
        System.out.println(" PerformanceBodyPart = " + model.performanceBodyPart());
        System.out.println(" PerformanceBodyZone = " + model.performanceBodyZone());

        System.out.println("\n ==== STATISTICS TIME : ");
        System.out.println(" TimeBodyPart = " + model.timeBodyPart());
        System.out.println(" TimeBodyZone = " + model.timeBodyZone());
        System.out.println(" TimeGymTool = " + model.timeGymTool());

        System.out.println("\n ==== BODY MASS : ");
        System.out.println(" TrendBodyMass = " + model.trendBodyMass());

    }

    /**
     * test method for try data building.
     * @param model MyWorkoutBuddyModel
     */
    @Test
    public static void testLoadData(final MyWorkoutBuddyModel model) {

        /* 
         * GYM TOOL: Make GymTool Test  Data
         * 
         */

        // description, path, num, valueMin, valueMax
        model.addGymTool(new GymToolImpl("T1", "Tapis Roulant", "image1.png", 10, 1, 10));
        model.addGymTool(new GymToolImpl("T2", "Cyclette", "image2.png", 10, 1, 10));
        model.addGymTool(new GymToolImpl("T3", "Hand Weight", "image3.png", 10, 1, 10));

        // setting body parts for each GymTool
        GymTool tempTool;

        tempTool = model.getGymTool("T1").get();
        tempTool.addBodyPart(BodyPart.HAMSTRINGS, 20.00);
        tempTool.addBodyPart(BodyPart.QUADRICEPS, 80.00);

        tempTool = model.getGymTool("T2").get();
        tempTool.addBodyPart(BodyPart.HAMSTRINGS, 30.00);
        tempTool.addBodyPart(BodyPart.CALVES, 50.00);
        tempTool.addBodyPart(BodyPart.BICEPS, 20.00);

        tempTool = model.getGymTool("T3").get();
        tempTool.addBodyPart(BodyPart.HAMSTRINGS, 0.00);
        tempTool.addBodyPart(BodyPart.BICEPS, 70.00);
        tempTool.addBodyPart(BodyPart.PECTORALIS_MAJOR, 30.00);


        /* USER: User (Account, Person)  make User Test data*/
        /*       Account : UserName, Password, Avatar image Person */
        /*       Person  : First Name, Last Name, age, email */

        final Account account1 = new AccountImpl("account1", "password1", "avatar1.png");
        final Person person1 = new PersonImpl("Paolo", "Rossi", 20, "paolo.rossi@studio.unibo.it");
        model.addUser(account1, person1);

        final Account account2 = new AccountImpl("account2", "password2", "avatar2.png");
        final Person person2 = new PersonImpl("Gino", "Bianchi", 25, "gino.bianchi@studio.unibo.it");
        model.addUser(account2, person2);

        final Account account3 = new AccountImpl("account3", "password3", "avatar3.png");
        final Person person3 = new PersonImpl("Mario", "Verdi", 30, "mario.verdi@studio.unibo.it");
        model.addUser(account3, person3);
        /*
         * ...
         */
    }

    /**
     * test method for try data loading.
     * @param model MyWorkoutBuddyModel
     */
    @Test
    public static void testLoginUser(final MyWorkoutBuddyModel model) {

    if (!model.isCurrentUser()) {
        System.out.println("\n Errore login \n");
        return;
    }

    final User logUser = model.getLoginUser();

    /* WorkRoutine for login User : name, target */
    final WorkoutRoutine workRoutine = new WorkoutRoutineImpl("Routine1", Body.Target.BODY_BUILDING);
    logUser.addRoutine(workRoutine);

    /* Exercise data for WorkRoutine : description, gymTool, settingValue, repetition, time, numSession, pause */
    workRoutine.addGymExcercise(new ExerciseImpl("Warming", model.getGymTool("T1").get(), 0, 10, 3, 10, 2));
    workRoutine.addGymExcercise(new ExerciseImpl("Running", model.getGymTool("T1").get(), 0, 10, 3, 10, 2));
    workRoutine.addGymExcercise(new ExerciseImpl("Tonifing", model.getGymTool("T2").get(), 0, 10, 3, 10, 2));
    workRoutine.addGymExcercise(new ExerciseImpl("Swimming", model.getGymTool("T3").get(), 0, 10, 3, 10, 2));
    workRoutine.addGymExcercise(new ExerciseImpl("Swimming", model.getGymTool("T2").get(), 0, 10, 3, 10, 2));

    /*Add initial body measures */
    final BodyData bodyData = Body.build().new BodyData(LocalDate.now());
    bodyData.addBodyMeasure(Body.Measure.HEIGHT, 1.80);
    bodyData.addBodyMeasure(Body.Measure.WEIGHT, 70.00);
    bodyData.addBodyMeasure(Body.Measure.UPPER_BODY, 80.00);
    bodyData.addBodyMeasure(Body.Measure.LOWER_BODY, 60.00);

    logUser.addMesure(bodyData);
    /*
     * Workout progress cycles
     */
    for (int k = 0; k < 5; k++) {

        final Workout newWorkout = new WorkoutImpl(LocalDate.now(), LocalTime.now(), workRoutine);
        newWorkout.modifyState(true);

        /* set scores */
        for (int i = 0; i < workRoutine.getExerciseList().size(); i++) {
            newWorkout.addScore(0, 3 + i + k);
        }
        logUser.addWorkout(newWorkout);
    }

    /*Add a new measure body*/
    final BodyData newBodyData = Body.build().new BodyData(LocalDate.now());
    newBodyData.addBodyMeasure(Body.Measure.HEIGHT, 1.80);
    newBodyData.addBodyMeasure(Body.Measure.WEIGHT, 65.00);
    newBodyData.addBodyMeasure(Body.Measure.UPPER_BODY, 82.00);
    newBodyData.addBodyMeasure(Body.Measure.LOWER_BODY, 63.00);

    logUser.addMesure(newBodyData);
    }
}