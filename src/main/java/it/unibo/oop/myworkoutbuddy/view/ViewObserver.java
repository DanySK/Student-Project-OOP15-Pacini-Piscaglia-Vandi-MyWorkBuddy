package it.unibo.oop.myworkoutbuddy.view;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Triple;

/**
 * Provided by Controller and used by the View to update data in the GUI.
 */
public interface ViewObserver {

    /**
     * 
     * @return a list of the string representations of the occurred errors,
     *         otherwise an empty list if credentials are correct.
     */
    List<String> loginUser();

    /**
     * 
     * @return a list of the string representations of the occurred errors, an
     *         empty list if registration process has been completed
     *         successfully.
     */
    List<String> registerUser();

    /**
     * 
     * @return a map of Workout exercises with body part name like key and a set
     *         of related exercises names.
     * 
     */
    Map<String, Set<String>> getExercises();

    /**
     * Ask Controller to save routine in the database.
     * 
     * @return true if routine is saved correctly otherwise false if an error
     *         occurred.
     */
    boolean saveRoutine();

    /**
     * 
     * @return the set of routines to show to user composed by a triple <index,
     *         description, Map<workout name, Map<exercise name, List of
     *         repetitions >>.
     * 
     */
    Set<Triple<Integer, String, Map<String, Map<String, List<Integer>>>>> getRoutines();

    /**
     * 
     * @return series of data in a map <chartName, Map<name,value>> to show in
     *         the chart.
     */
    Map<String, Map<String, Number>> getChartsData();

    /**
     * 
     * @return a map of user data Map key is the data description and the
     *         related value is the effective data.
     */
    Map<String, Object> getCurrentUserData();

    /**
     * Ask Controller to save user data modified.
     * 
     * @return a list of string representations of the occurred errors,
     *         otherwise an empty list if data are correct.
     */
    List<String> setUserData();

    /**
     * 
     * @return true if user results are saved, otherwise false if an error
     *         occurred.
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
     * @return exercise selected informations.
     */
    Map<String, String> getExerciseInfo(String exerciseName);

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
