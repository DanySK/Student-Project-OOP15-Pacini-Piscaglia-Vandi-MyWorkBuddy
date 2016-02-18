package it.unibo.oop.myworkoutbuddy.view.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.unibo.oop.myworkoutbuddy.view.CreateRoutineView;
import static it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory.showDialog;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
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
    private Button btnAddWorkout;

    @FXML
    private Button btnAddExercise;

    @FXML
    private TabPane exercisePane;

    @FXML
    private VBox workoutBox;

    @FXML
    private TextField txtDescription;

    private final Map<String, Map<String, List<Integer>>> routine = new HashMap<>();

    private static final int MAX_WORKOUTS = 4;

    private static final int MAX_EXERCISES = 6;

    private static final int REPS_MAX_WIDTH = 40;

    private Optional<VBox> workoutSelected = Optional.empty();

    private Optional<Label> exerciseSelected = Optional.empty();

    private final EventHandler<MouseEvent> selectWorkoutHandler = i -> {
        if ((workoutSelected.isPresent() && workoutSelected.get() != i.getSource())
                || !workoutSelected.isPresent()) {
            workoutSelected = Optional.of((VBox) i.getSource());
        }
        btnAddExercise.setDisable(childrenCount(workoutSelected.get()) == MAX_EXERCISES);
    };

    private final EventHandler<MouseEvent> selectExerciseHandler = i -> {

        if (exerciseSelected.isPresent() && exerciseSelected.get() != i.getSource()) {
            exerciseSelected.get().setStyle(null);
        }

        final Label selLabel = ((Label) i.getSource());
        selLabel.setStyle("-fx-font-weight:bold");
        exerciseSelected = Optional.of(selLabel);
    };

    @FXML
    private void saveRoutine() {
        // observer.saveRoutine();
        showDialog("Routine saved!", "Your routine has been saved!", Optional.empty(),
                AlertType.INFORMATION);
    }

    @FXML
    private void addWorkout() {
        if (childrenCount(workoutBox) < MAX_WORKOUTS) {
            workoutSelected = Optional.of(new VBox());
            final TitledPane newWorkout = new TitledPane("Workout", workoutSelected.get());
            workoutSelected.get().addEventHandler(MouseEvent.MOUSE_CLICKED, selectWorkoutHandler);
            workoutBox.getChildren().add(newWorkout);

            // to getRoutine
            routine.put("Workout", new HashMap<>());
        }
        if (childrenCount(workoutBox) == MAX_WORKOUTS) {
            btnAddWorkout.setDisable(true);
            showDialog("Limit reached", "Max addable workouts limit reached", Optional.empty(),
                    AlertType.ERROR);
        }
    }

    @FXML
    private void addExercise() {
        if (workoutSelected.isPresent() && childrenCount(workoutSelected.get()) < MAX_EXERCISES) {

            final HBox exBox = new HBox();
            final Label newExercise = new Label("exercise");
            final List<TextField> repsField = new ArrayList<>();
            IntStream.range(0, 3).forEach(i -> repsField.add(new TextField("10")));
            IntStream.range(0, 3).forEach(i -> repsField.get(i).setMaxWidth(REPS_MAX_WIDTH));
            exBox.getChildren().add(newExercise);
            IntStream.range(0, 3).forEach(i -> exBox.getChildren().add(repsField.get(i)));
            newExercise.addEventHandler(MouseEvent.MOUSE_CLICKED, selectExerciseHandler);
            workoutSelected.get().getChildren().add(exBox);

            // to getRoutine
            final List<Integer> reps = repsField.stream().map(TextField::getText).map(Integer::valueOf)
                    .collect(Collectors.toList());

            routine.get(getWorkName()).put(newExercise.getText(), reps);

        } else if (!workoutSelected.isPresent()) {
            showDialog("Error adding workout", "You have to had a workout first!", Optional.empty(),
                    AlertType.ERROR);

        } else if (childrenCount(workoutSelected.get()) >= MAX_EXERCISES) {
            btnAddExercise.setDisable(true);
            showDialog("Limit reached", "Max addable exercises limit reached", Optional.empty(),
                    AlertType.ERROR);
        }
        System.out.println(getRoutine());
    }

    @FXML
    private void showExercise() {
        showDialog("Exercise", "Exercise description", Optional
                .of("http://workouts.menshealth.com/sites/workouts.menshealth.com/files/back-and-biceps-builder.jpg"),
                AlertType.INFORMATION);
    }

    @FXML
    private void deleteWorkout() {

        // the double call to getParent() select first VBox -> AnchorPane->
        // and then the wanted AnchorPane -> TitlePane to remove

        if (childrenCount(workoutBox) > 0 && workoutSelected.isPresent()) {

            // to getRoutine
            routine.remove(getWorkName());

            workoutBox.getChildren().remove(workoutSelected.get().getParent().getParent());
            workoutSelected = Optional.empty();

            btnAddWorkout.setDisable(!btnAddWorkout.isDisabled());

        } else if (childrenCount(workoutBox) == 0) {
            showDialog("Error", "There aren't workouts added!", Optional.empty(), AlertType.ERROR);
        } else if (!workoutSelected.isPresent()) {
            showDialog("Error", "No workout selected!", Optional.empty(), AlertType.ERROR);
        }
    }

    @FXML
    private void deleteExercise() {

        if (!exerciseSelected.isPresent()) {
            showDialog("Error", "You have to select an exercise first!", Optional.empty(),
                    AlertType.ERROR);
            return;
        }
        if (workoutSelected.isPresent() && exerciseSelected.isPresent()) {
            btnAddExercise.setDisable(!btnAddExercise.isDisabled());

            // to getRoutine
            routine.get(getWorkName()).remove("exercise");

            workoutSelected.get().getChildren().remove(exerciseSelected.get().getParent());
            exerciseSelected = Optional.empty();
        }
        workoutSelected.ifPresent(i -> {

        });
    }

    @Override
    public Map<String, Map<String, List<Integer>>> getRoutine() {
        return routine;
    }

    @Override
    public String getRoutineDescription() {
        return txtDescription.getText();
    }

    private String getWorkName() {
        final TitledPane pane = (TitledPane) workoutSelected.get().getParent().getParent();
        return pane.getText();
    }

    private int childrenCount(final Parent parent) {
        return parent.getChildrenUnmodifiable().size();
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     */
    public void initialize() {
        ViewsHandler.getObserver().getExercises().forEach((section, exs) -> {
            final Tab newSection = new Tab(section);
            exercisePane.getTabs().add(newSection);
            final VBox workout = new VBox();
            exs.forEach(ex -> {
                workout.getChildren().add(new Label(ex));
            });
            newSection.setContent(workout);
        });
    }

}
