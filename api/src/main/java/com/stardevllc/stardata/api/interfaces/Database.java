package com.stardevllc.stardata.api.interfaces;

import com.stardevllc.stardata.api.model.DatabaseRegistry;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Represents a generic database, only really used for SQL based things for now. <br>
 * See {@link SQLDatabase} for more information.
 */
public interface Database {
    /**
     * Registers a table to this database
     *
     * @param clazz The table to register
     */
    void registerClass(Class<?> clazz);

    /**
     * @return The logger that was passed into the database.
     */
    Logger getLogger();

    /**
     * @return The {@link DatabaseRegistry} that this database is registered to.
     */
    DatabaseRegistry<? extends Database> getRegistry();

    /**
     * Sets the {@link DatabaseRegistry} that this is registered to.<br>
     * Note: The register methods will do this automatically
     *
     * @param registry The registry
     */
    void setRegistry(DatabaseRegistry<? extends Database> registry);

    /**
     * Gets Objects of the class provided. This will still return a list even if it is just one<br>
     * The two arrays must match each other in both length and what you want to do. The index of the first one will map to the index of the second one.
     *
     * @param clazz  The class of the table
     * @param keys   The Array of Columns to base the query on. This must match the values
     * @param values The array of values based on columns. This must match the columns
     * @param <T>    The type of the table
     * @return The list of objects that Match. This should never return a null object. If there is nothing that matches, it will be an empty list.
     * @throws SQLException Any SQL errors that happen
     */
    <T> List<T> get(Class<T> clazz, String[] keys, Object[] values) throws Exception;

    /**
     * Gets objects based on a column and a value. This will still return a list even if it is just one that matches
     *
     * @param clazz The class of the table
     * @param key   The column to select based on
     * @param value The value to select based on
     * @param <T>   The type of the table
     * @return The list of objects that match
     * @throws SQLException Any SQL errors
     */
    <T> List<T> get(Class<T> clazz, String key, Object value) throws Exception;

    /**
     * Gets all objects based on a type
     *
     * @param clazz The class of the table
     * @param <T>   The type of the table
     * @return The list of objects that match
     * @throws SQLException Any SQL errors
     */
    <T> List<T> get(Class<T> clazz) throws Exception;

    /**
     * Saves an object to the database while catching {@link SQLException}s
     *
     * @param object The object to save to the database
     */
    void saveSilent(Object object);

    /**
     * Saves an object to the database
     *
     * @param object The object to save
     * @throws Exception Any error that occurs
     */
    void save(Object object) throws Exception;

    /**
     * Deletes an object from the database while catching {@link Exception}'s
     *
     * @param clazz The class of the table
     * @param id    The ID to delete. This is the the Primary Column value based on either auto-detection or the @ID annotation
     */
    void deleteSilent(Class<?> clazz, Object id);

    /**
     * Deletes an object from the database while cactching {@link Exception}'s
     *
     * @param object The object to delete
     */
    void deleteSilent(Object object);

    /**
     * Deletes an Object from the database<br>
     * Note: This will throw an {@link IllegalArgumentException} if no table is found
     *
     * @param object The object to delete
     * @throws Exception Any errors that happen
     */
    void delete(Object object) throws Exception;

    /**
     * Deletes an object from the database
     *
     * @param clazz The table class
     * @param id    The id to delete. This is the the Primary Column value based on either auto-detection or the @ID annotation
     * @throws Exception Any errors that happen
     */
    void delete(Class<?> clazz, Object id) throws Exception;

    /**
     * Adds an object to the Queue.
     * This queue is to allow pushing many objects in a single connection
     *
     * @param object The object to add
     */
    void queue(Object object);

    /**
     * Flushes the queue. This does catch SQL Exceptions
     */
    void flush();

    /**
     * @return If this database has been setup yet, this is usually handled by a DatabaseRegistry
     */
    boolean isSetup();

    /**
     * Set up this database so that it can be used. 
     * @throws Exception Any exceptions that are thrown. 
     */
    void setup() throws Exception;
}
