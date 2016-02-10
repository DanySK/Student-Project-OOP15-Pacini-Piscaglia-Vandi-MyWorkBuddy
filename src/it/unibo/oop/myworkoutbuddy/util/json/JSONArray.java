package it.unibo.oop.myworkoutbuddy.util.json;

import static it.unibo.oop.myworkoutbuddy.util.json.JSONObject.wrap;
import static it.unibo.oop.myworkoutbuddy.util.json.JSONObject.wrapCollection;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A JSONArray is an ordered sequence of values. Its external text form is a string wrapped in square brackets with
 * commas separating the values. The internal form is an object having <code>get</code> method for accessing the values
 * by index, and <code>add</code> methods for adding or replacing values. The values can be any of these types:
 * <code>Boolean</code>, <code>JSONArray</code>, <code>JSONObject</code>, <code>Number</code>,
 * <code>String</code>, or the <code>JSONObject.NULL object</code>.
 * <p>
 * A <code>get</code> method returns a value if one can be found, and throws an exception if one cannot be found. An
 * <code>opt</code> method returns a default value instead of throwing an exception, and so is useful for obtaining
 * optional values.
 * <p>
 * The <code>get</code> method return an object which you can cast or query for type. There are also typed
 * <code>get</code> and <code>opt</code> methods that do type checking and type
 * coercion for you.
 * <p>
 * The texts produced by the <code>toString</code> methods strictly conform to JSON syntax rules. The constructors are
 * more forgiving in the texts they will accept:
 * <ul>
 * <li>An extra <code>,</code>&nbsp;<small>(comma)</small> may appear just before the closing bracket.</li>
 * <li>The <code>null</code> value will be inserted when there is <code>,</code> &nbsp;<small>(comma)</small> elision.
 * </li>
 * <li>Strings may be quoted with <code>'</code>&nbsp;<small>(single quote)</small>.</li>
 * <li>Strings do not need to be quoted at all if they do not begin with a quote or single quote, and if they do not
 * contain leading or trailing spaces, and if they do not contain any of these characters:
 * <code>{ } [ ] / \ : , #</code> and if they do not look like numbers and if they are not the reserved words
 * <code>true</code>, <code>false</code>, or <code>null</code>.</li>
 * </ul>
 */
public class JSONArray implements List<Object> {

    private final List<Object> list;

    /**
     * Constructs an empty JSONArray.
     */
    public JSONArray() {
        list = new ArrayList<>();
    }

    /**
     * Constructs a JSONArray from a {@link Collection}.
     *
     * @param c
     *            the {@link Collection} that will be used to create the JSONArray
     */
    public JSONArray(final Collection<?> c) {
        list = new ArrayList<>(wrapCollection(c));
    }

    /**
     * Constructs a JSONArray from an {@link Object} at least, possibly more.
     *
     * @param first
     *            the first {@link Object} that will be inserted in the JSONArray
     *
     * @param second
     *            the second {@link Object} that will be inserted in the JSONArray
     * @param more
     *            the rest of the {@link Object}s that will be inserted in the JSONArray
     */
    public JSONArray(final Object first, final Object second, final Object... more) {
        this(Arrays.stream(more).collect(Collectors.toList()));
        add(0, first);
        add(1, second);
    }

    /**
     * Constructs a JSONArray from another.
     *
     * @param a
     *            the JSONArray used as source, must be not {@link null}
     */
    public JSONArray(final JSONArray a) {
        list = new ArrayList<>(Objects.requireNonNull(a.list));
    }

