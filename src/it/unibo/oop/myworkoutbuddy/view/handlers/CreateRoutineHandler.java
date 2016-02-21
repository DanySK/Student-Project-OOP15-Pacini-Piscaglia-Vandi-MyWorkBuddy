package it.unibo.oop.myworkoutbuddy.view.handlers;

import static it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory.showDialog;
import static it.unibo.oop.myworkoutbuddy.view.handlers.ViewsHandler.getObserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import it.unibo.oop.myworkoutbuddy.view.CreateRoutineView;
import it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * Handler of the CreateRoutineView. It collects user routine data.
 */
public final class CreateRoutineHandler implements CreateRoutineView {

    @FXML
    private VBox workoutBox;

    @FXML
    private TabPane exercisePane;

    @FXML
    private Button btnAddWorkout;

    @FXML
    private Button btnAddExercise;

    @FXML
    private TextField txtDescription;

    private static final int REPS_MAX_WIDTH = 40;

    private Optional<VBox> workoutSelected = Optional.empty();

    private Optional<Label> exerciseSelected = Optional.empty();

    private CreateRoutineCheckStrategy checkStrategy = new CreateRoutineCheck();

    private final Map<String, Map<String, List<Integer>>> routine = new HashMap<>();

    private final EventHandler<MouseEvent> selectWorkoutHandler = event -> {
        if (checkStrategy.isWorkoutToBeSet(workoutSelected, btnAddExercise, event)) {
            workoutSelected = Optional.of((VBox) event.getSource());
        }
    };

    private final EventHandler<MouseEvent> selectExerciseHandler = event -> {

        if (checkStrategy.hasExBeenChanged(exerciseSelected, event)) {
            exerciseSelected.get().setId("exercise");
        }
        final Label selLabel = ((Label) event.getSource());
        selLabel.setId("selectedExercise");
        exerciseSelected = Optional.of(selLabel);
    };

    @FXML
    private void saveRoutine() {
        if (checkStrategy.hasRoutineBeenSaved()) {
            showDialog("Routine saved!", "Your routine has been saved!", Optional.empty(),
                    AlertType.INFORMATION);
        }
    }

    @FXML
    private void addWorkout() {
        if (checkStrategy.canAddWorkout(workoutBox, btnAddWorkout)) {
            workoutSelected = Optional.of(new VBox());

            // Ask user to assign a title to the new Workout.
            workoutSelected.get().addEventHandler(MouseEvent.MOUSE_CLICKED, selectWorkoutHandler);
            workoutBox.getChildren().add(new TitledPane(
                    FxWindowFactory.createInputDialog("Workout name", "Insert your workout name:", "A,B,C, Crunch,..."),
                    workoutSelected.get()));
        }
    }

    @FXML
    private void addExercise() {

        if (checkStrategy.canAddExercise(workoutSelected, exerciseSelected, btnAddExercise)) {
            workoutSelected.get().getChildren().add(buildExerciseBox());
        }
        // for testing
        System.out.println(getRoutine());
    }

    @FXML
    private void showExercise() {
        if (checkStrategy.canShowExercise(exerciseSelected)) {
            showDialog(exerciseSelected.get().getText(),
                    getObserver().getExerciseInfo(exerciseSelected.get().getText()).get(0), Optional
                            .of(getObserver().getExerciseInfo(exerciseSelected.get().getText()).get(1)),
                    AlertType.INFORMATION);
        }
    }

    @FXML
    private void deleteWorkout() {
        if (checkStrategy.canDeleteWorkout(workoutBox, workoutSelected, btnAddWorkout)) {
            workoutBox.getChildren().remove(workoutSelected.get().getParent().getParent());
            workoutSelected = Optional.empty();
        }
    }

    @FXML
    private void deleteExercise() {

        if (checkStrategy.canDeleteExercise(workoutSelected, exerciseSelected, btnAddExercise)) {
            workoutSelected.get().getChildren().remove(exerciseSelected.get().getParent());
            exerciseSelected = Optional.empty();
        }
    }

    @Override
    public Map<String, Map<String, List<Integer>>> getRoutine() {
        workoutBox.getChildren().forEach(workouts -> {
            final TitledPane tPane = (TitledPane) workouts;
            final Map<String, List<Integer>> exercises = new HashMap<>();
            final VBox exBox = (VBox) tPane.getContent();
            exBox.getChildren().forEach(ex -> {
                final HBox exInfo = (HBox) ex;
                final List<Integer> repList = new ArrayList<>();
                final Label exLabel = (Label) exInfo.getChildren().get(0);
                IntStream.range(1, 4).forEach(i -> {
                    final TextField repNumber = (TextField) exInfo.getChildren().get(i);
                    repList.add(Integer.parseInt(repNumber.getText()));
                });
                exercises.put(exLabel.getText(), repList);
            });
            routine.put(tPane.getText(), exercises);
        });
        return routine;
    }

    @Override
    public String getRoutineDescription() {
        return txtDescription.getText();
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed. It is used to show all the available exercises to
     * add in a routine.
     */
    public void initialize() {
        // only for testing
        final Map<String, Set<String>> prova = new HashMap<>();
        prova.put("Massa", new HashSet<>(Arrays.asList("alzate", "panca")));
        prova.put("Forza", new HashSet<>(Arrays.asList("crunch", "elastici")));
        //
        getObserver().getExercises().forEach((section, exs) -> {
            final Tab newSection = new Tab(section);
            newSection.setId("exerciseSection");
            exercisePane.getTabs().add(newSection);
            final VBox workout = new VBox();
            workout.setId("workout");
            exs.forEach(ex -> {
                final Label exLabel = new Label(ex);
                exLabel.setId("exercise");
                exLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, selectExerciseHandler);
                workout.getChildren().add(exLabel);
            });
            newSection.setContent(workout);
        });
    }

    private Node buildExerciseBox() {
        final HBox exBox = new HBox();
        final Label newExercise = new Label(exerciseSelected.get().getText());
        final List<TextField> repsField = new ArrayList<>();
        IntStream.range(0, 3).forEach(i -> repsField.add(new TextField("0")));
        IntStream.range(0, 3).forEach(i -> repsField.get(i).setMaxWidth(REPS_MAX_WIDTH));
        exBox.getChildren().add(newExercise);
        IntStream.range(0, 3).forEach(i -> exBox.getChildren().add(repsField.get(i)));
        newExercise.addEventHandler(MouseEvent.MOUSE_CLICKED, selectExerciseHandler);
        newExercise.setId("exercise");
        return exBox;
    }

}
