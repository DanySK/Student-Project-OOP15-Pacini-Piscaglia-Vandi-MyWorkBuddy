package it.unibo.oop.myworkoutbuddy.view;

import java.util.List;
import java.util.Map;
import java.util.Set;

import it.unibo.oop.myworkoutbuddy.util.Triple;

/**
 * Provided by Controller and used by the View to update data in the GUI.
 */
public interface ViewsObserver {

    /**
     * 
     * @return true
     *         if credentials are correct.
     */
    boolean loginUser();

    /**
     * 
     * @return true
     *         if registration process has been completed successfully.
     */
    boolean registerUser();

    /**
     * 
     * @param selectedTheme
     *            to save and show after login.
     */
    void setFavouriteTheme(String selectedTheme);

    /**
     * 
     * @return selected theme
     *         chosen previously.
     */
    String getFavouriteTheme();

    /**
     * 
     * @return a map of workout exercises
     *         with section name like key and a set of workout training
     *         composed by exercise name and its related description.
     * 
     */
    Set<Map<String, Object>> getExercises();

    /**
     * Ask Controller to save routine in the database.
     */
    void saveRoutine();

    /**
     * 
     * @return the set of routines to show to user.
     * 
     */
    Set<Triple<Integer, String, Map<String, Map<String, List<Integer>>>>> getRoutines();

    /**
     * 
     * @return a series of data in a map to show in the chart.
     */
    Map<String, Map<String, Number>> getChartsData();

    /**
     * 
     * @return a map of user data
     *         Map key is the data description and the related value is the
     *         effective data.
     */
    Map<String, Object> getUserData();

    /**
     * Ask Controller to save user data modified.
     * 
     * @return true
     *         if data are correct, false otherwise.
     */
    boolean setUserData();

}
