package it.unibo.oop.myworkoutbuddy.view.handlers;

import static it.unibo.oop.myworkoutbuddy.view.handlers.ViewHandler.getObserver;

import it.unibo.oop.myworkoutbuddy.view.factory.ChartFactory;
import it.unibo.oop.myworkoutbuddy.view.factory.SimpleChartFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

/**
 * Handler of the accessView. It show user statistics fetched from the database.
 */
public final class StatisticsHandler {

    @FXML
    private TabPane tabPane;

    @FXML
    private VBox indexBox;

    private VBox currentBox;

    private int nCharts;

    private static final int CHARTS_PER_TAB = 1;

    private final ChartFactory charts = new SimpleChartFactory();

    /**
     * Called to initialize a controller after its root element has been
     * completely processed. In this class this method builds charts and indexes
     * when user select statistics from the menu.
     */
    public void initialize() {

        // initializing charts
        getObserver().getChartsData().forEach((chart, data) -> {

            nCharts++;
            if (nCharts > CHARTS_PER_TAB || nCharts == 1) {
                // to add dynamically tab considering chart number.
                currentBox = new VBox();
                currentBox.setId("statBox");
                final Tab newChartTab = new Tab(chart);
                newChartTab.setContent(currentBox);
                tabPane.getTabs().add(newChartTab);
            }

            switch (chart) {
            case "Weight Chart":
                currentBox.getChildren().add(charts.buildLineChart(data, chart));
                break;

            case "Time Body Zone":
                currentBox.getChildren().add(charts.buildPieChart(data, chart));
                break;

            case "Trend BMI":
                currentBox.getChildren().add(charts.buildLineChart(data, chart));
                break;

            case "Trend BMR":
                currentBox.getChildren().add(charts.buildLineChart(data, chart));
                break;

            case "Trend LBM":
                currentBox.getChildren().add(charts.buildLineChart(data, chart));
                break;

            default:
                break;
            }
        });
    }

}
