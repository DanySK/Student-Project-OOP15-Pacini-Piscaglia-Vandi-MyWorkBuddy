package it.unibo.oop.myworkoutbuddy.view.handlers;

import static it.unibo.oop.myworkoutbuddy.view.handlers.ViewsHandler.getObserver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import it.unibo.oop.myworkoutbuddy.util.MutableTriple;
import it.unibo.oop.myworkoutbuddy.util.Pair;
import it.unibo.oop.myworkoutbuddy.util.Triple;
import it.unibo.oop.myworkoutbuddy.view.SelectRoutineView;
import it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
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

    private final WorkoutLayoutStrategy workoutLayout = new WorkoutLayout();

    private final EventHandler<Event> descriptionHandler = i -> {
        final Tab exs = (Tab) i.getSource();
        // getObserver().getRoutines().stream()
        // .filter(r -> r.getX().equals(exs.getText()))
        // .map(r -> r.getY())
        // .findAny()
        // .ifPresent(des -> txtDescription.setText(des));
    };

    @FXML
    private void insertData() {
        System.out.println(getUserResults());
        final String message;
        final String title;
        final AlertType dialogType;
        if (getObserver().addResults()) {
            message = "Your data has been successfully inserted!";
            title = "Data inserted!";
            dialogType = AlertType.INFORMATION;
        } else {
            message = "There was an error!";
            title = "Error!";
            dialogType = AlertType.ERROR;
        }
        FxWindowFactory.showDialog(title, message, Optional.empty(), dialogType);
    }

    @Override
    public Map<String, Pair<List<Integer>, Integer>> getUserResults() {
        final Map<String, Pair<List<Integer>, Integer>> results = new HashMap<>();
        tabRoutine.getTabs().stream()
                .map(i -> (VBox) i.getContent())
                .forEach(exsBox -> {
                    exsBox.getChildren().stream()
                            .map(workT -> (TitledPane) workT)
                            .map(exBox -> (VBox) exBox.getContent())
                            .forEach(exVbox -> {
                        exVbox.getChildren().stream()
                                .map(exs -> (HBox) exs)
                                .forEach(exs -> {
                            final Pair<String, Pair<List<Integer>, Integer>> result = workoutLayout
                                    .getExerciseResults(exs);
                            results.put(result.getX(), result.getY());
                        });
                    });
                });
        /*
         * tabRoutine.getTabs().stream()
         * .map(i -> (VBox) i.getContent())
         * .forEach(exsBox -> {
         * exsBox.getChildren().stream()
         * .map(workT -> (TitledPane) workT)
         * .map(exBox -> (VBox) exBox.getContent())
         * .forEach(exVbox -> {
         * exVbox.getChildren().stream()
         * .map(exs -> (HBox) exs)
         * .forEach(exs -> {
         * final Label exName = (Label) exs.getChildren().get(0);
         * final List<Integer> reps = new ArrayList<>();
         * IntStream.range(2, exs.getChildren().size() - 2).forEach(i -> {
         * final TextField repField = (TextField) exs.getChildren().get(i);
         * reps.add(Integer.valueOf(repField.getText()));
         * });
         * final TextField kgField = (TextField)
         * exs.getChildren().get(exs.getChildren().size() - 1);
         * final Pair<List<Integer>, Integer> repKg = new MutablePair<>(reps,
         * Integer.valueOf(kgField.getText()));
         * results.put(exName.getText(), repKg);
         * });
         * });
         * });
         */
        /*
         * tabRoutine.getTabs().stream().map(i -> (VBox)
         * i.getContent()).forEach(exsBox -> {
         * exsBox.getChildren().forEach(workTitled -> {
         * 
         * final TitledPane workoutTitled = (TitledPane) workTitled;
         * final VBox exVbox = (VBox) workoutTitled.getContent();
         * exVbox.getChildren().forEach(exRow -> {
         * 
         * final HBox exs = (HBox) exRow;
         * final Label exName = (Label) exs.getChildren().get(0);
         * final List<Integer> reps = new ArrayList<>();
         * IntStream.range(2, exs.getChildren().size() - 2).forEach(i -> {
         * final TextField repField = (TextField) exs.getChildren().get(i);
         * reps.add(Integer.valueOf(repField.getText()));
         * });
         * 
         * final TextField kgField = (TextField)
         * exs.getChildren().get(exs.getChildren().size() - 1);
         * final Pair<List<Integer>, Integer> repKg = new MutablePair<>(reps,
         * Integer.valueOf(kgField.getText()));
         * results.put(exName.getText(), repKg);
         * });
         * });
         * });
         */
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
        final Triple<Integer, String, Map<String, Map<String, List<Integer>>>> rou = new MutableTriple<>(0,
                "routine1 description",
                new TreeMap<>());
        final Map<String, List<Integer>> map = new TreeMap<>();
        map.put("Exercise", new LinkedList<>(Arrays.asList(8, 10, 12)));
        final Map<String, Map<String, List<Integer>>> map2 = new TreeMap<>();
        map2.put("Workout", map);
        rou.setZ(map2);
        set.add(rou);
        //
        final Triple<Integer, String, Map<String, Map<String, List<Integer>>>> rou2 = new MutableTriple<>(0,
                "routine2 description",
                new TreeMap<>());
        final Map<String, List<Integer>> map22 = new TreeMap<>();
        map22.put("Exercise2", new LinkedList<>(Arrays.asList(8, 10, 12)));
        final Map<String, Map<String, List<Integer>>> map3 = new TreeMap<>();
        map3.put("Workout2", map22);
        rou2.setZ(map3);
        set.add(rou2);
        //
        // getObserver().getRoutines()
        set.forEach(i -> {
            final int routineIndex = i.getX();
            final Tab newRoutine = new Tab(String.valueOf(routineIndex));
            newRoutine.setOnSelectionChanged(descriptionHandler);
            tabRoutine.getTabs().add(newRoutine);
            newRoutine.setContent(workoutLayout.addWorkoutNodes(i.getZ()));
        });
    }

}
