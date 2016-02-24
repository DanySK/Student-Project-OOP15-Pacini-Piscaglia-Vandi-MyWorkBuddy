package it.unibo.oop.myworkoutbuddy.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CollectionUtils {

    /**
     * Returns a copy of the source list copying all it's elements into another list.
     * 
     * @param src
     *            the source list
     * 
     * @return a copy of the source list
     */
    public static <E> List<E> copy(final List<? extends E> src) {
        return new ArrayList<>(src);
    }

    /**
     * Returns a copy of the source set copying all it's elements into another set.
     * 
     * @param src
     *            the source set
     * 
     * @return a copy of the source set
     */
    public static <E> Set<E> copy(final Set<? extends E> src) {
        return new HashSet<>(src);
    }

    /**
     * Returns a copy of the source map copying all it's elements into another map.
     * 
     * @param src
     *            the source map
     * 
     * @return a copy of the source map
     */
    public static <K, V> Map<K, V> copy(final Map<? extends K, ? extends V> src) {
        return new HashMap<>(src);
    }

    private CollectionUtils() {
    }

}
