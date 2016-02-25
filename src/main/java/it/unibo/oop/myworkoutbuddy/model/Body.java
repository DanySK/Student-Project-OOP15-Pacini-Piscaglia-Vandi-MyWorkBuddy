package it.unibo.oop.myworkoutbuddy.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 Class Body to manage a human body.
 -------------------------------------------------------------
*/
public class Body {
    private Map<String, Set<String>> bodyMap;
    private Set<String> measureSet;

    /**
     * 
     */
    public Body() {
        this.bodyMap = new HashMap<>();
        this.measureSet = new HashSet<>();
    }

    /**
     * give a part zone of human body in optional form.
     * @param muscle String
     * @return a Optional<String>
     */
    public Optional<String> getPartZone(final String muscle) {
        if (this.getBodyMap().isEmpty()) {
            return Optional.empty();
        }
        return getBodyMap().keySet().stream().filter(i -> getBodyMap().get(i).contains(muscle)).findAny();
    }

    /**
     * give an optional name of measure.
     * @param measure String
     * @return a Optional<String>
     */
    public Optional<String> getMeasure(final String measure) {
        if (this.getMeasureSet().isEmpty()) {
            return Optional.empty();
        }
        return this.getMeasureSet().stream().filter(i -> i.equals(measure)).findAny();
    }

    /**
     * give a map of partZones and relatives muscles.
     * @return a Map<String, Set<String>>
     */
    public Map<String, Set<String>> getBodyMap() {
        return this.bodyMap;
    }

    /**
     * give the set of measurable body parts.
     * @return a Set<String>
     */
    public Set<String> getMeasureSet() {
        return this.measureSet;
    }

    /**
     * add a new measure in set measure.
     * @param measure String
     */
    public void addMeasureData(final String measure) {
        this.measureSet.add(measure);
    }

    /**
     * add a bodyPart to the set of a bodyZone.
     * @param bodyZone String
     * @param bodyPart String
     */
    public void addMap(final String bodyZone, final String bodyPart) {
        final Set<String> setToadd = new HashSet<>();
        setToadd.add(bodyPart);
        bodyMap.merge(bodyZone, setToadd, (s1, s2) -> {
            setToadd.addAll(bodyMap.get(bodyZone));
            return setToadd;
        });
    }

    /**
     * remove a key of bodyMap.
     * @param partZone String
     * @throws ElementNotFoundException 
     */
    public void removingMapZone(final String partZone) {
        final Set<String> setKey = bodyMap.keySet();
        setKey.remove(partZone);
    }

    /**
     * remove an element of values of bodyMap.
     * @param bodyPart String
     * @throws ElementNotFoundException 
     */
    public void removingMapBodyPart(final String bodyPart) {
        this.bodyMap.keySet().forEach(i -> {
            if (this.bodyMap.get(i).contains(bodyPart)) {
                this.bodyMap.get(i).remove(bodyPart);
            }
        });
    }

    /**
     * remove an element of values of measureSet.
     * @param bodyMeasure String
     */
    public void removeBodyMeasure(final String bodyMeasure) {
        this.measureSet.remove(bodyMeasure);
    }
}