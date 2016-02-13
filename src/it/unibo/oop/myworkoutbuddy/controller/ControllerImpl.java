package it.unibo.oop.myworkoutbuddy.controller;

import it.unibo.oop.myworkoutbuddy.model.MyWorkoutBuddyModel;
import it.unibo.oop.myworkoutbuddy.view.AppViews;

public class ControllerImpl implements Controller {

    private final MyWorkoutBuddyModel model;
    private final AppViews view;

    public ControllerImpl(final MyWorkoutBuddyModel model, final AppViews view) {
        this.model = model;
        this.view = view;
    }

}
