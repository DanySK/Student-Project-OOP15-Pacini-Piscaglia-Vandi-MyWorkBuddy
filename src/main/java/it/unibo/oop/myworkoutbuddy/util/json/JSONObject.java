package it.unibo.oop.myworkoutbuddy.util.json;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ClassUtils;

/**
 * A JSONObject is an unordered collection of key/value pairs. Its external form is a string wrapped in curly braces
 * with colons between the names and values, and commas between the values and names. The internal form is an object
 * having <code>get</code> methods for accessing the values by key, and <code>put</code> methods for adding or replacing
 * values by key. The values can be any of these types: <code>Boolean</code>, <code>JSONArray</code>,
 * <code>JSONObject</code>, <code>Number</code>, <code>String</code>, or the <code>JSONObject.NULL</code> object. A
 * JSONObject constructor can be used to convert an external form JSON text into an internal form whose values can be
 * retrieved with the <code>get</code> methods, or to convert values into a JSON text using the <code>put</code> and
 * <code>toString</code> methods. A <code>get</code> method returns a value if one can be found, and throws an exception
 * if one cannot be found.
 * <p>
 * The generic <code>get</code> methods return an object, which you can cast or query for type. There are also typed
 * <code>get</code> methods that do type checking and type coercion for you. If the type cast is possiole they will
 * return an {@link Optional#of(T)}, an {@link Optional#empty()} is the cast is not possible.
 * <p>
 * The <code>put</code> methods add or replace values in an object. For example,
 *
 * <pre>
 * myString = new JSONObject().put(&quot;JSON&quot;, &quot;Hello, World!&quot;).toString();
 * </pre>
 *
 * produces the string <code>{"JSON": "Hello, World"}</code>.
 * <p>
 * The texts produced by the <code>toString</code> methods strictly conform to the JSON syntax rules. The constructors
 * are more forgiving in the texts they will accept:
 * <ul>
 * <li>An extra <code>,</code>&nbsp;<small>(comma)</small> may appear just before the closing brace.</li>
 * <li>Strings will be quoted with <code>"</code>&nbsp;<small>(double quote)</small>.
 * </ul>
 */
public class JSONObject implements Map<String, Object>, JSONValue {

    private static final long serialVersionUID = 8132214257960166022L;

