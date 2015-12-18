package it.unibo.oop.myworkoutbuddy.model;

import java.util.List;

public interface User {

    /**
     * 
     * @return
     */
    String getUsername();

    /**
     * 
     * @return
     */
    String getFirstName();

    /**
     * 
     * @param firstName
     */
    void setFirstName(String firstName);

    /**
     * 
     * @return
     */
    String getLastName();

    /**
     * 
     * @param lastName
     */
    void setLastName(String lastName);

    /**
     * 
     * @return
     */
    double getWeight();

    /**
     * 
     * @param height
     */
    void setHeight(int height);

    /**
     * 
     * @return
     */
    int getHeight();

    /**
     * 
     * @param weight
     */
    void setWeight(double weight);

    /**
     * 
     * @return
     */
    String getPassword();

    /**
     * 
     * @param newPassword
     */
    void setPassword(String newPassword);

    /**
     * 
     */
    void addPersonalRoutine(WorkoutRoutine routine);

    /**
     * 
     * @return
     */
    WorkoutRoutine getCurrentRoutine();

    /**
     * 
     * @return
     */
    List<WorkoutRoutine> getRoutines();

}
