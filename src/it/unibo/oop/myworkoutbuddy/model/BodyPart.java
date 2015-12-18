package it.unibo.oop.myworkoutbuddy.model;

public enum BodyPart {

    CHEST, BACK, BICEPS, TRICEPS, QUADRICEPS, HAMSTRINGS, CALVES, FOREARMS, SHOULDERS, TRAPS;

    @Override
    public String toString() {
        return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
    }

}
