package it.unibo.oop.myworkoutbuddy.view.handlers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import it.unibo.oop.myworkoutbuddy.util.MutableTriple;
import it.unibo.oop.myworkoutbuddy.util.Triple;
import it.unibo.oop.myworkoutbuddy.view.SelectRoutineView;
import it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private void insertData() {
        String message;
        String title;
        // if (observer.addResults) {
        message = "Your data has been successfully inserted!";
        title = "Data inserted!";
        // } else {
        // message = "There was an error!";
        // title = "Error!";
        // }
        FxWindowFactory.showDialog(title, message, Optional.empty(), AlertType.INFORMATION);
    }

    @Override
    public Map<String, List<Integer>> getUserResults() {
        return null;
    }

    @Override
    public int getRoutineIndex() {
        return 0;
    }

    /**
     * Show all saved Routines.
     */
    public void initialize() {
        // only for testing
        final Set<Triple<Integer, String, Map<String, Map<String, List<Integer>>>>> set = new HashSet<>();
        final Triple<Integer, String, Map<String, Map<String, List<Integer>>>> rou = new MutableTriple<>(0, "routine1",
                new TreeMap<>());
        final Map<String, List<Integer>> map = new TreeMap<>();
        map.put("Exercise - ", new LinkedList<>(Arrays.asList(8, 10, 12)));
        final Map<String, Map<String, List<Integer>>> map2 = new TreeMap<>();
        map2.put("Workout", map);
        rou.setZ(map2);
        set.add(rou);
        //
        set.forEach(i -> {
            final int routineIndex = i.getX();
            final String routineDescription = i.getY();
            txtDescription.setText(routineDescription);
            final Tab newRoutine = new Tab("Routine " + routineIndex);
            tabRoutine.getTabs().add(newRoutine);
            final VBox workout = new VBox();
            i.getZ().forEach((workName, exercises) -> {
                final VBox exercisesList = new VBox();
                exercises.forEach((exName, repetitions) -> {
                    final HBox box = new HBox();
                    box.getChildren().add(new Label(exName + "Repetitions: "));
                    box.getChildren().add(new TextField(repetitions.toString()));
                    box.getChildren().add(new TextField("Kg"));
                    exercisesList.getChildren().add(box);
                });
                workout.getChildren().add(new TitledPane(workName, exercisesList));
            });
            newRoutine.setContent(workout);
        });
    }

}
