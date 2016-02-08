package it.unibo.oop.myworkoutbuddy.model;

import java.util.ArrayList;
import java.util.List;

import it.unibo.oop.myworkoutbuddy.model.Body.BodyData;

/**
 * 
 * Informations about User's activities :
 * Account, Data, Measure, Training, TrainingCard.
 * -------------------------------------------------------------
 */
 
public class UserImpl implements User {

    private Account account;
    private UserData userData;
    private String avatar;

    private List<BodyData> measureList;     // list of body periodic measure
    private List<Workout> workoutList;    // list of training sessions done/to do
    private List<WorkoutRoutine> workoutRoutineList;    // list of available Cards
    /**
     * 
     */
    public UserImpl() {
    }

    /**
     * 
     * @param account Account
     * @param data UserData
     * @param avatar String
     */
    public UserImpl(final Account account, final UserData data, final String avatar) {
        this.setAccount(account);
        this.setUserData(data);
        this.setAvatar(avatar);
        this.measureList = new ArrayList<>();
        this.workoutList = new ArrayList<>();
        this.workoutRoutineList = new ArrayList<>();
    }
    @Override
    public Account getAccount() {
        // TODO Auto-generated method stub
        return this.account;
    }
    @Override
    public UserData getUserData() {
        // TODO Auto-generated method stub
        return this.userData;
    }
    @Override
    public String getAvatar() {
        return this.avatar;
    }
    /**
     * 
     */
    @Override
    public List<BodyData> getMeasureList() {
        // TODO Auto-generated method stub
        return this.measureList;
    }
    /**
     * 
     */
    @Override
    public List<Workout> getWorkoutList() {
        // TODO Auto-generated method stub
        return this.workoutList;
    }
    /**
     * 
     */
    @Override
    public List<WorkoutRoutine> getWorkoutRoutineList() {
        // TODO Auto-generated method stub
        return this.workoutRoutineList;
    }

    private void setAccount(final Account account) {
        this.account = account;
    }

    private void setUserData(final UserData userData) {
        this.userData = userData;
    }

    private void setAvatar(final String avatar) {
        this.avatar = avatar;
    }

    @Override
    public void addMesure(final BodyData bodyMeasure) {
        this.measureList.add(bodyMeasure);
    }
    @Override
    public void addWorkout(final Workout workout) {
        // TODO Auto-generated method stub
        this.workoutList.add(workout);
    }
    @Override
    public void addWorkoutRoutine(final WorkoutRoutine workoutRoutine) {
        // TODO Auto-generated method stub
        this.workoutRoutineList.add(workoutRoutine);
    }
    @Override
    public void upDateStatus() {
        // TODO Auto-generated method stub
    }

    /**
     * 
     *
     */
    public class AccountImpl implements Account {

        private String userName;
        private String password;
        /**
         * 
         * @param userName String
         * @param password String
         */
        public AccountImpl(final String userName, final String password) {
            this.setUserName(userName);
            this.setPassword(password);
        }

        @Override
        public String getUserName() {
            // TODO Auto-generated method stub
            return userName;
        }
        @Override
        public String getPassword() {
            return password;
        }

        private void setUserName(final String userName) {
            this.userName = userName;
        }

        private void setPassword(final String password) {
            this.password = password;
        }
    }
}
