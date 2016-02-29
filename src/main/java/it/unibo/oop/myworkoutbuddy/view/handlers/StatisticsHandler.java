package it.unibo.oop.myworkoutbuddy.view.handlers;

import static it.unibo.oop.myworkoutbuddy.view.factory.ChartFactory.buildBarChart;
import static it.unibo.oop.myworkoutbuddy.view.factory.ChartFactory.buildLineChart;
import static it.unibo.oop.myworkoutbuddy.view.factory.ChartFactory.buildPieChart;
import static it.unibo.oop.myworkoutbuddy.view.handlers.ViewHandler.getObserver;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
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

    private static final int CHARTS_PER_TAB = 2;

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
                final Tab newChartTab = new Tab("Charts");
                newChartTab.setContent(currentBox);
                tabPane.getTabs().add(newChartTab);
            }

            switch (chart) {
            case "weightChart":
                currentBox.getChildren().add(buildLineChart(data, chart));
                break;

            case "time performance":
                currentBox.getChildren().add(buildPieChart(data, chart));
                break;

            // not used
            case "bodyZone performance":
                currentBox.getChildren().add(buildBarChart(data, chart));
                break;

            // not used
            case "bodyPart performance":
                currentBox.getChildren().add(buildBarChart(data, chart));
                break;

            default:
                break;
            }
        });

        // initializing indexes
        indexBox.setId("statBox");
        getObserver().getIndexes().forEach((name, value) -> {
            final HBox singleBox = new HBox();
            final Label nameLabel = new Label(name);
            nameLabel.setId("indexNameLabel");
            final Label valueLabel = new Label(
                    String.valueOf(Math.round(value.doubleValue() * Math.pow(10, 2)) / Math.pow(10, 2)));
            valueLabel.setId("indexLabel");
            singleBox.getChildren().add(nameLabel);
            singleBox.getChildren().add(valueLabel);
            indexBox.getChildren().add(singleBox);
        });
    }

}
