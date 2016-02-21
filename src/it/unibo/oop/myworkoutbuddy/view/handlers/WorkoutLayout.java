package it.unibo.oop.myworkoutbuddy.view.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import it.unibo.oop.myworkoutbuddy.util.MutablePair;
import it.unibo.oop.myworkoutbuddy.util.Pair;
import it.unibo.oop.myworkoutbuddy.util.UnmodifiablePair;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Implementation of the building strategy of the GUI routine.
 * It also provides a method to encapsulate the strategy to get exercises
 * results.
 *
 */
public class WorkoutLayout implements WorkoutLayoutStrategy {

    private static final int FIELD_WIDTH = 45;

    @Override
    public Node addWorkoutNodes(final Map<String, Map<String, List<Integer>>> workouts) {
        final VBox workout = new VBox();
        workouts.forEach((workName, exercises) -> {

            final VBox exercisesList = new VBox();
            exercises.forEach((exName, repetitions) -> {

                final HBox box = new HBox();
                exerciseBoxBuild(box, exName, repetitions);
                exercisesList.getChildren().add(box);
            });
            workout.getChildren().add(new TitledPane(workName, exercisesList));
        });
        return workout;
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
        final Pair<List<Integer>, Integer> repKg = new MutablePair<>(reps,
                Integer.valueOf(kgField.getText()));
        return new UnmodifiablePair<String, Pair<List<Integer>, Integer>>(exName.getText(), repKg);
    }

    private void exerciseBoxBuild(final HBox box, final String exName, final List<Integer> repetitions) {
        box.getChildren().add(new Label(exName));
        box.getChildren().add(new Label("- Repetitions: "));
        repetitions.forEach(rep -> box.getChildren().add(new TextField(rep.toString())));
        box.getChildren().add(new Label("  - Kg: "));
        box.getChildren().add(new TextField("0"));
        box.getChildren().stream()
                .filter(label -> label.getClass().equals(Label.class))
                .forEach(label -> label.setId("exercise"));
        resizeTextField(box, FIELD_WIDTH);
    }

    private void resizeTextField(final HBox box, final int width) {
        box.getChildren().stream()
                .filter(rep -> rep.getClass().equals(TextField.class))
                .map(rep -> (TextField) rep)
                .forEach(rep -> rep.setMaxWidth(width));
    }

}