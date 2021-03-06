package it.unibo.oop.myworkoutbuddy.model;

import java.util.Set;
/**
 Class Exercise to personalized a single exercise

     description : the exercise description
     gymTool : the exercise tool used
     settingValue : value of setting tool
     repetition : number of repetition of exercise
     time : duration time of a single repetition
     pause : pause time between two consecutive repetitions
     numSession : number of cycles (cycle = repetition + pause).
*/
public final class ExerciseImpl implements Exercise {

    private final String description;
    private final GymTool gymTool;
    private int repetition;
    private int time;
    private int sessions;

    /*
     * to make private x use of builder
     */
    /**
     * @param description String
     * @param gymTool GymTool
     * @param time int 
     * @param numRepetitons integer
     * @param numSession int 
     */
    private ExerciseImpl(final String description, final GymTool gymTool, final int repetition, final int time, final int sessions) {
        this.description = description;
        this.gymTool = gymTool;
        this.repetition = repetition;
        this.sessions = sessions;
        this.time = time;
    }

    /**
     * class x builder method.
     *
     */
    public static class Builder {
        private String description;
        private GymTool gymTool;
        private int repetition;
        private int sessions;
        private int time;

        /**
         * add a description to an Exercise.
         * @param description String
         * @return a builder
         */
        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        /**
         * add a GymTool to an Exercise.
         * @param gymTool GymTool
         * @return a builder
         */
        public Builder gymTool(final GymTool gymTool) {
            this.gymTool = gymTool;
            return this;
        }

        /**
         * add a repetition to an Exercise.
         * @param repetition integer
         * @return a builder
         */
        public Builder repetition(final int repetition) {
            this.repetition = repetition;
            return this;
        }

        /**
         * add a time to an Exercise.
         * @param time integer
         * @return a builder
         */
        public Builder time(final int time) {
            this.time = time;
            return this;
        }

        /**
         * add a numSession to an Exercise.
         * @param sessions integer
         * @return a builder
         */
        public Builder sessions(final int sessions) {
            this.sessions = sessions;
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
         */
        public ExerciseImpl build() {
            this.checkNotNull(this.description);
            this.checkNotNull(this.gymTool);
            this.checkNotNegative(this.sessions);
            this.checkNotNegative(this.repetition);
            this.checkNotNegative(this.time);

            return new ExerciseImpl(this.description, this.gymTool, this.repetition, this.time, this.sessions);
        }
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public GymTool getGymTool() {
        return this.gymTool;
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
    public int getSessions() {
        return this.sessions;
    }

    @Override
    public Set<String> getBodyParts() {
        return this.gymTool.getBodyMap().keySet();
    }

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
                + "\n gymTool = " + this.getGymTool().getCode()
                + ", repetition = " + this.getRepetition() + ", time = " + this.getTime() + ", numSession = " + this.getSessions()
                + "]";
    }
}
