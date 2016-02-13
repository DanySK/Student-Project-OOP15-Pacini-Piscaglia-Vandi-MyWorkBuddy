package it.unibo.oop.myworkoutbuddy.view.handlers;

import java.util.List;
import java.util.Map;

import it.unibo.oop.myworkoutbuddy.util.Triple;
import it.unibo.oop.myworkoutbuddy.view.CreateRoutineView;
import it.unibo.oop.myworkoutbuddy.view.ViewsObserver;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * 
 * Handler of the CreateRoutineView. It collects user routine.
 */
public final class CreateRoutineHandler implements CreateRoutineView {

    @FXML
    private Button btnInsertExercise;

    @FXML
    private AnchorPane anchorEx;

    @FXML
    private Button btnSaveRoutine;

    private ViewsObserver observer = ViewsHandler.getObserver();

    @Override
    public Triple<Integer, String, Map<String, Map<String, List<Integer>>>> getRoutine() {
        return null;
    }

}