    /**
     * Returns an {@link Optional#of(JSONArray)} if the element at the specified position in list is an instance of
     * {@link JSONArray}. {@link Optional#empty()} otherwise.
     *
     * @param index
     *            index of the element to return
     * @return an {@link Optional#of(JSONArray)} if the element at the specified position in list is an instance of
     *         {@link JSONArray}. {@link Optional#empty()} otherwise
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    public Optional<JSONArray> getJSONArray(final int index) {
        final Object o = get(index);
        return (o instanceof JSONArray)
                ? Optional.of((JSONArray) o)
                : Optional.empty();
    }

    /**
     * Returns an {@link Optional#of(JSONObject)} if the element at the specified position in list is an instance of
     * {@link JSONObject}. {@link Optional#empty()} otherwise.
     *
     * @param index
     *            index of the element to return
     * @return an {@link Optional#of(JSONObject)} if the element at the specified position in list is an instance of
     *         {@link JSONObject}. {@link Optional#empty()} otherwise
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    public Optional<JSONObject> getJSONObject(final int index) {
        final Object o = get(index);
        return (o instanceof JSONObject)
                ? Optional.of((JSONObject) o)
                : Optional.empty();
    }

    /**
     * Returns an {@link Optional#of(Boolean)} if the element at the specified position in list is an instance of
     * {@link Boolean}. {@link Optional#empty()} otherwise.
     *
     * @param index
     *            index of the element to return
     * @return an {@link Optional#of(Boolean)} if the element at the specified position in list is an instance of
     *         {@link Boolean}. {@link Optional#empty()} otherwise
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    public Optional<Boolean> getBoolean(final int index) {
        final Object o = get(index);
        return (o instanceof Boolean)
                ? Optional.of((Boolean) o)
                : Optional.empty();
    }

    /**
     * Returns an {@link Optional#of(Byte)} if the element at the specified position in list is an instance of
     * {@link Byte}. {@link Optional#empty()} otherwise.
     *
     * @param index
     *            index of the element to return
     * @return an {@link Optional#of(Byte)} if the element at the specified position in list is an instance of
     *         {@link Byte}. {@link Optional#empty()} otherwise
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    public Optional<Byte> getByte(final int index) {
        final Object o = get(index);
        return (o instanceof Byte)
                ? Optional.of((Byte) o)
                : Optional.empty();
    }

    /**
     * Returns an {@link Optional#of(Short)} if the element at the specified position in list is an instance of
     * {@link Short}. {@link Optional#empty()} otherwise.
     *
     * @param index
     *            index of the element to return
     * @return an {@link Optional#of(Short)} if the element at the specified position in list is an instance of
     *         {@link Short}. {@link Optional#empty()} otherwise
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    public Optional<Short> getShort(final int index) {
        final Object o = get(index);
        return (o instanceof Short)
                ? Optional.of((Short) o)
                : Optional.empty();
    }

    /**
     * Returns an {@link OptionalInt#of(Int)} if the element at the specified position in list is an instance of
     * {@link Integer}. {@link OptionalInt#empty()} otherwise.
     *
     * @param index
     *            index of the element to return
     * @return an {@link OptionalInt#of(Int)} if the element at the specified position in list is an instance of
     *         {@link Integer}. {@link OptionalInt#empty()} otherwise
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    public OptionalInt getInteger(final int index) {
        final Object o = get(index);
        return (o instanceof Integer)
                ? OptionalInt.of((int) o)
                : OptionalInt.empty();
    }

    /**
     * Returns an {@link OptionalLong#of(Long)} if the element at the specified position in list is an instance of
     * {@link Long}. {@link OptionalLong#empty()} otherwise.
     *
     * @param index
     *            index of the element to return
     * @return an {@link OptionalLong#of(Long)} if the element at the specified position in list is an instance of
     *         {@link Long}. {@link OptionalLong#empty()} otherwise
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    public OptionalLong getLong(final int index) {
        final Object o = get(index);
        return (o instanceof Long)
                ? OptionalLong.of((long) o)
                : OptionalLong.empty();
    }

    /**
     * Returns an {@link Optional#of(BigInteger)} if the element at the specified position in list is an instance of
     * {@link BigInteger}. {@link Optional#empty()} otherwise.
     *
     * @param index
     *            index of the element to return
     * @return an {@link Optional#of(BigInteger)} if the element at the specified position in list is an instance of
     *         {@link BigInteger}. {@link Optional#empty()} otherwise
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    public Optional<BigInteger> getBigInteger(final int index) {
        final Object o = get(index);
        return (o instanceof BigInteger)
                ? Optional.of((BigInteger) o)
                : Optional.empty();
    }

    /**
     * Returns an {@link Optional#of(Float)} if the element at the specified position in list is an instance of
     * {@link Float}. {@link Optional#empty()} otherwise.
     *
     * @param index
     *            index of the element to return
     * @return an {@link Optional#of(Float)} if the element at the specified position in list is an instance of
     *         {@link Float}. {@link Optional#empty()} otherwise
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    public Optional<Float> getFloat(final int index) {
        final Object o = get(index);
        return (o instanceof Float)
                ? Optional.of((Float) o)
                : Optional.empty();
    }

    /**
     * Returns an {@link OptionalDouble#of(Double)} if the element at the specified position in list is an instance of
     * {@link Double}. {@link OptionalDouble#empty()} otherwise.
     *
     * @param index
     *            index of the element to return
     * @return an {@link OptionalDouble#of(Double)} if the element at the specified position in list is an instance of
     *         {@link Double}. {@link OptionalDouble#empty()} otherwise
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    public OptionalDouble getDouble(final int index) {
        final Object o = get(index);
        return (o instanceof Double)
                ? OptionalDouble.of((double) o)
                : OptionalDouble.empty();
    }

    /**
     * Returns an {@link Optional#of(BigDecimal)} if the element at the specified position in list is an instance of
     * {@link BigDecimal}. {@link Optional#empty()} otherwise.
     *
     * @param index
     *            index of the element to return
     * @return an {@link Optional#of(BigDecimal)} if the element at the specified position in list is an instance of
     *         {@link BigDecimal}. {@link Optional#empty()} otherwise
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    public Optional<BigDecimal> getBigDecimal(final int index) {
        final Object o = get(index);
        return (o instanceof BigDecimal)
                ? Optional.of((BigDecimal) o)
                : Optional.empty();
    }

    /**
     * Returns an {@link Optional#of(Character)} if the element at the specified position in list is an instance of
     * {@link Character}. {@link Optional#empty()} otherwise.
     *
     * @param index
     *            index of the element to return
     * @return an {@link Optional#of(Character)} if the element at the specified position in list is an instance of
     *         {@link Character}. {@link Optional#empty()} otherwise
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    public Optional<Character> getCharacter(final int index) {
        final Object o = get(index);
        return (o instanceof Character)
                ? Optional.of((Character) o)
                : Optional.empty();
    }

    /**
     * Returns an {@link Optional#of(String)} if the element at the specified position in list is an instance of
     * {@link String}. {@link Optional#empty()} otherwise.
     *
     * @param index
     *            index of the element to return
     * @return an {@link Optional#of(String)} if the element at the specified position in list is an instance of
     *         {@link String}. {@link Optional#empty()} otherwise
     * @throws IndexOutOfBoundsException
     *             if the index is out of range (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    public Optional<String> getString(final int index) {
        final Object o = get(index);
        return (o instanceof String)
                ? Optional.of((String) o)
                : Optional.empty();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        return prime + ((list == null) ? 0 : list.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
        return this == obj
                || (obj != null
                        && obj instanceof JSONArray
                        && list.equals(((JSONArray) obj).list));
    }

    @Override
    public String toString() {
        return list.toString();
    }

    // Delegate methods

    @Override
    public void forEach(final Consumer<? super Object> action) {
        list.forEach(action);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<Object> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(final Object e) {
        return list.add(wrap(Objects.requireNonNull(e)));
    }

    @Override
    public boolean remove(final Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(final Collection<?> c) {
        return list.addAll(wrapCollection(c));
    }

    @Override
    public boolean addAll(final int index, final Collection<?> c) {
        return list.addAll(index, wrapCollection(c));
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void replaceAll(final UnaryOperator<Object> operator) {
        list.replaceAll(operator);
    }

    @Override
    public boolean removeIf(final Predicate<? super Object> filter) {
        return list.removeIf(filter);
    }

    @Override
    public void sort(final Comparator<? super Object> c) {
        list.sort(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Object get(final int index) {
        return list.get(index);
    }

    @Override
    public Object set(final int index, final Object element) {
        return list.set(index, wrap(element));
    }

    @Override
    public void add(final int index, final Object element) {
        list.add(index, wrap(element));
    }

    @Override
    public Stream<Object> stream() {
        return list.stream();
    }

    @Override
    public Object remove(final int index) {
        return list.remove(index);
    }

    @Override
    public Stream<Object> parallelStream() {
        return list.parallelStream();
    }

    @Override
    public int indexOf(final Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(final Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<Object> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<Object> listIterator(final int index) {
        return list.listIterator(index);
    }

    @Override
    public List<Object> subList(final int fromIndex, final int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public Spliterator<Object> spliterator() {
        return list.spliterator();
    }

}
