package it.unibo.oop.myworkoutbuddy.view.strategy;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

/**
 * Implementation of the building strategy of the GUI routine. It also provides
 * a method to encapsulate the strategy to get exercises results.
 *
 */
public final class WorkoutLayout implements WorkoutLayoutStrategy {

    private static final double EXERCISE_BOX_WIDTH = 638;

    private static final double EXERCISE_FIELD_WIDTH = 200;

    private static final double KG_FIELD_WIDTH = 136;

    private static final double REPS_FIELD_WIDTH = 100;

    private final TableBuildStrategy tableBuildStrategy = new TableBuild();

    @Override
    public Node addWorkoutNodes(final Map<String, Map<String, List<Integer>>> workouts) {
        final VBox workout = new VBox();
        workout.setPrefWidth(EXERCISE_BOX_WIDTH);
        workouts.forEach((workName, exercises) -> {

            final ObservableList<Exercise> data = FXCollections.observableArrayList();
            exercises.forEach((exName, repetitions) -> {
                data.add(new Exercise(exName, repetitions, 0));
            });
            workout.getChildren().add(new TitledPane(workName, tableBuild(data)));
        });
        return new ScrollPane(workout);
    }

    @Override
    public List<Pair<String, Pair<List<Integer>, Integer>>> getExerciseResults(final Node workout) {
        final List<Pair<String, Pair<List<Integer>, Integer>>> results = new LinkedList<>();
        @SuppressWarnings("unchecked")
        final TableView<Exercise> table = (TableView<Exercise>) workout;
        table.getItems().forEach(ex -> {
                results.add(new ImmutablePair<String, Pair<List<Integer>, Integer>>(ex.getExerciseName(),
                        new ImmutablePair<>(ex.getReps(), ex.getKg())));
        });
        return results;
    }

    private TableView<Exercise> tableBuild(final ObservableList<Exercise> data) {

        final TableColumn<Exercise, String> exCol = tableBuildStrategy.buildColumn("Exercise Name",
                EXERCISE_FIELD_WIDTH,
                "exerciseName");
        final TableColumn<Exercise, String> repsCol = tableBuildStrategy.buildColumn("Repetitions", 0, "");
        final TableColumn<Exercise, String> kgCol = tableBuildStrategy.buildKgColumn("Kg", KG_FIELD_WIDTH, "kg");
        final TableColumn<Exercise, String> rep1Col = tableBuildStrategy.buildRepColumn("Rep 1", REPS_FIELD_WIDTH,
                "rep1");
        final TableColumn<Exercise, String> rep2Col = tableBuildStrategy.buildRepColumn("Rep 2", REPS_FIELD_WIDTH,
                "rep2");
        final TableColumn<Exercise, String> rep3Col = tableBuildStrategy.buildRepColumn("Rep 3", REPS_FIELD_WIDTH,
                "rep3");
        repsCol.getColumns().addAll(Arrays.asList(rep1Col, rep2Col, rep3Col));
        return tableBuildStrategy.build(Arrays.asList(exCol, repsCol, kgCol), data);
    }

    /**
     * Inner class used to model table data.
     */
    public static final class Exercise {

        private final SimpleStringProperty exerciseName;
        private final SimpleIntegerProperty rep1;
        private final SimpleIntegerProperty rep2;
        private final SimpleIntegerProperty rep3;
        private final SimpleIntegerProperty kg;

        private Exercise(final String exName, final List<Integer> repetitions, final int kg) {
            this.exerciseName = new SimpleStringProperty(exName);
            rep1 = new SimpleIntegerProperty(repetitions.get(0));
            rep2 = new SimpleIntegerProperty(repetitions.get(1));
            rep3 = new SimpleIntegerProperty(repetitions.get(2));
            this.kg = new SimpleIntegerProperty(kg);
        }

        /**
         * 
         * @return exercise name.
         */
        public String getExerciseName() {
            return exerciseName.get();
        }

        /**
         * 
         * @param exName
         *            to set.
         */
        public void setExerciseName(final String exName) {
            exerciseName.set(exName);
        }

        /**
         * 
         * @return a string representation of Kg.
         */
        public int getKg() {
            return kg.get();
        }

        /**
         * 
         * @param newKg
         *            registered by user to set.
         */
        public void setKg(final int newKg) {
            kg.set(newKg);
        }

        /**
         * 
         * @return a string representation of repetition 1.
         */
        public int getRep1() {
            return rep1.get();
        }

        /**
         * 
         * @param repetition
         *            string to set.
         */
        public void setRep1(final int repetition) {
            rep1.set(repetition);
        }

        /**
         * 
         * @return a string representation of repetition 2.
         */
        public int getRep2() {
            return rep2.get();
        }

        /**
         * 
         * @param repetition
         *            string to set.
         */
        public void setRep2(final int repetition) {
            rep2.set(repetition);
        }

        /**
         * 
         * @return a string representation of repetition 3.
         */
        public int getRep3() {
            return rep3.get();
        }

        /**
         * 
         * @param repetition
         *            string to set.
         */
        public void setRep3(final int repetition) {
            rep3.set(repetition);
        }

        /**
         * 
         * @return a list of repetitions.
         */
        public List<Integer> getReps() {
            return new LinkedList<>(Arrays.asList(getRep1(), getRep2(), getRep3()));
        }

    }

}