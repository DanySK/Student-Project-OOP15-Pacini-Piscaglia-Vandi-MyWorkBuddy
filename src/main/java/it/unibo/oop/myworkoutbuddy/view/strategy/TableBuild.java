package it.unibo.oop.myworkoutbuddy.view.strategy;

import java.util.List;
import java.util.Optional;

import it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory;
import it.unibo.oop.myworkoutbuddy.view.strategy.WorkoutLayout.Exercise;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

/**
 * Implementation of the build strategy of the routine table.
 *
 */
public class TableBuild implements TableBuildStrategy {

    @Override
    public TableColumn<Exercise, String> buildColumn(final String colName, final double width,
            final String propertyValue) {
        return createColumn(colName, width, propertyValue);
    }

    @Override
    public TableColumn<Exercise, String> buildKgColumn(final String colName, final double width,
            final String propertyValue) {
        final TableColumn<Exercise, String> col = createColumn(colName, width, propertyValue);
        col.setOnEditCommit(t -> {
            ((Exercise) t.getTableView().getItems().get(t.getTablePosition().getRow()))
                    .setKg(parseCellInput(t.getNewValue()));
        });
        return col;
    }

    @Override
    public TableColumn<Exercise, String> buildRepColumn(final String colName, final double width,
            final String propertyValue, int colNumber) {
        final TableColumn<Exercise, String> col = createColumn(colName, width, propertyValue);
        col.setOnEditCommit(t -> {
            ((Exercise) t.getTableView().getItems().get(t.getTablePosition().getRow())).getRepProperties()
                    .get(colNumber).set((parseCellInput(t.getNewValue())));
        });
        return col;
    }

    @Override
    public TableView<Exercise> build(final List<TableColumn<Exercise, String>> columns,
            final ObservableList<Exercise> data) {
        final TableView<Exercise> table = new TableView<>();
        columns.forEach(col -> table.getColumns().add(col));
        table.setEditable(true);
        table.setItems(data);
        return table;
    }

    private TableColumn<Exercise, String> createColumn(final String name, final double width,
            final String propertyValue) {
        final TableColumn<Exercise, String> col = new TableColumn<>(name);
        col.setPrefWidth(width);
        col.setCellFactory(TextFieldTableCell.forTableColumn());
        col.setCellValueFactory(new PropertyValueFactory<Exercise, String>(propertyValue));
        return col;
    }

    private int parseCellInput(final String cellInput) {
        try {
            return Integer.parseInt(cellInput);
        } catch (NumberFormatException e) {
            FxWindowFactory.showDialog("Uncorrect field inserted", "Please insert an integer number!", Optional.empty(),
                    AlertType.ERROR);
        }
        return 0;
    }

}
