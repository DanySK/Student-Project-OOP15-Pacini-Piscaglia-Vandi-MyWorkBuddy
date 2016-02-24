package it.unibo.oop.myworkoutbuddy.view.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Implementation of the building strategy of the GUI routine. It also provides
 * a method to encapsulate the strategy to get exercises results.
 *
 */
public final class WorkoutLayout implements WorkoutLayoutStrategy {

    private static final int FIELD_WIDTH = 45;

    private static final int EXERCISE_BOX_WIDTH = 614;

    /*@Override
    public Node addWorkoutNodes(final Map<String, Map<String, List<Integer>>> workouts) {
        final VBox workout = new VBox();
        workouts.forEach((workName, exercises) -> {
            final VBox exercisesList = new VBox();
            exercises.forEach((exName, repetitions) -> {
                exercisesList.getChildren().add(exerciseBoxBuild(exName, repetitions));
            });
            workout.getChildren().add(new TitledPane(workName, exercisesList));
        });
        return new ScrollPane(workout);
    }*/
    
    @Override
    public Node addWorkoutNodes(final Map<String, Map<String, List<Integer>>> workouts) {
        final VBox workout = new VBox();
        workouts.forEach((workName, exercises) -> {
            final GridPane exercisesList = new GridPane();
            exercises.forEach((exName, repetitions) -> {
                exercisesList.getChildren().add(exerciseBoxBuild(exName, repetitions));
            });
            workout.getChildren().add(new TitledPane(workName, exercisesList));
        });
        return new ScrollPane(workout);
    }
    
    @Override
    public Pair<String, Pair<List<Integer>, Integer>> getExerciseResults(final Node workout) {
        final HBox exs = (HBox) workout;
        final Label exName = (Label) exs.getChildren().get(0);
        final List<Integer> reps = new ArrayList<>();
        IntStream.range(2, exs.getChildren().size() - 2).forEach(i -> {
            final TextField repField = (TextField) exs.getChildren().get(i);
            reps.add(Integer.valueOf(repField.getText()));
        });
        final TextField kgField = (TextField) exs.getChildren().get(exs.getChildren().size() - 1);
        return new ImmutablePair<String, Pair<List<Integer>, Integer>>(exName.getText(),
                new ImmutablePair<>(reps, Integer.valueOf(kgField.getText())));
    }

    private Node exerciseBoxBuild(final String exName, final List<Integer> repetitions) {
        final HBox box = new HBox();
        box.getChildren().add(new Label(exName));
        box.getChildren().add(new Label("- Repetitions: "));
        repetitions.forEach(rep -> box.getChildren().add(new TextField(rep.toString())));
        box.getChildren().add(new Label("  - Kg: "));
        box.getChildren().add(new TextField("0"));
        box.getChildren().stream().filter(label -> label.getClass().equals(Label.class))
                .forEach(label -> label.setId("exercise"));
        box.setPrefWidth(EXERCISE_BOX_WIDTH);
        resizeTextField(box, FIELD_WIDTH);
        return box;
    }

    private void resizeTextField(final HBox box, final int width) {
        box.getChildren().stream().filter(rep -> rep.getClass().equals(TextField.class)).map(rep -> (TextField) rep)
                .forEach(rep -> rep.setMaxWidth(width));
    }

}