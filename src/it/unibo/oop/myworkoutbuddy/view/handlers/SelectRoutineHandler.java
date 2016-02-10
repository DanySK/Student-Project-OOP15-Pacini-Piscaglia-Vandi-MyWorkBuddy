package it.unibo.oop.myworkoutbuddy.view.handlers;

import it.unibo.oop.myworkoutbuddy.view.SelectRoutineView;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;

/**
 * 
 * Handler of the SelectRoutineView. It handles the events captured by the GUI
 * selecting the chosen routine.
 */
public class SelectRoutineHandler implements SelectRoutineView {

    @FXML
    private HBox exerciseBox;

    @FXML
    private TabPane tabRoutine;

    
    @Override
    public int getRoutineIndex() {
        return 0;
    }

}
