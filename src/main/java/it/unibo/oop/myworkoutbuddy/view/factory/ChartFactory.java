package it.unibo.oop.myworkoutbuddy.view.factory;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;

/**
 * 
 * Used to define methods that build JavaFX charts.
 *
 */
public interface ChartFactory {

    /**
     * 
     * @param data
     *            to insert in the chart.
     * @param chartTitle
     *            to show.
     * @return a bar chart.
     */
    BarChart<String, Number> buildBarChart(List<Pair<String, Number>> data, String chartTitle);

    /**
     * 
     * @param data
     *            to insert in the chart.
     * @param title
     *            of the chart.
     * @return a pie chart.
     */
    PieChart buildPieChart(List<Pair<String, Number>> data, String title);

    /**
     * 
     * @param data
     *            to insert in the chart.
     * @param chartName
     *            to show.
     * @return a line chart.
     */
    LineChart<String, Number> buildLineChart(List<Pair<String, Number>> data, String chartName);

}
