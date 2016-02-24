package it.unibo.oop.myworkoutbuddy.view.handlers;

import static it.unibo.oop.myworkoutbuddy.view.handlers.ViewsHandler.getObserver;

import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
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

    private static final int PIECHART_SIZE = 400;

    private static final int CHARTS_PER_TAB = 2;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     * In this class this method builds charts and indexes when user select
     * statistics from the menu.
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

            case "bodyZone performance":
                currentBox.getChildren().add(buildBarChart(data, chart));
                break;

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
            nameLabel.setId("indexLabel");
            final Label valueLabel = new Label(value.toString());
            valueLabel.setId("indexLabel");
            singleBox.getChildren().add(nameLabel);
            singleBox.getChildren().add(valueLabel);
            indexBox.getChildren().add(singleBox);
        });
        
    }

    private BarChart<String, Number> buildBarChart(final Map<String, Number> data, final String chartTitle) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle(chartTitle);
        final Series<String, Number> serie = new Series<String, Number>();
        data.forEach((name, value) -> {
            serie.getData().add(new XYChart.Data<String, Number>(name, value));
        });
        bc.getData().add(serie);
        return bc;
    }

    private PieChart buildPieChart(final Map<String, Number> data, final String title) {
        final PieChart pieChart = new PieChart();
        pieChart.setTitle(title);
        pieChart.setLabelLineLength(10);
        pieChart.setLegendSide(Side.LEFT);
        final ObservableList<Data> pieData = FXCollections.observableArrayList();
        data.forEach((ex, value) -> pieData.add(new PieChart.Data(ex, value.doubleValue())));
        pieChart.setData(pieData);
        pieChart.setMaxSize(PIECHART_SIZE, PIECHART_SIZE);
        return pieChart;
    }

    private LineChart<String, Number> buildLineChart(final Map<String, Number> data, final String chartName) {
        final LineChart<String, Number> lineChart = new LineChart<>(new CategoryAxis(), new NumberAxis());
        lineChart.setTitle(chartName);
        final Series<String, Number> series = new XYChart.Series<>();
        series.setName(chartName);
        data.forEach((ex, value) -> series.getData().add(new XYChart.Data<String, Number>(ex, value)));
        lineChart.getData().add(series);
        return lineChart;
    }

}
