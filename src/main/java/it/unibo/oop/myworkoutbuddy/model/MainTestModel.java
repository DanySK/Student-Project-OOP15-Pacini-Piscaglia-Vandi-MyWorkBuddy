package it.unibo.oop.myworkoutbuddy.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 
 *
 */
public class MainTestModel {

    private static final Double valueDouble = 20.00;
    
    //private final Integer SETTING_VALUE = 0;

    /**
     * 
     * @param args parameters for the main
     * @throws NullPointerException exception for nullPointer
     * @throws IllegalArgumentException exception for not supported values
     */
    public static void main(final String[] args) throws NullPointerException, IllegalArgumentException {

        final MyWorkoutBuddyModel model;

        /* --- LOAD DATA ------*/
        System.out.println("\n Start Model");
        model = new MyWorkoutBuddyModelImpl();

        System.out.println("\n Load Data");
        testLoadData(model);

        /* --- PRINTS DATA ------*/
        System.out.println("\n ==== GYM TOOL LIST ==== \n");
        System.out.println(model.getGymToolList());

        System.out.println("\n ==== USERS LIST ==== \n");
        System.out.println(model.getUserList());

        /* --- LOGIN USER ------*/
        /* loginUser : set current user of model,
         * after that all model functions are working with loginUser*/

        System.out.println("\n ==== LOGIN USER ==== \n");
        model.loginUser("account2", "password2");

        // WOROUT DATE LOGIN USER: generates test data for login User
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

        model.logoutUser();
        System.out.println("\n ==== LOGOUT USER : ");
        System.out.println(" TrendBodyMass = " + model.getMeasureList());

     // -----------------------------------------------------------------------------------
        // use secondTestLoadData to try other functionalities
        //System.out.println("\n Load Data");
        //secondTestLoadData();


}

    /**
     * test method for try data building.
     * @param model MyWorkoutBuddyModel
     */
    //@Test
    public static void testLoadData(final MyWorkoutBuddyModel model) {

        /* 
         * BODY MAP : map : (Zone -> Body Parts)
         * 
         */

        /* 
         * GYM TOOL: Make GymTool Data
         * 
         */

        // description, path, num, valueMin, valueMax
        model.addGymTool("T1", "Tapis Roulant", "image1.png", 10, 1, 10);
        model.addGymTool("T2", "Cyclette", "image2.png", 10, 1, 10);
        model.addGymTool("T3", "Hand Weight", "image3.png", 10, 1, 10);
        
        // setting body parts for each GymTool
        model.addBodyPart("T1", "HAMSxxTRINGS", valueDouble);

        model.addBodyPart("T1", "QUADRICEPS", 80.00);

        model.addBodyPart("T2", "HAMSTRINGS", 30.00);
        model.addBodyPart("T2", "CALVES", 50.00);
        model.addBodyPart("T2", "BICEPS", 20.00);

        model.addBodyPart("T3", "HAMSTRINGS", 0.00);
        model.addBodyPart("T3", "BICEPS", 70.00);
        model.addBodyPart("T3", "PECTORALIS_MAJOR", 30.00);

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

    if (!model.isLoginUser()) {
        System.out.println("\n Errore login \n");
        return;
    }

    model.loginUser("account1", "password1");
    if (!model.isLoginUser()) {
        System.out.println("Errore login");
        return;
    }

    /*Add a measure body*/
    model.addDataMeasure(LocalDate.now());

    model.addBodyMeasure("HEIGHT", 1.80, true);
    model.addBodyMeasure("WEIGHT", 70.00, true);
    model.addBodyMeasure("UPPER_BODY", 80.00, true);
    model.addBodyMeasure("LOWER_BODY", 60.00, true);

    /* 
     * ROUTINE: load Routine for Current User
     * 
     */

    /* Routine: code , name, target */
    model.addRoutine("R1", "Routine1", "BODY_BUILDING");

    /* Exercise data for Routine: codeRoutine, description, codeGymTool, settingValue, repetition, time, numSession, pause */
    model.addGymExcercise("R1", "Warming", "T1", 0, 10, 3, 10, 2);
    model.addGymExcercise("R1", "Running", "T1", 0, 10, 3, 10, 2);
    model.addGymExcercise("R1", "Tonifing", "T2", 0, 10, 3, 10, 2);
    model.addGymExcercise("R1", "Swimming", "T3", 0, 10, 3, 10, 2);
    model.addGymExcercise("R1", "Swimming", "T2", 0, 10, 3, 10, 2);

    /* 
     * WORKOUT: Workout Cycle for current User
     * 
     */
    for (int k = 0; k < 5; k++) {

        /*Workout : codeRoutine, date, hour, state*/
        model.addWorkout("R1", LocalDate.now(), LocalTime.now(), true);

        /* set scores */
        for (int i = 0; i < model.getNumExercise("R1"); i++) {
            /*exerciseScore : numExercise, scoreExercise*/
            model.addExerciseScore(i, 3 + i + k);
        }
    }

    /*Add a new measure body*/
    model.addDataMeasure(LocalDate.now());

    model.addBodyMeasure("HEIGHT", 1.80, false);
    model.addBodyMeasure("WEIGHT", 65.00, false);
    model.addBodyMeasure("UPPER_BODY", 82.00, false);
    model.addBodyMeasure("LOWER_BODY", 63.00, false);

    }

// -----------------------------------------------------------------------------------

