package it.unibo.oop.myworkoutbuddy.view.handlers;

import it.unibo.oop.myworkoutbuddy.view.SelectRoutineView;
import it.unibo.oop.myworkoutbuddy.view.ViewsObserver;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;

/**
 * 
 * Handler of the SelectRoutineView. It handles the events captured by the GUI
 * selecting the chosen routine.
 */
public final class SelectRoutineHandler implements SelectRoutineView {

    @FXML
    private HBox exerciseBox;

    @FXML
    private TabPane tabRoutine;

    private ViewsObserver observer = ViewsHandler.getObserver();
    
    @Override
    public int getRoutineIndex() {
        return 0;
    }

}
