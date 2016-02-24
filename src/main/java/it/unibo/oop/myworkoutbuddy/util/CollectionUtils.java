package it.unibo.oop.myworkoutbuddy.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CollectionUtils {

    public static <E> List<E> copy(final List<? extends E> l) {
        return new ArrayList<>(l);
    }

    public static <E> Set<E> copy(final Set<? extends E> s) {
        return new HashSet<>(s);
    }

    public static <K, V> Map<K, V> copy(final Map<? extends K, ? extends V> m) {
        return new HashMap<>(m);
    }

    private CollectionUtils() {
    }

}
