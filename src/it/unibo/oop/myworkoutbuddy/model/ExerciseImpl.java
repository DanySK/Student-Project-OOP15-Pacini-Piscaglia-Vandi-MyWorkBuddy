package it.unibo.oop.myworkoutbuddy.model;

import java.util.Set;

import it.unibo.oop.myworkoutbuddy.model.Body.BodyPart;

/**
 * 
 *
 */
public final class ExerciseImpl implements Exercise {

    private String description;
    private GymTool gymTool;
    private int settingValue;
    private int repetition;
    private int time;
    private int pause;
    private int numSession;

    /*
     * to make private x use of builder
     */
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
        this.description = description;
        this.gymTool = gymTool;
        this.settingValue = settingValue;
        this.repetition = repetition;
        this.time = time;
        this.pause = pause;
        this.numSession = numSession;
    }

    /**
     * 
     *
     */
    public static class Builder {
        private String description;
        private GymTool gymTool;
        private int settingValue;
        private int repetition;
        private int time;
        private int pause;
        private int numSession;

        /**
         * 
         * @param description String
         * @return a builder
         */
        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        /**
         * 
         * @param gymTool GymTool
         * @return a builder
         */
        public Builder gymTool(final GymTool gymTool) {
            this.gymTool = gymTool;
            return this;
        }

        /**
         * 
         * @param settingValue integer
         * @return a builder
         */
        public Builder settingValue(final int settingValue) {
            this.settingValue = settingValue;
            return this;
        }

        /**
         * 
         * @param repetition integer
         * @return a builder
         */
        public Builder repetition(final int repetition) {
            this.repetition = repetition;
            return this;
        }

        /**
         * 
         * @param time integer
         * @return a builder
         */
        public Builder time(final int time) {
            this.time = time;
            return this;
        }

        /**
         * 
         * @param pause integer
         * @return a builder
         */
        public Builder pause(final int pause) {
            this.pause = pause;
            return this;
        }

        /**
         * 
         * @param numSession integer
         * @return a builder
         */
        public Builder numSession(final int numSession) {
            this.numSession = numSession;
            return this;
        }

        private void checkNotNull(final Object object) throws NullPointerException {
            if (object == null) {
                throw new NullPointerException();
            }
        }

        private void checkNotNegative(final int num) throws IllegalStateException {
            if (num < 0) {
                throw new IllegalStateException();
            }
        }

        /**
         * 
         * @return Builder
         * @throws IllegalStateException exception
         */
        public ExerciseImpl build() throws IllegalStateException {
            this.checkNotNull(this.description);
            this.checkNotNull(this.gymTool);
            this.checkNotNegative(this.numSession);
            this.checkNotNegative(this.pause);
            this.checkNotNegative(this.repetition);
            this.checkNotNegative(this.settingValue);
            this.checkNotNegative(this.time);

            return new ExerciseImpl(this.description, this.gymTool, this.numSession, this.pause, this.repetition, this.settingValue, this.time);
        }
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

    /**
     * 
     * @param score Double
     * @return calculate the normalized score for an exercise
     */
    @Override
    public Double getNormalizedScore(final Double score) {
        final int max = this.getGymTool().getMaxValue();
        final int min = this.getGymTool().getMinValue();
        final double delta = (max - min);
        return (double) ((score - min) / delta);
    }

    @Override
    public String toString() {
        return "\n\n ExerciseImpl " 
                + " [description = " + this.getDescription() 
                + "\n gymTool = " + this.getGymTool().getCode() + ", settingValue = " + this.getSettingValue()
                + ", repetition = " + this.getRepetition() + ", time = " + this.getTime() + ", pause = " + this.getPause() + ", numSession = " + this.getNumSession()
                + "]";
    }
}
