package com.stardevllc.stardata.api.interfaces;

import com.stardevllc.stardata.api.interfaces.TypeHandler;
import com.stardevllc.stardata.api.interfaces.model.Database;
import com.stardevllc.stardata.api.interfaces.model.FieldModel;

/**
 * The functional interface for the serializer in the {@link TypeHandler} class
 */
@FunctionalInterface
public interface TypeSerializer<T extends Database> {
    /**
     * Serializes an object into a MySQL Supported Type <br>
     * @param fieldModel The field model that the object is represented by
     * @param object The actual object
     * @return A supported JDBC conversion type. Please see <a href="https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-type-conversions.html">MySQL JDBC Reference</a>
     */
    Object serialize(FieldModel<T> fieldModel, Object object);
}
