package it.unibo.oop.myworkoutbuddy.view.handlers;

import static it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory.showDialog;
import static it.unibo.oop.myworkoutbuddy.view.handlers.ViewsHandler.getObserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unibo.oop.myworkoutbuddy.util.Pair;
import it.unibo.oop.myworkoutbuddy.view.SelectRoutineView;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * Handler of the SelectRoutineView. It handles the events captured by the GUI
 * selecting the chosen routine.
 */
public final class SelectRoutineHandler implements SelectRoutineView {

    @FXML
    private TabPane tabRoutine;

    @FXML
    private TextArea txtDescription;

    @FXML
    private Button btnDeleteRoutine;

    private int selectedRoutineIndex;

    private final WorkoutLayoutStrategy workoutLayout = new WorkoutLayout();

    private final EventHandler<Event> tabHandler = i -> {
        final Tab exs = (Tab) i.getSource();

        // update routine selected index
        selectedRoutineIndex = extractRoutineIndex(exs);

        // update description field
        updateDescriptionField();

        // check if user can delete routines.
        btnDeleteRoutine.setDisable(tabRoutine.getTabs().size() < 0);
    };

    @FXML
    private void insertData() {
        if (getObserver().addResults()) {
            showDialog("Data inserted!", "Your data has been successfully inserted!", Optional.empty(),
                    AlertType.INFORMATION);
        } else {
            showDialog("Error!", "There was an error!", Optional.empty(), AlertType.ERROR);
        }
    }

    @Override
    public Map<String, Pair<List<Integer>, Integer>> getUserResults() {
        final Map<String, Pair<List<Integer>, Integer>> results = new HashMap<>();
        tabRoutine.getTabs().stream().map(i -> (VBox) i.getContent()).forEach(exsBox -> {
            exsBox.getChildren().stream().map(workT -> (TitledPane) workT).map(exBox -> (VBox) exBox.getContent())
                    .forEach(exVbox -> {
                exVbox.getChildren().stream().map(exs -> (HBox) exs).forEach(exs -> {
                    final Pair<String, Pair<List<Integer>, Integer>> result = workoutLayout.getExerciseResults(exs);
                    results.put(result.getX(), result.getY());
                });
            });
        });
        return results;
    }

    @FXML
    private void deleteRoutine() {
        if (getObserver().deleteRoutine() && tabRoutine.getTabs().size() > 0) {
            tabRoutine.getTabs().remove(tabRoutine.getTabs().stream()
                    .filter(tab -> selectedRoutineIndex == extractRoutineIndex(tab)).findAny().get());
        } else {
            showDialog("Error deleting routine", "Your routine hasn't been deleted", Optional.empty(), AlertType.ERROR);
        }
    }

    @Override
    public int getRoutineIndex() {
        return selectedRoutineIndex;
    }

    private int extractRoutineIndex(final Tab exs) {
        return Integer.valueOf(exs.getText().substring(exs.getText().length() - 1));
    }

    private void updateDescriptionField() {
        getObserver().getRoutines().stream().filter(r -> r.getX().equals(selectedRoutineIndex)).map(r -> r.getY())
                .findAny().ifPresent(des -> txtDescription.setText(des));
    }

    /**
     * Show all saved Routines.
     */
    public void initialize() {
        getObserver().getRoutines().forEach(i -> {
            final Tab newRoutine = new Tab("Routine " + i.getX());
            newRoutine.setOnSelectionChanged(tabHandler);
            newRoutine.setContent(workoutLayout.addWorkoutNodes(i.getZ()));
            tabRoutine.getTabs().add(newRoutine);
        });
    }

}
