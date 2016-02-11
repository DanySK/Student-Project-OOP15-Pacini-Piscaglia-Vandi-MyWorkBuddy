package it.unibo.oop.myworkoutbuddy.util.json;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

/**
 * 
 * @param <TKey>
 *            the type of the key to access elements.
 */
public interface JSONValue<TKey> extends Serializable {

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
    Optional<JSONArray> getJSONArray(TKey key);

    /**
     * Returns an {@link Optional#of(JSONTKey)} if the value to which the specified key is mapped and is an instance
     * of
     * {@link JSONTKey}, {@link Optional#empty()} otherwise.
     * 
     * @param key
     *            the key whose associated value is to be returned
     * @return an {@link Optional#of(JSONTKey)} if the value to which the specified key is mapped and is an instance
     *         of
     *         {@link JSONTKey}, {@link Optional#empty()} otherwise
     * @throws ClassCastException
     *             if the key is of an inappropriate type for this map
     * @throws NullPointerException
     *             if the specified key is null and this map does not permit null keys
     */
    Optional<JSONObject> getJSONObject(final TKey key);

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
    Optional<Boolean> getBoolean(final TKey key);

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
    Optional<Byte> getByte(final TKey key);

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
    Optional<Short> getShort(final TKey key);

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
    Optional<BigInteger> getBigInteger(final TKey key);

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
    Optional<Float> getFloat(final TKey key);

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
    Optional<BigDecimal> getBigDecimal(final TKey key);

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
    Optional<Character> getCharacter(final TKey key);

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
    Optional<String> getString(final TKey key);

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
    OptionalInt getInteger(final TKey key);

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
    OptionalLong getLong(final TKey key);

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
    OptionalDouble getDouble(final TKey key);

}
