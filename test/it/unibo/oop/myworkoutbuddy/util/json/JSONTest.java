package it.unibo.oop.myworkoutbuddy.util.json;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class JSONTest {

    class MapBuilder<K, V> {

        @SuppressWarnings("rawtypes")
        private final Class<? extends Map> clazz;

        private Map<K, V> m;

        @SuppressWarnings("rawtypes")
        MapBuilder(final Class<? extends Map> clazz) {
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
        final Map<String, Object> people = new HashMap<>();
        people.put("String", "s");
        people.put("JSONArray", new JSONArray(1, 2, 3, 4, 5));
        people.put("byte", new Byte((byte) 50));
        people.put("char", new Character('A'));
        JSONObject json = new JSONObject(people);
        System.out.println(json);
        System.out.println(json.getJSONArray("JSONArray").isPresent());
        System.out.println(json.getString("String").isPresent());
        System.out.println(json.getByte("byte").isPresent());
        System.out.println(json.getCharacter("char").isPresent());
    }

}
