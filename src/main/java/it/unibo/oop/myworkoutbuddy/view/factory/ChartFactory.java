package it.unibo.oop.myworkoutbuddy.view.factory;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

/**
 * Utility class which provides methods to build charts using static factory
 * pattern.
 *
 */
public final class ChartFactory {

    private static final int PIECHART_SIZE = 400;

    private ChartFactory() {
    }

    /**
     * 
     * @param data
     *            to insert in the chart.
     * @param chartTitle
     *            to show.
     * @return a bar chart.
     */
    public static BarChart<String, Number> buildBarChart(final List<Pair<String, Number>> data,
            final String chartTitle) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle(chartTitle);
        final Series<String, Number> serie = new Series<String, Number>();
        data.forEach(d -> serie.getData().add(new XYChart.Data<String, Number>(d.getLeft(), d.getRight())));
        bc.getData().add(serie);
        return bc;
    }

    /**
     * 
     * @param data
     *            to insert in the chart.
     * @param title
     *            of the chart.
     * @return a pie chart.
     */
    public static PieChart buildPieChart(final List<Pair<String, Number>> data, final String title) {
        final PieChart pieChart = new PieChart();
        pieChart.setTitle(title);
        pieChart.setLabelLineLength(10);
        pieChart.setLegendSide(Side.LEFT);
        final ObservableList<Data> pieData = FXCollections.observableArrayList();
        data.forEach(d -> pieData.add(new PieChart.Data(d.getLeft(), d.getRight().doubleValue())));
        pieChart.setData(pieData);
        pieChart.setMaxSize(PIECHART_SIZE, PIECHART_SIZE);
        return pieChart;
    }

    /**
     * 
     * @param data
     *            to insert in the chart.
     * @param chartName
     *            to show.
     * @return a line chart.
     */
    public static LineChart<String, Number> buildLineChart(final List<Pair<String, Number>> data,
            final String chartName) {
        final LineChart<String, Number> lineChart = new LineChart<>(new CategoryAxis(), new NumberAxis());
        lineChart.setTitle(chartName);
        final Series<String, Number> series = new XYChart.Series<>();
        series.setName(chartName);
        data.forEach(d -> series.getData().add(new XYChart.Data<>(d.getLeft(), d.getRight())));
        lineChart.getData().add(series);
        return lineChart;
    }

}
