package it.unibo.oop.myworkoutbuddy.model;

import java.util.Set;

import it.unibo.oop.myworkoutbuddy.model.Body.BodyPart;

/**
 * 
 *
 */
public class ExerciseImpl implements Exercise {

    private String description;
    private GymTool gymTool;
    private int settingValue;
    private int repetition;
    private int time;
    private int pause;
    private int numSession;
    /**
     * @param description String
     * @param gymTool GymTool
     * @param settingValue int 
     * @param repetition int 
     * @param time int 
     * @param numSession int 
     * @param pause int 
     */
    public ExerciseImpl(final String description, final GymTool gymTool, final int settingValue, final int repetition, final int time, final int numSession, final int pause) {
        this.setDescription(description);
        this.setGymTool(gymTool);
        this.setSettingValue(settingValue);
        this.setRepetition(repetition);
        this.setTime(time);
        this.setPause(pause);
        this.setNumSession(numSession);
    }

    private void setDescription(final String description) {
        this.description = description;
    }

    private void setGymTool(final GymTool gymTool) {
        this.gymTool = gymTool;
    }

    private void setSettingValue(final int settingValue) {
        this.settingValue = settingValue;
    }

    private void setRepetition(final int repetition) {
        this.repetition = repetition;
    }

    private void setTime(final int time) {
        this.time = time;
    }

    private void setNumSession(final int numSession) {
        this.numSession = numSession;
    }

    private void setPause(final int pause) {
        this.pause = pause;
    }
    /**
     * 
     * @return the description of Exercise
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * @return gymTool
     */
    @Override
    public GymTool getGymTool() {
        return this.gymTool;
    }

    @Override
    public int getSettingValue() {
        return settingValue;
    }

    @Override
    public int getRepetition() {
        return this.repetition;
    }

    @Override
    public int getTime() {
        return this.time;
    }

    @Override
    public int getNumSession() {
        return this.numSession;
    }

    @Override
    public int getPause() {
        return this.pause;
    }
    /**
     * @return the set of body parts
     */
    @Override
    public Set<BodyPart> getBodyParts() {
        return this.gymTool.getBodyMap().keySet();
    }
}
