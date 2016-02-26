package it.unibo.oop.myworkoutbuddy.view.strategy;

import java.util.List;

import it.unibo.oop.myworkoutbuddy.view.strategy.WorkoutLayout.Exercise;
import javafx.collections.ObservableList;
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
            ((Exercise) t.getTableView().getItems().get(t.getTablePosition().getRow())).setKg(t.getNewValue());
        });
        return col;
    }

    @Override
    public TableColumn<Exercise, String> buildRepColumn(final String colName, final double width,
            final String propertyValue) {
        TableColumn<Exercise, String> col = createColumn(colName, width, propertyValue);
        switch (propertyValue) {
        case "rep1":
            col.setOnEditCommit(t -> {
                ((Exercise) t.getTableView().getItems().get(t.getTablePosition().getRow())).setRep1(t.getNewValue());
            });
            break;
        case "rep2":
            col.setOnEditCommit(t -> {
                ((Exercise) t.getTableView().getItems().get(t.getTablePosition().getRow())).setRep2(t.getNewValue());
            });
            break;
        case "rep3":
            col.setOnEditCommit(t -> {
                ((Exercise) t.getTableView().getItems().get(t.getTablePosition().getRow())).setRep3(t.getNewValue());
            });
            break;
        default:
            break;
        }
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
        TableColumn<Exercise, String> col = new TableColumn<>(name);
        col.setPrefWidth(width);
        col.setCellFactory(TextFieldTableCell.forTableColumn());
        col.setCellValueFactory(new PropertyValueFactory<Exercise, String>(propertyValue));
        return col;
    }

}