    // Null instance of a JSONObject
    private static final Object NULL = new Object() {

        @Override
        protected Object clone() {
            return this;
        }

        @Override
        public boolean equals(final Object object) {
            return object == null || object == this;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public String toString() {
            return "null";
        }

    };

    private final Map<String, Object> map;

    /**
     * Wraps an object, if necessary. If the object is {@code null}, return the NULL object. If it is an array or
     * collection, wrap it in a JSONArray. If it is a map, wrap it in a JSONObject. If it is an Optional tries to get
     * its value. If it is a standard property (Double, String, etc...) then it is already wrapped.
     * If it doesn't, try to wrap it in a JSONObject. If the wrapping fails, then {@code null} is returned.
     *
     * @param o
     *            the object to wrap
     * @return The wrapped value
     */
    @SuppressWarnings("unchecked")
    public static Object wrap(final Object o) {
        try {
            if (NULL.equals(o)) {
                return NULL;
            }
            if (o instanceof Boolean || o instanceof Byte || o instanceof Short
                    || o instanceof Integer || o instanceof Long || o instanceof BigInteger
                    || o instanceof Float || o instanceof Double || o instanceof BigDecimal
                    || o instanceof Character || o instanceof JSONValue) {
                return o;
            }
            if (o instanceof String) {
                final String str = (String) o;
                return tryParse(str);
            }
            if (o.getClass().isArray()) {
                final Object[] arr = (Object[]) o;
                return new JSONArray(Arrays.asList(arr));
            }
            if (o instanceof Collection) {
                final Collection<?> coll = (Collection<?>) o;
                return new JSONArray(coll);
            }
            if (o instanceof Map) {
                final Map<String, ?> map = (Map<String, ?>) o;
                return new JSONObject(map);
            }
            if (o instanceof Optional) {
                final Optional<?> opt = (Optional<?>) o;
                return opt.get();
            }
            if (o instanceof OptionalDouble) {
                final OptionalDouble opt = (OptionalDouble) o;
                return opt.getAsDouble();
            }
            if (o instanceof OptionalInt) {
                final OptionalInt opt = (OptionalInt) o;
                return opt.getAsInt();
            }
            if (o instanceof OptionalLong) {
                final OptionalLong opt = (OptionalLong) o;
                return opt.getAsLong();
            }
            final Package objectPackage = o.getClass().getPackage();
            final String objectPackageName = objectPackage != null ? objectPackage.getName() : "";
            if (objectPackageName.startsWith("java.")
                    || objectPackageName.startsWith("javax.")
                    || o.getClass().getClassLoader() == null) {
                return o.toString();
            }
            return new JSONObject(o);
        } catch (final Exception exception) {
            return null;
        }
    }

    /**
     * Constructs an empty JSONObject.
     */
    public JSONObject() {
        map = new HashMap<>();
    }

    /**
     * Constructs a JSONObject from an {@link Object}.
     *
     * @param bean
     *            the {@link Object} that will be used to create the JSONObject.
     */
    public JSONObject(final Object bean) {
        this();
        populateMap(bean);
    }

    /**
     * Constructs a JSONObject from a {@link Map}.
     *
     * @param m
     *            the {@link Map} that will be used to create the JSONObject.
     */
    public JSONObject(final Map<? extends String, ?> m) {
        map = new JSONObject(Objects.isNull(m)
                ? Collections.emptyMap()
                : m.entrySet().stream()
                        .collect(Collectors.toMap(
                                Entry::getKey,
                                e -> wrap(e.getValue()))));
    }

    /**
     * Constructs a JSONObject from another.
     *
     * @param o
     *            the JSONObject used as source, must be not {@link null}
     */
    public JSONObject(final JSONObject o) {
        this(o.map);
    }

    /**
     * Returns an {@link Optional#of(JSONArray)} if the value to which the specified key is mapped and is an instance of
     * {@link JSONArray}, {@link Optional#empty()} otherwise.
     * 
     * @param key
     *            the key whose associated value is to be returned
     * @return an {@link Optional#of(JSONArray)} if the value to which the specified key is mapped and is an instance of
     *         {@link JSONArray}, {@link Optional#empty()} otherwise
     * @throws ClassCastException
     *             if the key is of an inappropriate type for this map
     * @throws NullPointerException
     *             if the specified key is null and this map does not permit null keys
     */
    public Optional<JSONArray> getJSONArray(final Object key) {
        return getIfAssignable(key, JSONArray.class);
    }

    /**
     * Returns an {@link Optional#of(JSONObject)} if the value to which the specified key is mapped and is an instance
     * of
     * {@link JSONObject}, {@link Optional#empty()} otherwise.
     * 
     * @param key
     *            the key whose associated value is to be returned
     * @return an {@link Optional#of(JSONObject)} if the value to which the specified key is mapped and is an instance
     *         of
     *         {@link JSONObject}, {@link Optional#empty()} otherwise
     * @throws ClassCastException
     *             if the key is of an inappropriate type for this map
     * @throws NullPointerException
     *             if the specified key is null and this map does not permit null keys
     */
    public Optional<JSONObject> getJSONObject(final Object key) {
        return getIfAssignable(key, JSONObject.class);
    }

    /**
     * Returns an {@link Optional#of(Boolean)} if the value to which the specified key is mapped and is an instance of
     * {@link Boolean}, {@link Optional#empty()} otherwise.
     * 
     * @param key
     *            the key whose associated value is to be returned
     * @return an {@link Optional#of(Boolean)} if the value to which the specified key is mapped and is an instance of
     *         {@link Boolean}, {@link Optional#empty()} otherwise
     * @throws ClassCastException
     *             if the key is of an inappropriate type for this map
     * @throws NullPointerException
     *             if the specified key is null and this map does not permit null keys
     */
    public Optional<Boolean> getBoolean(final Object key) {
        return getIfAssignable(key, Boolean.class);
    }

    /**
     * Returns an {@link Optional#of(Byte)} if the value to which the specified key is mapped and is an instance of
     * {@link Byte}, {@link Optional#empty()} otherwise.
     * 
     * @param key
     *            the key whose associated value is to be returned
     * @return an {@link Optional#of(Byte)} if the value to which the specified key is mapped and is an instance of
     *         {@link Byte}, {@link Optional#empty()} otherwise
     * @throws ClassCastException
     *             if the key is of an inappropriate type for this map
     * @throws NullPointerException
     *             if the specified key is null and this map does not permit null keys
     */
    public Optional<Byte> getByte(final Object key) {
        return getIfAssignable(key, Byte.class);
    }

    /**
     * Returns an {@link Optional#of(Short)} if the value to which the specified key is mapped and is an instance of
     * {@link Short}, {@link Optional#empty()} otherwise.
     * 
     * @param key
     *            the key whose associated value is to be returned
     * @return an {@link Optional#of(Short)} if the value to which the specified key is mapped and is an instance of
     *         {@link Short}, {@link Optional#empty()} otherwise
     * @throws ClassCastException
     *             if the key is of an inappropriate type for this map
     * @throws NullPointerException
     *             if the specified key is null and this map does not permit null keys
     */
    public Optional<Short> getShort(final Object key) {
        return getIfAssignable(key, Short.class);
    }

    /**
     * Returns an {@link Optional#of(BigInteger)} if the value to which the specified key is mapped and is an instance
     * of
     * {@link BigInteger}, {@link Optional#empty()} otherwise.
     * 
     * @param key
     *            the key whose associated value is to be returned
     * @return an {@link Optional#of(BigInteger)} if the value to which the specified key is mapped and is an instance
     *         of
     *         {@link BigInteger}, {@link Optional#empty()} otherwise
     * @throws ClassCastException
     *             if the key is of an inappropriate type for this map
     * @throws NullPointerException
     *             if the specified key is null and this map does not permit null keys
     */
    public Optional<BigInteger> getBigInteger(final Object key) {
        return getIfAssignable(key, BigInteger.class);
    }

    /**
     * Returns an {@link Optional#of(Float)} if the value to which the specified key is mapped and is an instance of
     * {@link Float}, {@link Optional#empty()} otherwise.
     * 
     * @param key
     *            the key whose associated value is to be returned
     * @return an {@link Optional#of(Float)} if the value to which the specified key is mapped and is an instance of
     *         {@link Float}, {@link Optional#empty()} otherwise
     * @throws ClassCastException
     *             if the key is of an inappropriate type for this map
     * @throws NullPointerException
     *             if the specified key is null and this map does not permit null keys
     */
    public Optional<Float> getFloat(final Object key) {
        return getIfAssignable(key, Float.class);
    }

    /**
     * Returns an {@link Optional#of(BigDecimal)} if the value to which the specified key is mapped and is an instance
     * of
     * {@link BigDecimal}, {@link Optional#empty()} otherwise.
     * 
     * @param key
     *            the key whose associated value is to be returned
     * @return an {@link Optional#of(BigDecimal)} if the value to which the specified key is mapped and is an instance
     *         of
     *         {@link BigDecimal}, {@link Optional#empty()} otherwise
     * @throws ClassCastException
     *             if the key is of an inappropriate type for this map
     * @throws NullPointerException
     *             if the specified key is null and this map does not permit null keys
     */
    public Optional<BigDecimal> getBigDecimal(final Object key) {
        return getIfAssignable(key, BigDecimal.class);
    }

    /**
     * Returns an {@link Optional#of(Character)} if the value to which the specified key is mapped and is an instance of
     * {@link Character}, {@link Optional#empty()} otherwise.
     * 
     * @param key
     *            the key whose associated value is to be returned
     * @return an {@link Optional#of(Character)} if the value to which the specified key is mapped and is an instance of
     *         {@link Character}, {@link Optional#empty()} otherwise
     * @throws ClassCastException
     *             if the key is of an inappropriate type for this map
     * @throws NullPointerException
     *             if the specified key is null and this map does not permit null keys
     */
    public Optional<Character> getCharacter(final Object key) {
        return getIfAssignable(key, Character.class);
    }

    /**
     * Returns an {@link Optional#of(String)} if the value to which the specified key is mapped and is an instance of
     * {@link String}, {@link Optional#empty()} otherwise.
     * 
     * @param key
     *            the key whose associated value is to be returned
     * @return an {@link Optional#of(String)} if the value to which the specified key is mapped and is an instance of
     *         {@link String}, {@link Optional#empty()} otherwise
     * @throws ClassCastException
     *             if the key is of an inappropriate type for this map
     * @throws NullPointerException
     *             if the specified key is null and this map does not permit null keys
     */
    public Optional<String> getString(final Object key) {
        return getIfAssignable(key, String.class);
    }

    /**
     * Returns an {@link Optional#of(Integer)} if the value to which the specified key is mapped and is an instance of
     * {@link Integer}, {@link Optional#empty()} otherwise.
     * 
     * @param key
     *            the key whose associated value is to be returned
     * @return an {@link Optional#of(Integer)} if the value to which the specified key is mapped and is an instance of
     *         {@link Integer}, {@link Optional#empty()} otherwise
     * @throws ClassCastException
     *             if the key is of an inappropriate type for this map
     * @throws NullPointerException
     *             if the specified key is null and this map does not permit null keys
     */
    public OptionalInt getInteger(final Object key) {
        final Object o = get(key);
        return (o instanceof Integer)
                ? OptionalInt.of((int) o)
                : OptionalInt.empty();
    }

    /**
     * Returns an {@link Optional#of(Long)} if the value to which the specified key is mapped and is an instance of
     * {@link Long}, {@link Optional#empty()} otherwise.
     * 
     * @param key
     *            the key whose associated value is to be returned
     * @return an {@link Optional#of(Long)} if the value to which the specified key is mapped and is an instance of
     *         {@link Long}, {@link Optional#empty()} otherwise
     * @throws ClassCastException
     *             if the key is of an inappropriate type for this map
     * @throws NullPointerException
     *             if the specified key is null and this map does not permit null keys
     */
    public OptionalLong getLong(final Object key) {
        final Object o = get(key);
        return (o instanceof Long)
                ? OptionalLong.of((long) o)
                : OptionalLong.empty();
    }

    /**
     * Returns an {@link Optional#of(Double)} if the value to which the specified key is mapped and is an instance of
     * {@link Double}, {@link Optional#empty()} otherwise.
     * 
     * @param key
     *            the key whose associated value is to be returned
     * @return an {@link Optional#of(Double)} if the value to which the specified key is mapped and is an instance of
     *         {@link Double}, {@link Optional#empty()} otherwise
     * @throws ClassCastException
     *             if the key is of an inappropriate type for this map
     * @throws NullPointerException
     *             if the specified key is null and this map does not permit null keys
     */
    public OptionalDouble getDouble(final Object key) {
        final Object o = get(key);
        return (o instanceof Double)
                ? OptionalDouble.of((double) o)
                : OptionalDouble.empty();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        return prime + ((map == null) ? 0 : map.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
        return this == obj
                || (obj != null
                        && obj instanceof JSONObject
                        && map.equals(((JSONObject) obj).map));
    }

    @Override
    public String toJSONString() {
        return toJSONString();
    }

    @Override
    public String toString() {
        return entrySet().stream()
                .map(e -> {
                    final Object v = e.getValue();
                    return new StringBuilder()
                            .append("\"")
                            .append(e.getKey())
                            .append("\": ")
                            .append(v instanceof String || v instanceof Character
                                    ? "\"" + v + "\""
                                    : v)
                            .toString();
                })
                .collect(Collectors.joining(", ", "{", "}"));
    }

    // Delegate methods

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(final Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(final Object value) {
        return map.containsValue(value);
    }

    @Override
    public Object get(final Object key) {
        return map.get(key);
    }

    @Override
    public Object put(final String key, final Object value) {
        return map.put(key, wrap(value));
    }

    @Override
    public Object remove(final Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(final Map<? extends String, ?> m) {
        map.putAll(new JSONObject(m));
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<Object> values() {
        return map.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return map.entrySet();
    }

    @Override
    public Object getOrDefault(final Object key, final Object defaultValue) {
        return map.getOrDefault(key, wrap(defaultValue));
    }

    @Override
    public void forEach(final BiConsumer<? super String, ? super Object> action) {
        map.forEach(action);
    }

    @Override
    public void replaceAll(final BiFunction<? super String, ? super Object, ?> function) {
        map.replaceAll(function);
    }

    @Override
    public Object putIfAbsent(final String key, final Object value) {
        return map.putIfAbsent(key, wrap(value));
    }

    @Override
    public boolean remove(final Object key, final Object value) {
        return map.remove(key, value);
    }

    @Override
    public boolean replace(final String key, final Object oldValue, final Object newValue) {
        return map.replace(key, oldValue, wrap(newValue));
    }

    @Override
    public Object replace(final String key, final Object value) {
        return map.replace(key, wrap(value));
    }

    @Override
    public Object computeIfAbsent(
            final String key,
            final Function<? super String, ?> mappingFunction) {
        return map.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public Object computeIfPresent(
            final String key,
            final BiFunction<? super String, ? super Object, ?> remappingFunction) {
        return map.computeIfPresent(key, remappingFunction);
    }

    @Override
    public Object compute(
            final String key,
            final BiFunction<? super String, ? super Object, ?> remappingFunction) {
        return map.compute(key, remappingFunction);
    }

    @Override
    public Object merge(
            final String key,
            final Object value,
            final BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return map.merge(key, wrap(value), remappingFunction);
    }

    private void populateMap(final Object bean) {
        if (bean instanceof Map) {
            throw new UncheckedJSONException(
                    new JSONException("Maps cannot be encoded as JSON objects"));
        }
        if (bean instanceof Collection) {
            throw new UncheckedJSONException(
                    new JSONException("Collections cannot be encoded as JSON objects"));
        }
        final Class<?> clazz = requireNonNull(bean).getClass();
        if (clazz.isArray()) {
            throw new UncheckedJSONException(
                    new JSONException("Arrays cannot be encoded as JSON objects"));
        }

        // If clazz is a System class then return only the methods declared by clazz, all the declared methods if
        // its not.
        final Method[] methods = clazz.getClassLoader() != null ? clazz.getMethods() : clazz.getDeclaredMethods();
        for (final Method method : methods) {
            try {
                if (Modifier.isPublic(method.getModifiers())) {
                    final String name = method.getName();
                    String key = "";
                    if (name.startsWith("get")) {
                        if ("getClass".equals(name)
                                || "getDeclaringClass".equals(name)) {
                            key = "";
                        } else {
                            key = name.substring(3);
                        }
                    } else if (name.startsWith("is")) {
                        key = name.substring(2);
                    }
                    if (key.length() > 0
                            && Character.isUpperCase(key.charAt(0))
                            && method.getParameterTypes().length == 0) {
                        if (key.length() == 1) {
                            key = key.toLowerCase();
                        } else if (!Character.isUpperCase(key.charAt(1))) {
                            key = key.substring(0, 1).toLowerCase()
                                    + key.substring(1);
                        }
                        final Object result = method.invoke(bean, (Object[]) null);
                        if (result != null) {
                            map.put(key, wrap(result));
                        }
                    }
                }
            } catch (final Exception ignore) {
                // Thrown exceptions will be ignored. BTW exceptions should not be thrown.
            }
        }
    }

    /**
     * Tries to convert a {@link String} into an {@link Integer}, {@link Long}, {@link Float} or a {@link Double}. If
     * the operation succeedes (at least one parsing operation doesn't throw a {@link NumberFormatException}) then
     * returns the parsed value. If the string is impossible to parse then returns the given string.
     * 
     * @param s
     *            The string that we are trying to parse
     * @return the parsed string into a {@link Number}, the given string otherwise
     */
    private static Object tryParse(final String s) {
        try {
            return Integer.parseInt(s);
        } catch (final NumberFormatException e1) {
            try {
                return Long.parseLong(s);
            } catch (final NumberFormatException e2) {
                try {
                    return Float.parseFloat(s);
                } catch (final NumberFormatException e3) {
                    try {
                        return Double.parseDouble(s);
                    } catch (final NumberFormatException e4) {
                        return s;
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Optional<T> getIfAssignable(final Object key, final Class<? extends T> clazz) {
        final Object v = get(key);
        return v != null && ClassUtils.isAssignable(v.getClass(), clazz)
                ? Optional.of((T) v)
                : Optional.empty();
    }

}
