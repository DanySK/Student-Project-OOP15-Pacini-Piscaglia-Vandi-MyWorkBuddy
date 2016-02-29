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
public class InsertBoxBuild implements InsertBoxBuildStrategy {

    @Override
    public TableColumn<Exercise, String> buildStringColumn(final String colName, final double width,
            final Optional<String> propertyValue) {
        return createColumn(colName, width, propertyValue);
    }

    @Override
    public TableColumn<Exercise, String> buildKgColumn(final String colName, final double width,
            final String propertyValue) {
        final TableColumn<Exercise, String> col = createColumn(colName, width, Optional.of(propertyValue));
        col.setCellFactory(TextFieldTableCell.forTableColumn());
        col.setOnEditCommit(t -> {
            if (checkCellInput(t.getNewValue())) {
                ((Exercise) t.getTableView().getItems().get(t.getTablePosition().getRow())).setKg(t.getNewValue());
            }
        });
        return col;
    }

    @Override
    public TableColumn<Exercise, String> buildRepColumn(final String colName, final double width,
            final String propertyValue, final int colNumber) {
        final TableColumn<Exercise, String> col = createColumn(colName, width, Optional.of(propertyValue));
        col.setCellFactory(TextFieldTableCell.forTableColumn());
        col.setOnEditCommit(t -> {
            if (checkCellInput(t.getNewValue())) {
                ((Exercise) t.getTableView().getItems().get(t.getTablePosition().getRow())).getRepProperties()
                        .get(colNumber - 1).set(t.getNewValue());
            }
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
            final Optional<String> propertyValue) {
        final TableColumn<Exercise, String> col = new TableColumn<>(name);
        col.setPrefWidth(width);
        propertyValue.ifPresent(p -> {
            col.setCellValueFactory(new PropertyValueFactory<Exercise, String>(p));
        });
        return col;
    }

    private boolean checkCellInput(final String cellInput) {
        try {
            Integer.parseInt(cellInput);
        } catch (NumberFormatException e) {
            FxWindowFactory.showDialog("Uncorrect field inserted", "Please insert an integer number!", Optional.empty(),
                    AlertType.ERROR);
            return false;
        }
        return true;
    }

}
