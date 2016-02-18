package it.unibo.oop.myworkoutbuddy.view.handlers;

import javafx.scene.layout.VBox;

/**
 * Models a background thread created to handle the exit animation in the
 * menu bar.
 */
public class MenuBarAgent extends Thread {

    private static final double HIDE_MENU_DELTA_WIDTH = 100.0;

    private static final int EXIT_MENU_TIME_DURATION = 2000;

    private final VBox btnContainer;

    /**
     * 
     * @param container
     *            menu bar to move.
     */
    public MenuBarAgent(final VBox container) {
        btnContainer = container;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(EXIT_MENU_TIME_DURATION);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        btnContainer.setTranslateX(-HIDE_MENU_DELTA_WIDTH);
    }
}
