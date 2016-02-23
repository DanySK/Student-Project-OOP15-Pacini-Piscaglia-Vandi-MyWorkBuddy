package it.unibo.oop.myworkoutbuddy.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 Class Body to manage human body

     Body contains body data :
     BodyZone    : enum of body zone for mapping;
     BodyPart    : enum of body parts/muscles;
     BodyMeasure : enum of body parts for measurement data;
     BodyTarget  : enum of aims for each single training;
     BodyData : class for body parts measurement.
*/
public final class Body {
    private Map<String, Set<String>> bodyMap;
    private Set<String> measureSet;

    private void initBodyMap() {
        bodyMap = new HashMap<>();
    }

    private void initMeasureSet() {
        measureSet = new HashSet<>();
    }

    /**
     * @return a map of partZones and relatives muscles
     */
    public Map<String, Set<String>> getBodyMap() {
        return this.bodyMap;
    }

    /**
     * @return a map of partZones and relatives muscles
     */
    public Set<String> getMeasureSet() {
        return this.measureSet;
    }

    /**
     * add a new measure in set measure.
     * @param measure String
     */
    public void addMeasureData(final String measure) {
        if (this.getMeasureSet() == null) {
            this.initMeasureSet();
        }

        this.measureSet.add(measure);
    }

    /**
     * @param bodyZone String
     * @param bodyPart String
     */
    public void addMap(final String bodyZone, final String bodyPart) {
        if (getBodyMap() == null) {
            initBodyMap();
        }
        /*
        final Set<String> retSet = bodyMap.get(bodyZone);
        final Set<String> parts = new HashSet<>();
        if (retSet != null) {
            parts.addAll(retSet);
        }
        parts.add(bodyPart);
        bodyMap.put(bodyZone, parts);
        */
        final Set<String> setToadd = new HashSet<>();
        setToadd.add(bodyPart);
        bodyMap.merge(bodyZone, setToadd, (s1, s2) -> {
            setToadd.addAll(bodyMap.get(bodyZone));
            return setToadd;
        });
    }

    /**
     * @param partZone String
     * @throws ElementNotFoundException 
     */
    public void removingMapZone(final String partZone) throws ElementNotFoundException {
        final Set<String> setKey = bodyMap.keySet();
        checkBodyPart(setKey, partZone);
        setKey.remove(partZone);
    }

    /**
     * @param bodyPart String
     * @throws ElementNotFoundException 
     */
    public void removingMapBodyPart(final String bodyPart) throws ElementNotFoundException {
        this.bodyMap.keySet().forEach(i -> {
            if (this.bodyMap.get(i).contains(bodyPart)) {
                this.bodyMap.get(i).remove(bodyPart);
            }
        });
    }

    /**
     * 
     * @param bodyMeasure String
     */
    public void removeBodyMeasure(final String bodyMeasure) {
        this.measureSet.remove(bodyMeasure);
    }

    /**
     * @param muscle String
     * @return partZone Optional<String>
     */
    public Optional<String> getPartZone(final String muscle) {
        if (this.getBodyMap() == null) {
            return Optional.empty();
        }
        return getBodyMap().keySet().stream().filter(i -> getBodyMap().get(i).contains(muscle)).findAny();
    }

    /**
     * 
     * @param measure String
     * @return an optional name of measure
     */
    public Optional<String> getMeasure(final String measure) {
        if (this.getMeasureSet() == null) {
            return Optional.empty();
        }
        return this.getMeasureSet().stream().filter(i -> i.equals(measure)).findAny();
    }

    private <X> void checkBodyPart(final Collection<X> collection, final X bodyPart) throws ElementNotFoundException {
        final Optional<X> optValue = collection.stream().filter(i -> i.equals(bodyPart)).findAny();

        if (!optValue.isPresent()) {
            throw new ElementNotFoundException();
        }
    }

    /**
     * an exception for an element not found in a collection.
     */
    public static final class ElementNotFoundException extends Exception {

        /**
         * 
         */
        private static final long serialVersionUID = 8629645694649187830L;

        /**
         * builder of the class.
         */
        public ElementNotFoundException() {

        }
    }
}