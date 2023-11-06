package com.stardevllc.stardata.api.interfaces.model;

import com.stardevllc.stardata.api.annotations.Name;
import com.stardevllc.stardata.api.annotations.Order;
import com.stardevllc.stardata.api.interfaces.ObjectCodec;
import com.stardevllc.stardata.api.interfaces.TypeHandler;

import java.lang.reflect.Field;

public interface FieldModel<T extends Database> extends Comparable<FieldModel<T>> {
    /**
     * @return The field that this model represents.
     */
    Field getField();

    /**
     * @return The class where the field came from.
     */
    Class<?> getFieldClass();

    /**
     * @return The order index, see the {@link Order} annotation
     */
    int getOrder();

    /**
     * @return The type handler for parsing values of this field.
     */
    TypeHandler getTypeHandler();

    /**
     * @return The current object codec for parsing values of this field. 
     */
    ObjectCodec<?> getCodec();
    
    /**
     * @return The name for use within the database. This name can be set from the {@link Name} annotation, or from this field name.
     */
    String getName();

    /**
     * @return The class model parent of this field model. 
     */
    ClassModel<T> getParent();
}