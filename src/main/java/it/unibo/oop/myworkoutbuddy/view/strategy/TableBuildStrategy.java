package it.unibo.oop.myworkoutbuddy.view.strategy;

import java.util.List;

import it.unibo.oop.myworkoutbuddy.view.strategy.WorkoutLayout.Exercise;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Strategy to build the routine table.
 * 
 */
public interface TableBuildStrategy {

    /**
     * 
     * @param colName
     *            to set.
     * @param width
     *            to assign to column.
     * @param propertyValue
     *            name of the field in Exercise static class.
     * @return a table column with the passed features.
     */
    TableColumn<Exercise, String> buildColumn(String colName, double width, String propertyValue);

    /**
     * 
     * @param colName
     *            to set.
     * @param width
     *            to assign to column.
     * @param propertyValue
     *            name of field in Exercise static class.
     * @return a table column with kg with built the passed features.
     */
    TableColumn<Exercise, String> buildKgColumn(String colName, double width, String propertyValue);

    /**
     * 
     * @param colName
     *            to set.
     * @param width
     *            to assign to column.
     * @param propertyValue
     *            name of field in Exercise static class.
     * @return a table column with repetitions built with the passed features.
     */
    TableColumn<Exercise, String> buildRepColumn(String colName, double width, String propertyValue);

    /**
     * 
     * @param columns
     *            a list of table columns to add to the table.
     * @param data
     *            to insert in the table.
     * @return the table view built.
     */
    TableView<Exercise> build(List<TableColumn<Exercise, String>> columns, ObservableList<Exercise> data);
}
