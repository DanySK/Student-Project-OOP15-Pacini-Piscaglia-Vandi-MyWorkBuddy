package it.unibo.oop.myworkoutbuddy.model;

import java.util.List;

import it.unibo.oop.myworkoutbuddy.model.Body.BodyData;


/**
 * 
 * Informations about User's activities :
 * Account, Data, Measure, Training, TrainingCard.
 * -------------------------------------------------------------
 */
public interface User {
    /**
     * 
     * @return the Account of User
     */
    Account getAccount();
    /**
     * 
     * @return avatar of User
     */
    String getAvatar();
    /**
     * 
     * @return the specific Data of User
     */
    UserData getUserData();
    /**
     * 
     * @return the body data for a specific user
     */
    List<BodyData> getMeasureList();
    /**
     * 
     * @return the training list for a specific user
     */
    List<Training> getTrainingList(); // workoutRoutine
    /**
     * 
     * @return the trainingCard list for a specific user
     */
    List<TrainingCard> getTrainingCardList();
    /**
     * 
     * @param bodyMeasure of User
     */
    void addMesure(BodyData bodyMeasure);
    /**
     * 
     * @param training of User
     */
    void addTraining(Training training);
    /**
     * 
     * @param trainingCard of User
     */
    void addTrainingCard(TrainingCard trainingCard);
    /**
     * Update the Status of User's trainings.
     */
    void upDateStatus();
    /**
     * Class to access system.
     * -------------------------------------------------------------
     */
    public interface Account {
        /**
         * 
         * @return userName
         */
        String getUserName();

        /**
         * 
         * @return userPassword
         */
        String getPassword();
    }
}
