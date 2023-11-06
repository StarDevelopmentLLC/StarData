package com.stardevllc.stardata.api.interfaces.model;

import java.util.Set;

public interface ClassModel<T extends Database> extends Comparable<ClassModel<T>> {

    /**
     * The primary field is the main field or the id field of a class. This is required by the library in order to properly function.
     * @return The primary field
     */
    FieldModel<T> getPrimaryField();
    
    /**
     * @return Gets the name that is used within the database. 
     */
    String getName();
    
    /**
     * @return The database that this table is registered under.
     */
    T getDatabase();

    /**
     * @return The Java Class this model represents
     */
    Class<?> getModelClass();

    /**
     * @return The generated field models for this model.
     */
    Set<FieldModel<T>> getFieldModels();

    /**
     * Sets up this model for the database provided. <br>
     * The model itself should be generated before this method is called. <br>
     * This method is used to create the actual thing that is used to represent it, like a file or a sql table etc...
     * @param database The database instance.
     */
    void setup(T database) throws Exception;
}
