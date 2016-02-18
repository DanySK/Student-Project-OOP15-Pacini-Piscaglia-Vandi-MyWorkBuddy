package it.unibo.oop.myworkoutbuddy.view.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.IntStream;

import it.unibo.oop.myworkoutbuddy.util.MutablePair;
import it.unibo.oop.myworkoutbuddy.util.MutableTriple;
import it.unibo.oop.myworkoutbuddy.util.Pair;
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

    private static final int FIELD_WIDTH = 45;

    @FXML
    private void insertData() {
        // only for testing
        System.out.println(getUserResults());
        //
        final String message;
        final String title;
        if (ViewsHandler.getObserver().addResults()) {
            message = "Your data has been successfully inserted!";
            title = "Data inserted!";
        } else {
            message = "There was an error!";
            title = "Error!";
        }
        FxWindowFactory.showDialog(title, message, Optional.empty(), AlertType.INFORMATION);
    }

    @Override
    public Map<String, Pair<List<Integer>, Integer>> getUserResults() {
        final Map<String, Pair<List<Integer>, Integer>> results = new HashMap<>();
        tabRoutine.getTabs().stream().map(i -> (VBox) i.getContent()).forEach(exsBox -> {
            exsBox.getChildren().forEach(workTitled -> {

                final TitledPane workoutTitled = (TitledPane) workTitled;
                final VBox exVbox = (VBox) workoutTitled.getContent();
                exVbox.getChildren().forEach(exRow -> {

                    final HBox exs = (HBox) exRow;
                    final Label exName = (Label) exs.getChildren().get(0);
                    final List<Integer> reps = new ArrayList<>();
                    IntStream.range(2, exs.getChildren().size() - 2).forEach(i -> {
                        final TextField repField = (TextField) exs.getChildren().get(i);
                        reps.add(Integer.valueOf(repField.getText()));
                    });

                    final TextField kgField = (TextField) exs.getChildren().get(exs.getChildren().size() - 1);
                    final Pair<List<Integer>, Integer> repKg = new MutablePair<>(reps,
                            Integer.valueOf(kgField.getText()));
                    results.put(exName.getText(), repKg);
                });
            });
        });
        return results;
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
        map.put("Exercise", new LinkedList<>(Arrays.asList(8, 10, 12)));
        final Map<String, Map<String, List<Integer>>> map2 = new TreeMap<>();
        map2.put("Workout", map);
        rou.setZ(map2);
        set.add(rou);
        //
        ViewsHandler.getObserver().getRoutines().forEach(i -> {
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
                    box.getChildren().add(new Label(exName));
                    box.getChildren().add(new Label("- Repetitions: "));
                    repetitions.forEach(rep -> box.getChildren().add(new TextField(rep.toString())));
                    box.getChildren().stream()
                            .filter(rep -> rep.getClass().equals(TextField.class))
                            .map(rep -> (TextField) rep)
                            .forEach(rep -> rep.setMaxWidth(FIELD_WIDTH));

                    box.getChildren().add(new Label("  - Kg: "));
                    final TextField kgField = new TextField("0");
                    kgField.setMaxWidth(FIELD_WIDTH);
                    box.getChildren().add(kgField);
                    exercisesList.getChildren().add(box);
                });
                workout.getChildren().add(new TitledPane(workName, exercisesList));
            });
            newRoutine.setContent(workout);
        });
    }

}
