package it.unibo.oop.myworkoutbuddy.controller;

import it.unibo.oop.myworkoutbuddy.model.MyWorkoutBuddyModel;

public class ControllerImpl implements Controller {

    private final MyWorkoutBuddyModel model;

    public ControllerImpl(final MyWorkoutBuddyModel model) {
        this.model = model;
    }

}
