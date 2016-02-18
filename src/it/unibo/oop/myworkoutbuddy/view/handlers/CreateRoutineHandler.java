package it.unibo.oop.myworkoutbuddy.view.handlers;

import static it.unibo.oop.myworkoutbuddy.view.factory.FxWindowFactory.showDialog;

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
    private VBox workoutBox;

    @FXML
    private TabPane exercisePane;

    @FXML
    private Button btnAddWorkout;

    @FXML
    private Button btnAddExercise;

    @FXML
    private TextField txtDescription;

    private static final int MAX_WORKOUTS = 4;

    private static final int MAX_EXERCISES = 6;

    private static final int REPS_MAX_WIDTH = 40;

    private Optional<VBox> workoutSelected = Optional.empty();

    private Optional<Label> exerciseSelected = Optional.empty();

    private final Map<String, Map<String, List<Integer>>> routine = new HashMap<>();

    private final EventHandler<MouseEvent> selectWorkoutHandler = i -> {
        if ((workoutSelected.isPresent() && workoutSelected.get() != i.getSource())
                || !workoutSelected.isPresent()) {
            workoutSelected = Optional.of((VBox) i.getSource());
        }
        btnAddExercise.setDisable(childrenCount(workoutSelected.get()) == MAX_EXERCISES);
    };

    private final EventHandler<MouseEvent> selectExerciseHandler = i -> {

        if (exerciseSelected.isPresent() && exerciseSelected.get() != i.getSource()) {
            exerciseSelected.get().setStyle("-fx-font-size: 18");
        }

        final Label selLabel = ((Label) i.getSource());
        selLabel.setStyle("-fx-font-weight:bold; -fx-background-color: lightBlue; -fx-font-size: 18");
        exerciseSelected = Optional.of(selLabel);
    };

    @FXML
    private void saveRoutine() {
        if (ViewsHandler.getObserver().saveRoutine()) {
            showDialog("Routine saved!", "Your routine has been saved!", Optional.empty(),
                    AlertType.INFORMATION);
        } else {
            showDialog("Error saving routine", "You have inserted wrong data!", Optional.empty(), AlertType.ERROR);
        }
    }

    @FXML
    private void addWorkout() {
        if (childrenCount(workoutBox) < MAX_WORKOUTS) {
            workoutSelected = Optional.of(new VBox());
            // Ask user to assign a title to the new Workout.
            final TitledPane newWorkout = new TitledPane(
                    FxWindowFactory.createInputDialog("Workout name", "Insert your workout name:", "A,B,C, Crunch,..."),
                    workoutSelected.get());
            workoutSelected.get().addEventHandler(MouseEvent.MOUSE_CLICKED, selectWorkoutHandler);
            workoutBox.getChildren().add(newWorkout);
        }

        if (childrenCount(workoutBox) == MAX_WORKOUTS) {
            btnAddWorkout.setDisable(true);
            showDialog("Limit reached", "Max addable workouts limit reached", Optional.empty(),
                    AlertType.ERROR);
        }
    }

    @FXML
    private void addExercise() {
        if (workoutSelected.isPresent() && childrenCount(workoutSelected.get()) < MAX_EXERCISES
                && exerciseSelected.isPresent() && !isAlreadyInserted(exerciseSelected.get().getText())) {

            final HBox exBox = new HBox();
            final Label newExercise = new Label(exerciseSelected.get().getText());
            final List<TextField> repsField = new ArrayList<>();
            IntStream.range(0, 3).forEach(i -> repsField.add(new TextField("0")));
            IntStream.range(0, 3).forEach(i -> repsField.get(i).setMaxWidth(REPS_MAX_WIDTH));
            exBox.getChildren().add(newExercise);
            IntStream.range(0, 3).forEach(i -> exBox.getChildren().add(repsField.get(i)));
            newExercise.addEventHandler(MouseEvent.MOUSE_CLICKED, selectExerciseHandler);
            workoutSelected.get().getChildren().add(exBox);

        } else if (!workoutSelected.isPresent()) {
            showDialog("Error adding workout", "You have to had a workout first!", Optional.empty(),
                    AlertType.ERROR);

        } else if (childrenCount(workoutSelected.get()) >= MAX_EXERCISES) {
            btnAddExercise.setDisable(true);
            showDialog("Limit reached", "Max addable exercises limit reached", Optional.empty(),
                    AlertType.ERROR);
        } else if (!exerciseSelected.isPresent()) {
            showDialog("No exercise selected", "Please select an exercise from the list", Optional.empty(),
                    AlertType.ERROR);
        } else if (isAlreadyInserted(exerciseSelected.get().getText())) {
            showDialog("Exercise is already added", "You can't add a same exercise twice in the same workout",
                    Optional.empty(), AlertType.ERROR);
        }
        System.out.println(getRoutine());
    }

    @FXML
    private void showExercise() {
        if (exerciseSelected.isPresent()) {
            showDialog(exerciseSelected.get().getText(),
                    ViewsHandler.getObserver().getExerciseInfo(exerciseSelected.get().getText()).get(0), Optional
                            .of("http://workouts.menshealth.com/sites/workouts.menshealth.com/files/back-and-biceps-builder.jpg"),
                    AlertType.INFORMATION);
        } else {
            showDialog("No exercise selected", "Please select an exercise to show from the list", Optional.empty(),
                    AlertType.ERROR);
        }
    }

    @FXML
    private void deleteWorkout() {

        // the double call to getParent() select first VBox -> AnchorPane->
        // and then the wanted AnchorPane -> TitlePane to remove

        if (childrenCount(workoutBox) > 0 && workoutSelected.isPresent()) {

            workoutBox.getChildren().remove(workoutSelected.get().getParent().getParent());
            workoutSelected = Optional.empty();

            btnAddWorkout.setDisable(false);

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

    private boolean isAlreadyInserted(final String exercise) {
        System.out.println(exercise);
        return workoutSelected.get().getChildren().stream()
                .map(exBox -> (HBox) exBox)
                .map(ex -> (Label) ex.getChildren().get(0))
                .map(exLabel -> exLabel.getText())
                .anyMatch(exName -> exName.equals(exercise));
    }

    private int childrenCount(final Parent parent) {
        return parent.getChildrenUnmodifiable().size();
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
        ViewsHandler.getObserver().getExercises().forEach((section, exs) -> {
            final Tab newSection = new Tab(section);
            exercisePane.getTabs().add(newSection);
            final VBox workout = new VBox();
            exs.forEach(ex -> {
                final Label exLabel = new Label(ex);
                exLabel.setStyle("-fx-font-size: 18");
                exLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, selectExerciseHandler);
                workout.getChildren().add(exLabel);
            });
            newSection.setContent(workout);
        });
    }

}
