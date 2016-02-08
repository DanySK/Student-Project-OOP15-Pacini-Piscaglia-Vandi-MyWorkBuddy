package it.unibo.oop.myworkoutbuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

import it.unibo.oop.myworkoutbuddy.model.Body.BodyData;
import it.unibo.oop.myworkoutbuddy.model.Body.BodyPart;
import it.unibo.oop.myworkoutbuddy.model.Body.BodyTarget;
import it.unibo.oop.myworkoutbuddy.model.User.Account;

/**
 * 
 *
 */
public class MyWorkoutBuddyModelImpl {

    private static final int SETTING_VALUE = 5;

    private List<User> listUser;
    private List<Account> listAccount;
    private List<GymTool> listGymTool;

    private Map<String, GymTool> mapGymTool;

    private User loginUser;
    /**
     * 
     */
    public MyWorkoutBuddyModelImpl() {
        this.listUser = new ArrayList<>();
        this.listAccount = new ArrayList<>();
        this.listGymTool = new ArrayList<>();

        this.mapGymTool = new HashMap<>();
    }

    /**
     * 
     * @param indice int
     * @return the Account at indice pos of listAccount
     */
    private Account getAccount(final int indice) {
        return this.listAccount.get(indice);
    }

    /**
     * @param code String
     * @param toolName String 
     * @param fileImage String 
     * @param vMin int
     * @param vMax int
     * @param num int
     */
    public void addGymTool(final String code, final String toolName, final String fileImage, final int vMin, final int vMax, final int num) {
        final GymTool newTool = new GymToolImpl(code, toolName, fileImage, vMin, vMax, num);
        this.listGymTool.add(newTool);
        this.mapGymTool.put(code, newTool);
    }
    /**
     * 
     * @param account Account
     * @param userData UserData
     * @param email String
     */
    public void addUser(final Account account, final UserData userData, final String email) {
        this.listUser.add(new UserImpl(account, userData, email));
    }
    /**
     * 
     * @param name String
     * @param password String
     */
    public void addAccount(final String name, final String password) {
        this.listAccount.add(new UserImpl().new AccountImpl(name, password));
    }
    /**
     * 
     * @param userName String
     * @param password String
     */
    public void loginUser(final String userName, final String password) {
        this.listUser.forEach(t-> {
            if (t.getAccount().getUserName().equals(userName) && t.getAccount().getPassword().equals(password)) {
                this.loginUser = t;
            }
        });
    }
    /**
     * 
     * @return Optional version of loginUser
     */
    public Optional<User> getLoginUser() {
        return Optional.of(this.loginUser);
    }

    /**
     * 
     * @param code String
     * @return Optional version of a GymTool, mapped for the param code
     */
    public Optional<GymTool> getGymTool(final String code) {
        return Optional.of(this.mapGymTool.get(code));
    }

    /**
     * test method for try data building.
     */
    @Test
    public void testData1() {
        /* Test : Account Data*/
        this.addAccount("account1", "password1");
        this.addAccount("account2", "password2");
        this.addAccount("account3", "password3");

        /* Test : User Data*/
        this.addUser(this.getAccount(0), new UserDataImpl("Paolo", "Rossi", 20, "paolo.rossi@studio.unibo.it"), "avatar0.png");
        this.addUser(this.getAccount(1), new UserDataImpl("Gino", "Bianchi", 20, "gino.bianchi@studio.unibo.it"), "avatar1.png");
        this.addUser(this.getAccount(2), new UserDataImpl("Mario", "Verdi", 20, "mario.verdi@studio.unibo.it"), "avatar2.png");

        /* Test : GymTool Data*/
        // description, path, num, valueMin, valueMax
        this.addGymTool("T1", "Tapis Roulant", "image1.png", 10, 1, 10);
        this.addGymTool("T2", "Gym Tool2", "image2.png", 10, 1, 10);
        this.addGymTool("T3", "Gym Tool3", "image3.png", 10, 1, 10);

        /*
         * body parts for GymTool
         */
        final GymTool tempTool = this.mapGymTool.get("T1");

        tempTool.addBodyPart(BodyPart.HAMSTRINGS, 40.00);
        tempTool.addBodyPart(BodyPart.QUADRICEPS, 60.00);

        /* Test : WorkRoutine for User0*/
        // name, target
        WorkoutRoutine workRoutine = new WorkoutRoutineImpl("Routine1", BodyTarget.BODY_BUILDING);

        loginUser = this.listUser.get(0);
        loginUser.addWorkoutRoutine(workRoutine);

        /* Test : Exercise data for WorkRoutine*/
        // description, gymTool, setting grade, repetition, time, numSession, pause
        workRoutine.addGymExcercise(new ExerciseImpl("Warming", this.getGymTool("T1").get(), SETTING_VALUE, 1, 3, 2, 1));
        workRoutine.addGymExcercise(new ExerciseImpl("Running", this.getGymTool("T1").get(), SETTING_VALUE, 10, 3, 2, 1));
        workRoutine.addGymExcercise(new ExerciseImpl("Tonifing", this.getGymTool("T2").get(), SETTING_VALUE, 5, 3, 2, 1));
        workRoutine.addGymExcercise(new ExerciseImpl("Swimming", this.getGymTool("T3").get(), SETTING_VALUE, 10, 3, 2, 1));
        workRoutine.addGymExcercise(new ExerciseImpl("Swimming", this.getGymTool("T2").get(), SETTING_VALUE, 10, 3, 2, 1));

        /*Add initial measures*/
        final BodyData bodyData = new Body().new BodyData(LocalDate.now());
        bodyData.addBodyMeasure(Body.BodyMeasure.HEIGHT, 1.80);
        bodyData.addBodyMeasure(Body.BodyMeasure.WEIGHT, 70.00);
        bodyData.addBodyMeasure(Body.BodyMeasure.UPPER_BODY, 80.00);
        bodyData.addBodyMeasure(Body.BodyMeasure.LOWER_BODY, 60.00);

        /*
         * Workout progress cycles
         */
        for (int k = 0; k < 5; k++) {

            final Workout newWorkout = new WorkoutImpl(LocalDate.now(), LocalTime.now(), workRoutine);
            /* set scores */
            for (int i = 0; i < workRoutine.getExerciseList().size(); i++) {
                newWorkout.addScore(0, 10 + i + k);
            }
            loginUser.addWorkout(newWorkout);
        }
        
        final BodyData newbodyData = new Body().new BodyData(LocalDate.now());
        newbodyData.addBodyMeasure(Body.BodyMeasure.HEIGHT, 1.80);
        newbodyData.addBodyMeasure(Body.BodyMeasure.WEIGHT, 65.00);
        newbodyData.addBodyMeasure(Body.BodyMeasure.UPPER_BODY, 82.00);
        newbodyData.addBodyMeasure(Body.BodyMeasure.LOWER_BODY, 63.00);
    }
}
