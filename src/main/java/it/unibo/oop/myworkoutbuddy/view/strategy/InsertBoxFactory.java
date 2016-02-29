package it.unibo.oop.myworkoutbuddy.view.strategy;

import java.util.List;
import java.util.Optional;

import it.unibo.oop.myworkoutbuddy.view.strategy.WorkoutLayout.Exercise;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Strategy to build the routine table.
 * 
 */
public interface InsertBoxFactory {

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
    TableColumn<Exercise, String> buildStringColumn(String colName, double width, Optional<String> propertyValue);

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
     * @param colNumber
     *            the column number in repetition column.
     * @return a table column with repetitions built with the passed features.
     */
    TableColumn<Exercise, String> buildRepColumn(String colName, double width, String propertyValue, int colNumber);

    /**
     * 
     * @param list
     *            a list of table columns to add to the table.
     * @param data
     *            to insert in the table.
     * @return the table view built.
     */
    TableView<Exercise> build(List<TableColumn<Exercise, String>> list, ObservableList<Exercise> data);
}
