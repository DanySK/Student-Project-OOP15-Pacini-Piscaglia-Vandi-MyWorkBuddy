package it.unibo.oop.myworkoutbuddy.view.handlers;

import static it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory.showDialog;
import static it.unibo.oop.myworkoutbuddy.view.handlers.ViewHandler.getObserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.oop.myworkoutbuddy.view.SelectRoutineView;
import it.unibo.oop.myworkoutbuddy.view.strategy.WorkoutLayout;
import it.unibo.oop.myworkoutbuddy.view.strategy.WorkoutLayoutStrategy;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
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
    private Label messageLabel;

    @FXML
    private TextField txtKg;

    @FXML
    private Button btnDeleteRoutine;

    private String selectedRoutineName;

    private final WorkoutLayoutStrategy workoutLayout = new WorkoutLayout();

    private static final int NO_ROUTINE_MESSAGE_Y = 300;

    private final EventHandler<Event> tabHandler = i -> {
        final Tab exs = (Tab) i.getSource();

        // update routine selected name
        selectedRoutineName = exs.getText();

        // update description field
        updateDescriptionField();

        // check if user can delete routines.
        btnDeleteRoutine.setDisable(tabRoutine.getTabs().size() < 0);
    };

    /**
     * Show all saved Routines.
     */
    public void initialize() {
        routineCheck();
        getObserver().getRoutines().forEach(i -> {
            final Tab newRoutine = new Tab(i.getLeft());
            newRoutine.setOnSelectionChanged(tabHandler);
            newRoutine.setContent(workoutLayout.addWorkoutNodes(i.getRight()));
            tabRoutine.getTabs().add(newRoutine);
        });
    }

    @FXML
    private void insertData() {
        if (getObserver().addResults()) {
            showDialog("Data inserted!", "Your data has been successfully inserted!", Optional.empty(),
                    AlertType.INFORMATION);
        } else {
            showDialog("Error!", "Your routine data hasn't been saved, check your results inserted", Optional.empty(),
                    AlertType.ERROR);
        }
    }

    @FXML
    private void insertWeight() {
        if (isWeightCorrect() && getObserver().updateWeight()) {
            showDialog("Weight inserted!", "Your data has been successfully inserted!", Optional.empty(),
                    AlertType.INFORMATION);
        } else {
            showDialog("Wrong Weight!", "You have insert a wrong weight! Please insert a double number (e.g. 20.0)",
                    Optional.empty(), AlertType.ERROR);
        }
    }

    @Override
    public Map<String, List<Pair<String, Pair<List<Integer>, Integer>>>> getUserResults() {
        final Map<String, List<Pair<String, Pair<List<Integer>, Integer>>>> results = new HashMap<>();
        tabRoutine.getTabs().stream().filter(tab -> tab.getText().equals(selectedRoutineName))
                .map(i -> (ScrollPane) i.getContent()).map(i -> (VBox) i.getContent()).forEach(exsBox -> {
                    exsBox.getChildren().stream().map(workT -> (TitledPane) workT).map(workBox -> workBox.getContent())
                            .forEach(workout -> {
                        final List<Pair<String, Pair<List<Integer>, Integer>>> result = workoutLayout
                                .getExerciseResults(workout);
                        final TitledPane titled = (TitledPane) workout.getParent().getParent();
                        System.out.println(titled.getText());
                        results.put(titled.getText(), result);
                    });
                });
        return results;
    }

    @FXML
    private void deleteRoutine() {
        if (getObserver().deleteRoutine() && tabRoutine.getTabs().size() > 0) {
            tabRoutine.getTabs().remove(tabRoutine.getTabs().stream()
                    .filter(tab -> selectedRoutineName.equals(tab.getText())).findAny().get());
            routineCheck();

        } else {
            showDialog("Error deleting routine", "Your routine hasn't been deleted", Optional.empty(), AlertType.ERROR);
        }
    }

    @Override
    public String getSelectedRoutine() {
        return selectedRoutineName;
    }

    @Override
    public OptionalDouble getWeight() {
        if (txtKg.getText().isEmpty()) {
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(Double.parseDouble(txtKg.getText()));
    }

    // User can insert its weight but it must be a correct number.
    private boolean isWeightCorrect() {
        if (!txtKg.getText().isEmpty()) {
            try {
                Double.parseDouble(txtKg.getText());
                return true;
            } catch (NumberFormatException e) {
                showDialog("Wrong weight", "Please insert a double value (e.g. 20.0)", Optional.empty(),
                        AlertType.ERROR);
                return false;
            }
        }
        return true;
    }

    private void updateDescriptionField() {
        getObserver().getRoutines().stream().filter(r -> r.getLeft().equals(selectedRoutineName))
                .map(r -> r.getMiddle()).findAny().ifPresent(des -> txtDescription.setText(des));
    }

    private void routineCheck() {
        if (getObserver().getRoutines().isEmpty()) {
            messageLabel.setTranslateY(NO_ROUTINE_MESSAGE_Y);
            messageLabel.setText("Create a new routine from the item in the menu!");
        }
    }

}
