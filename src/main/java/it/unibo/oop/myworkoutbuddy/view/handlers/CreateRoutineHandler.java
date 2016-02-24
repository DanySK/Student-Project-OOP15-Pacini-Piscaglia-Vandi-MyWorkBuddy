package it.unibo.oop.myworkoutbuddy.view.handlers;

import static it.unibo.oop.myworkoutbuddy.view.handlers.ViewsHandler.getObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import it.unibo.oop.myworkoutbuddy.view.CreateRoutineView;
import it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    private TextField txtDescription;

    private static final int REPS_MAX_WIDTH = 40;

    private static final int TAB_PANE_WIDTH = 250;

    private static final int TAB_PANE_HEIGHT = 500;

    private static final int REPS_FIELD_TRANSLATE_Y = 10;

    private static final int REP_LABEL_TRANSLATE_Y = 13;

    private Optional<VBox> workoutSelected = Optional.empty();

    private Optional<Label> exerciseSelected = Optional.empty();

    private final CreateRoutineCheckStrategy checkStrategy = new CreateRoutineCheck();

    private final Map<String, Map<String, List<Integer>>> routine = new HashMap<>();

    private final EventHandler<MouseEvent> selectWorkoutHandler = event -> {
        if (checkStrategy.isWorkoutToBeSet(workoutSelected, event)) {
            workoutSelected = Optional.of((VBox) event.getSource());
        }
    };

    private final EventHandler<MouseEvent> selectExerciseHandler = event -> {

        if (checkStrategy.hasExBeenChanged(exerciseSelected, event)) {
            exerciseSelected.get().setId("exerciseToSelect");
        }
        final Label selLabel = ((Label) event.getSource());
        selLabel.setId("selectedExercise");
        exerciseSelected = Optional.of(selLabel);
    };

    @FXML
    private void saveRoutine() {
        if (checkStrategy.canRoutineBeenSaved(workoutBox) && checkStrategy.hasRoutineBeenSaved()) {
            // clear routine fields after routine creation.
            workoutBox.getChildren().clear();
        }
    }

    @FXML
    private void addWorkout() {
        if (checkStrategy.canAddWorkout(workoutBox)) {
            workoutSelected = Optional.of(new VBox());

            // Ask user to assign a title to the new Workout.
            workoutSelected.get().addEventHandler(MouseEvent.MOUSE_CLICKED, selectWorkoutHandler);
            final String workoutName = FxWindowFactory.createInputDialog("Workout name", "Insert your workout name:",
                    "A,B,C, Crunch,...");
            if (!checkStrategy.isWorkoutAlreadyAdded(workoutName, workoutBox)) {
                workoutBox.getChildren().add(new TitledPane(workoutName, workoutSelected.get()));
            }
        }
    }

    @FXML
    private void addExercise() {
        if (checkStrategy.canAddExercise(workoutSelected, exerciseSelected)) {
            workoutSelected.get().getChildren().add(buildExerciseBox());
            exerciseSelected.get().setId("exerciseToSelect");
            exerciseSelected = Optional.empty();
        }
    }

    @FXML
    private void showExercise() {
        if (checkStrategy.canShowExercise(exerciseSelected)) {
            FxWindowFactory.showDialog(exerciseSelected.get().getText(),
                    getObserver().getExerciseInfo(exerciseSelected.get().getText()).get("description"),
                    Optional.of(getObserver().getExerciseInfo(exerciseSelected.get().getText()).get("videoURL")),
                    AlertType.INFORMATION);
        }
    }

    @FXML
    private void deleteWorkout() {
        if (checkStrategy.canDeleteWorkout(workoutBox, workoutSelected)) {
            workoutBox.getChildren().remove(workoutSelected.get().getParent().getParent());
            workoutSelected = Optional.empty();
        }
    }

    @FXML
    private void deleteExercise() {
        if (checkStrategy.canDeleteExercise(workoutSelected, exerciseSelected)) {
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
            exBox.getChildren().stream().map(ex -> (HBox) ex).forEach(ex -> {
                final List<Integer> repList = new ArrayList<>();
                final Label exLabel = (Label) ex.getChildren().get(0);
                IntStream.range(2, 5).forEach(i -> {
                    final TextField repNumber = (TextField) ex.getChildren().get(i);
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
        getObserver().getExercises().forEach((section, exs) -> {
            final Tab newSection = new Tab(section);
            newSection.setId("exerciseSection");
            exercisePane.getTabs().add(newSection);
            final ScrollPane scroll = new ScrollPane();
            final VBox workout = new VBox();
            workout.setPrefWidth(TAB_PANE_WIDTH);
            workout.setPrefHeight(TAB_PANE_HEIGHT);
            workout.setId("workout");
            exs.forEach(ex -> {
                final Label exLabel = new Label(ex);
                exLabel.setId("exerciseToSelect");
                exLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, selectExerciseHandler);
                workout.getChildren().add(exLabel);
            });
            scroll.setContent(workout);
            newSection.setContent(scroll);
        });
    }

    private Node buildExerciseBox() {
        final HBox exBox = new HBox();
        final Label newExercise = new Label(exerciseSelected.get().getText());
        newExercise.addEventHandler(MouseEvent.MOUSE_CLICKED, selectExerciseHandler);
        newExercise.setId("exerciseToSelect");
        final List<TextField> repsField = new ArrayList<>();
        IntStream.range(0, 3).forEach(i -> repsField.add(new TextField("0")));
        IntStream.range(0, 3).forEach(i -> repsField.get(i).setTranslateY(REPS_FIELD_TRANSLATE_Y));
        IntStream.range(0, 3).forEach(i -> repsField.get(i).setMaxWidth(REPS_MAX_WIDTH));
        exBox.getChildren().add(newExercise);
        final Label repLabel = new Label(" - Repetitions: ");
        repLabel.setTranslateY(REP_LABEL_TRANSLATE_Y);
        exBox.getChildren().add(repLabel);
        IntStream.range(0, 3).forEach(i -> exBox.getChildren().add(repsField.get(i)));
        return exBox;
    }

}
