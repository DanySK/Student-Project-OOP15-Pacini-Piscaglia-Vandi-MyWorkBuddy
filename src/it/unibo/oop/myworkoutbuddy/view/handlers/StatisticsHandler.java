package it.unibo.oop.myworkoutbuddy.view.handlers;

import it.unibo.oop.myworkoutbuddy.view.StatisticsView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;

/**
 * 
 * Handler of the accessView. It show user statistics fetched from the database.
 */
public class StatisticsHandler implements StatisticsView {

    @FXML
    private LineChart<?, ?> weightChart;

    @FXML
    private PieChart pieChart;

    @FXML
    void viewPie() {
        pieChart.setTitle("Exercises distribution");
        pieChart.setLabelLineLength(10);
        pieChart.setLegendSide(Side.LEFT);
        pieChart.setData(FXCollections.observableArrayList(
                new PieChart.Data("Chest", 13),
                new PieChart.Data("Legs", 25),
                new PieChart.Data("Arms", 10),
                new PieChart.Data("Shoulders", 22),
                new PieChart.Data("Crunch", 30)));
    }

      @Override
    public int getWeight() {
        return 0;
    }

}