    /*
    @Test
    public void secondTestLoadData() {

        final MyWorkoutBuddyModel model;

        System.out.println("\n Start Model");
        model = new MyWorkoutBuddyModelImpl();

        final GymTool gymTool = new GymToolImpl("Tool1", "Tapis Roulant", "image1.png", 10, 1, 10);

        model.addGymTool("Tool1", "Tapis Roulant", "image1.png", 10, 1, 10);

        assertEquals(model.getGymToolList().size(), 1);
        assertEquals(model.getGymToolList().get(0), gymTool);

        System.out.println("\n Prova GymTool passata");

        final Account account4 = new AccountImpl("account4", "password4", "avatar4.png");
        final Person person4 = new PersonImpl("Silvio", "Pani", 20, "Silvio.pani@studio.unibo.it");
        model.addUser(account4, person4);

        model.loginUser("account4", "password4"); // setLoginUser

        assertEquals(model.getLoginUser().getAccount(), account4);
        assertEquals(model.getLoginUser().getPerson(), person4);
        
        System.out.println("\n Prova Login User passata");
        
        final GymTool tempTool = model.getGymTool("Tool1").get();
        tempTool.addBodyPart(BodyPart.HAMSTRINGS, 30.00);
        tempTool.addBodyPart(BodyPart.CALVES, 50.00);
        tempTool.addBodyPart(BodyPart.BICEPS, 20.00);

        assertEquals(model.getGymToolList().get(0).getBodyMap().keySet(), tempTool.getBodyMap().keySet());
        assertEquals(model.getGymToolList().get(0).getBodyMap().values(), tempTool.getBodyMap().values());
        System.out.println("\n Prova GymTool passata");
        
        final User logUser = model.getLoginUser();

         // WorkRoutine for login User : name, target 
        final WorkoutRoutine workRoutineProva = new WorkoutRoutineImpl("Routine1", "SPORTING");
        logUser.addRoutine(workRoutineProva);
        
        assertEquals(logUser.getRoutineList().size(), 1);
        assertEquals(logUser.getRoutineList().get(0), workRoutineProva);
        System.out.println("\n Prova WorkoutRoutine passata");

        // Exercise data for WorkRoutine : description, gymTool, settingValue, repetition, time, numSession, pause 
        workRoutineProva.addGymExcercise(new ExerciseImpl("Sporting", model.getGymTool("Tool1").get(), 0, 10, 3, 10, 2));
        
        assertEquals(logUser.getRoutineList().get(0).getExerciseList().size(), 1);
        assertEquals(logUser.getRoutineList().get(0).getExerciseList(), workRoutineProva.getExerciseList());
        System.out.println("\n Prova Exercise passata");
        
        final BodyData bodyData = Body.build().new BodyData(LocalDate.now());
        bodyData.addBodyMeasure(Body.Measure.HEIGHT, 1.80);
        bodyData.addBodyMeasure(Body.Measure.WEIGHT, 70.00);
        bodyData.addBodyMeasure(Body.Measure.UPPER_BODY, 80.00);
        bodyData.addBodyMeasure(Body.Measure.LOWER_BODY, 60.00);
        
        logUser.addMesure(bodyData);
        
        assertEquals(logUser.getMeasureList().size(), 1);
        assertEquals(logUser.getMeasureList().get(0), bodyData);
        System.out.println("\n Prima prova BodyData passata");
        
        logUser.getMeasureList().forEach(i -> {
            System.out.println("\n Meausure = " + i.getBodyMeasure());
        });
        
        final List<Workout> workoutList = new ArrayList<>();
        
        // Workout progress cycles

        for (int k = 0; k < 5; k++) {

            final Workout newWorkout = new WorkoutImpl(LocalDate.now(), LocalTime.now(), workRoutineProva);
            newWorkout.modifyState(true);

            // set scores
            for (int i = 0; i < workRoutineProva.getExerciseList().size(); i++) {
                newWorkout.addScore(i, 3 + i + k);
            }
            //logUser.addWorkout(workRoutineProva);
            workoutList.add(newWorkout);
            logUser.addWorkout(newWorkout);
        }
        
        assertEquals(logUser.getWorkoutList(), workoutList);
        System.out.println("\n Prima prova punteggio passata");
        
        logUser.getWorkoutList().forEach(i -> {
            System.out.println("Punteggio = " + i.getScoreList());
        });
        
        final BodyData newBodyData = Body.build().new BodyData(LocalDate.now());
        newBodyData.addBodyMeasure("HEIGHT", 1.70);
        newBodyData.addBodyMeasure("WEIGHT", 65.00);
        newBodyData.addBodyMeasure("UPPER_BODY", 82.00);
        newBodyData.addBodyMeasure("LOWER_BODY", 63.00);

        logUser.addMesure(newBodyData);
        
        assertEquals(logUser.getMeasureList().size(), 2);
        assertEquals(logUser.getMeasureList().get(1), newBodyData);
        System.out.println("\n Seconda prova BodyData passata");
        
        logUser.getMeasureList().forEach(i -> {
            System.out.println("\n Meausure = " + i.getBodyMeasure());
        });
        
    }
    */
}