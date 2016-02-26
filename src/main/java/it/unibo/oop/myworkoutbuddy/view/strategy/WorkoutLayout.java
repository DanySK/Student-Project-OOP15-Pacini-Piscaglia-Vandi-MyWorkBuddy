package it.unibo.oop.myworkoutbuddy.view.strategy;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

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

    private TableBuildStrategy tableBuild = new TableBuild();

    @Override
    public Node addWorkoutNodes(final Map<String, Map<String, List<Integer>>> workouts) {
        final VBox workout = new VBox();
        workout.setPrefWidth(EXERCISE_BOX_WIDTH);
        workouts.forEach((workName, exercises) -> {

            final ObservableList<Exercise> data = FXCollections.observableArrayList();
            exercises.forEach((exName, repetitions) -> {
                data.add(new Exercise(exName, repetitions, "0"));
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
                    new ImmutablePair<>(ex.getReps(), Integer.parseInt(ex.getKg()))));
        });
        return results;
    }

    private TableView<Exercise> tableBuild(final ObservableList<Exercise> data) {

        TableColumn<Exercise, String> exCol = tableBuild.buildColumn("Exercise Name", EXERCISE_FIELD_WIDTH,
                "exerciseName");
        TableColumn<Exercise, String> repsCol = tableBuild.buildColumn("Repetitions", 0, "");
        TableColumn<Exercise, String> kgCol = tableBuild.buildKgColumn("Kg", KG_FIELD_WIDTH, "kg");
        TableColumn<Exercise, String> rep1Col = tableBuild.buildRepColumn("Rep 1", REPS_FIELD_WIDTH, "rep1");
        TableColumn<Exercise, String> rep2Col = tableBuild.buildRepColumn("Rep 2", REPS_FIELD_WIDTH, "rep2");
        TableColumn<Exercise, String> rep3Col = tableBuild.buildRepColumn("Rep 3", REPS_FIELD_WIDTH, "rep3");
        repsCol.getColumns().addAll(Arrays.asList(rep1Col, rep2Col, rep3Col));
        return tableBuild.build(Arrays.asList(exCol, repsCol, kgCol), data);
    }

    /**
     * Inner class used to model table data.
     */
    public static final class Exercise {

        private final SimpleStringProperty exerciseName;
        private final SimpleStringProperty rep1;
        private final SimpleStringProperty rep2;
        private final SimpleStringProperty rep3;
        private final ObservableList<Integer> reps;
        private final SimpleStringProperty kg;

        private Exercise(final String exName, final List<Integer> repetitions, final String kg) {
            this.exerciseName = new SimpleStringProperty(exName);
            rep1 = new SimpleStringProperty(repetitions.get(0).toString());
            rep2 = new SimpleStringProperty(repetitions.get(1).toString());
            rep3 = new SimpleStringProperty(repetitions.get(2).toString());
            this.kg = new SimpleStringProperty(kg);
            reps = FXCollections.observableArrayList(repetitions);
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
        public String getKg() {
            return kg.get();
        }

        /**
         * 
         * @param newKg
         *            registered by user to set.
         */
        public void setKg(final String newKg) {
            kg.set(newKg);
        }

        /**
         * 
         * @return a string representation of repetition 1.
         */
        public String getRep1() {
            return rep1.get();
        }

        /**
         * 
         * @param repetition
         *            string to set.
         */
        public void setRep1(final String repetition) {
            rep1.set(repetition);
        }

        /**
         * 
         * @return a string representation of repetition 2.
         */
        public String getRep2() {
            return rep2.get();
        }

        /**
         * 
         * @param repetition
         *            string to set.
         */
        public void setRep2(final String repetition) {
            rep2.set(repetition);
        }

        /**
         * 
         * @return a string representation of repetition 3.
         */
        public String getRep3() {
            return rep3.get();
        }

        /**
         * 
         * @param repetition
         *            string to set.
         */
        public void setRep3(final String repetition) {
            rep3.set(repetition);
        }

        /**
         * 
         * @return a list of repetitions.
         */
        public List<Integer> getReps() {
            return new LinkedList<>(Arrays.asList(Integer.parseInt(getRep1()), Integer.parseInt(getRep2()),
                    Integer.parseInt(getRep3())));
        }

    }

}