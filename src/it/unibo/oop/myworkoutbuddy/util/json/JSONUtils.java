package it.unibo.oop.myworkoutbuddy.util.json;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public final class JSONUtils {

    public static JSONObject unmodifiableJSONObject(final JSONObject o) {
        return new UnmodifiableJSONObject(o);
    }

    public static JSONArray unmodifiableJSONArray(final JSONArray a) {
        return new UnmodifiableJSONArray(a);
    }

    private static final class UnmodifiableJSONArray extends JSONArray {

        private static final long serialVersionUID = 4707520406742461953L;

        /**
         * Constructs a UnmodifiableJSONArray from a JSONArray.
         *
         * @param a
         *            the JSONArray used as source, must be not {@link null}
         */
        UnmodifiableJSONArray(final JSONArray a) {
            super(a);
        }

        @Override
        public boolean add(final Object e) {
            throw new UnsupportedOperationException("add");
        }

        @Override
        public void add(final int index, final Object element) {
            throw new UnsupportedOperationException("add");
        }

        @Override
        public boolean addAll(final Collection<?> c) {
            throw new UnsupportedOperationException("add");
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
        }

        @Override
        public Object remove(final int index) {
            throw new UnsupportedOperationException("remove");
        }

        @Override
        public boolean remove(final Object o) {
            throw new UnsupportedOperationException("remove");
        }

        @Override
        public boolean removeAll(final Collection<?> c) {
            throw new UnsupportedOperationException("removeAll");
        }

        @Override
        public boolean removeIf(final Predicate<? super Object> filter) {
            throw new UnsupportedOperationException("removeIf");
        }

        @Override
        public void replaceAll(final UnaryOperator<Object> operator) {
            throw new UnsupportedOperationException("replaceAll");
        }

        @Override
        public boolean retainAll(final Collection<?> c) {
            throw new UnsupportedOperationException("retainAll");
        }

        @Override
        public Object set(final int index, final Object element) {
            throw new UnsupportedOperationException("set");
        }

    }

    private static final class UnmodifiableJSONObject extends JSONObject {

        private static final long serialVersionUID = 7935239133966066650L;

        /**
         * Constructs a UnmodifiableJSONObject from a JSONObject.
         *
         * @param o
         *            the JSONObject used as source, must be not {@link null}
         */
        UnmodifiableJSONObject(final JSONObject o) {
            super(o);
        }

        @Override
        public Object compute(final String key, final BiFunction<? super String, ? super Object, ?> remappingFunction) {
            throw new UnsupportedOperationException("compute");
        }

        @Override
        public Object computeIfAbsent(final String key, final Function<? super String, ?> mappingFunction) {
            throw new UnsupportedOperationException("computeIfAbsent");
        }

        @Override
        public Object computeIfPresent(
                final String key,
                final BiFunction<? super String, ? super Object, ?> remappingFunction) {
            throw new UnsupportedOperationException("computeIfPresent");
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
        }

        @Override
        public Object merge(
                final String key,
                final Object value,
                final BiFunction<? super Object, ? super Object, ?> remappingFunction) {
            throw new UnsupportedOperationException("merge");
        }

        @Override
        public Object put(final String key, final Object value) {
            throw new UnsupportedOperationException("put");
        }

        @Override
        public void putAll(final Map<? extends String, ?> m) {
            throw new UnsupportedOperationException("putAll");
        }

        @Override
        public Object putIfAbsent(final String key, final Object value) {
            throw new UnsupportedOperationException("putIfAbsent");
        }

        @Override
        public Object remove(final Object key) {
            throw new UnsupportedOperationException("remove");
        }

        @Override
        public boolean remove(final Object key, final Object value) {
            throw new UnsupportedOperationException("remove");
        }

        @Override
        public boolean replace(final String key, final Object oldValue, final Object newValue) {
            throw new UnsupportedOperationException("replace");
        }

        @Override
        public Object replace(final String key, final Object value) {
            throw new UnsupportedOperationException("replace");
        }

        @Override
        public void replaceAll(final BiFunction<? super String, ? super Object, ?> function) {
            throw new UnsupportedOperationException("replaceAll");
        }

    }

    private JSONUtils() {
        throw new IllegalAccessError("No instances of " + getClass().getName());
    }

}
