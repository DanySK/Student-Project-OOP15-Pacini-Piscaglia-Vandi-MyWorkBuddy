package it.unibo.oop.myworkoutbuddy.model;

import java.util.Set;

public interface Exercise {

    /**
     * 
     * @return
     */
    String getDescription();

    /**
     * 
     * @return
     */
    Set<BodyPart> getBodyParts();

}
