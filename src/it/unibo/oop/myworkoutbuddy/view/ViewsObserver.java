package it.unibo.oop.myworkoutbuddy.view;

import java.util.Map;
import java.util.Set;

import it.unibo.oop.myworkoutbuddy.util.Pair;

/**
 * Provided by Controller and used by the View to update data in the GUI.
 */
public interface ViewsObserver {

    /**
     * 
     * @param username
     *            to insert in login view.
     * @param password
     *            to insert in login view.
     * @return true
     *         if credentials are correct.
     */
    boolean checkLogin(String username, String password);

    /**
     * 
     * @param registerData
     *            map (description with registration details of the new user.
     * @return true
     *         if registration process has been completed successfully.
     */
    boolean registerUser(Map<String, String> registerData);

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
     *         with Routine name like key and a set of workout training
     *         composed by exercise name and its related description.
     * 
     */
    Map<String, Set<Pair<String, String>>> getWorkoutExercises();

    /**
     * 
     * @param routine
     *            to save.
     *            Map key is workout's name and the related value is the set of
     *            exercises.
     * 
     */
    void saveRoutine(Map<String, Set<String>> routine);

    /**
     * 
     * @return the set of routines to show to user.
     *         Map key is workout's name and the related value is the set of
     *         exercises.
     */
    Set<Map<String, Set<String>>> getRoutines();

    /**
     * 
     * @param chartName
     *            to select the correct chart.
     * 
     * @return a series of data in a map to show in the chart.
     */
    Map<String, Number> getChartData(String chartName);

    /**
     * 
     * @return a map of user data
     *         Map key is the data description and the related value is the
     *         effective data.
     */
    Map<String, String> getUserData();

    /**
     * 
     * @param modifiedData
     *            Map key is the data description and the related value is the
     *            effective data.
     */
    void setUserData(Map<String, String> modifiedData);

}
