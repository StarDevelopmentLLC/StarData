package com.stardevllc.stardata.api.interfaces.sql;

import com.stardevllc.stardata.api.interfaces.ObjectCodec;

import java.util.Map;
import java.util.UUID;

/**
 * This class represents a Row or a Record in a Table<br>
 * This is mainly to allow closing the connection to the database as quick as possible as it stores the data in a HashMap based on the column<br>
 * The values from each database cell are parsed using {@link TypeHandler}'s for the field/class and any defined {@link ObjectCodec}'s for the columns<br>
 * This means that you can case the value from the {@code getObject} method to what you know the type to be.<br>
 * You can also use the other methods to do this for you, they will attempt to parse the values as well depending on the type of value<br>
 * Please see each individual method for more information
 */
public interface Row {
    /**
     * Gets a parsed object from this Row
     * @param key The column name
     * @return The object
     */
    Object getObject(String key);

    /**
     * Gets the value as a string
     * @param key The column name
     * @return The value as a string
     */
    String getString(String key);

    /**
     * Gets the value as an int
     * @param key The column name
     * @return The value as an int
     */
    int getInt(String key);

    /**
     * Gets the value as a long
     * @param key The column name
     * @return The value as a long
     */
    long getLong(String key);

    /**
     * Gets the value as a double
     * @param key The column name
     * @return The value as a double
     */
    double getDouble(String key);

    /**
     * Gets the value as a float
     * @param key The column name
     * @return The value as a float
     */
    float getFloat(String key);

    /**
     * Gets the value as a boolean
     * @param key The column name
     * @return The value as a boolean
     */
    boolean getBoolean(String key);

    /**
     * Gets the value as a UUID
     * @param key The column name
     * @return The value as a UUID
     */
    UUID getUuid(String key);

    /**
     * Gets the value using the codec provided
     * @param key The column name
     * @return The value decoded
     */
    <T> T get(String key, ObjectCodec<T> codec);

    /**
     * @return All the data related to this row that was selected from the query
     */
    Map<String, Object> getData();

    /**
     * @return The table related to this row.
     */
    Table getTable();
}
