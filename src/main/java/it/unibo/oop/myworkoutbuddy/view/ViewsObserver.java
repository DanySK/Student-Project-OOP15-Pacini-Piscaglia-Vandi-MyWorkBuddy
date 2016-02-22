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
     * @return a map of Workout exercises
     *         with section name like key and a set of workout training
     *         composed by exercise name and its related description.
     * 
     */
    Map<String, Set<String>> getExercises();

    /**
     * Ask Controller to save routine in the database.
     * 
     * @return true if routine is saved correctly
     *         otherwise false if repetitions inserted are wrong.
     */
    boolean saveRoutine();

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

    /**
     * 
     * @return true
     *         if user results are saved.
     */
    boolean addResults();

    /**
     * Ask Controller to logout the user.
     */
    void logoutUser();

    /**
     * 
     * @param exerciseName
     *            to get informations.
     * @return exercise selected
     *         informations.
     */
    Map<String,String> getExerciseInfo(String exerciseName);

    /**
     * 
     * @return a map index name - index value.
     */
    Map<String, Number> getIndexes();

    /**
     * 
     * @return true if routine has been deleted successfully.
     */
    boolean deleteRoutine();

}
