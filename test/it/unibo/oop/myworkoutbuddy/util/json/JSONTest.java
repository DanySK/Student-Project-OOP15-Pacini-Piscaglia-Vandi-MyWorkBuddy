package it.unibo.oop.myworkoutbuddy.util.json;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import it.unibo.oop.myworkoutbuddy.controller.db.Person;

public class JSONTest {

    private class MapBuilder<K, V> {

        private final Class<? extends Map<? extends K, ? extends V>> clazz;

        private Map<K, V> m;

        MapBuilder(final Class<? extends Map<? extends K, ? extends V>> clazz) {
            this.clazz = clazz;
            m = new HashMap<>();
        }

        public MapBuilder<K, V> put(final K key, final V value) {
            m.put(key, value);
            return this;
        }

        public Map<K, V> build() {
            return newMap(m);
        }

        @SuppressWarnings("unchecked")
        private Map<K, V> newMap(final Map<? extends K, ? extends V> m) {
            try {
                return (Map<K, V>) clazz.getConstructor(Map.class).newInstance(m);
            } catch (final Exception e) {
                // Exceptions should not be thrown
                return null;
            }
        }

    }

    @Test
    public void test() {
        final JSONObject json = new JSONObject(new Person.Builder()
                .age(20)
                .firstName("Mattia")
                .lastName("Vandi")
                .build());
        System.out.println(json);
        System.out.println(json.getString("firstName").get() + " " + json.getString("lastName").get());
    }

}
