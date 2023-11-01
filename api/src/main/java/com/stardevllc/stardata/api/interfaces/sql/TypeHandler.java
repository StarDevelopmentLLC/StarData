package com.stardevllc.stardata.api.interfaces.sql;

import com.stardevllc.stardata.api.interfaces.ObjectCodec;
import com.stardevllc.stardata.api.interfaces.SQLDatabase;
import com.stardevllc.stardata.api.model.DatabaseRegistry;

import java.util.Set;

/**
 * This allows the abiltity to have Java Classes converted automatically to MySQL Types and vica versa<br>
 * It is up to the implementor to handle the seriazation and deseralization<br>
 * You can add these handlers to a {@link DatabaseRegistry} which will allow it to be used by all databases registered under that Registry<br>
 * Alternatively, you can add the handlers to {@link SQLDatabase} of which it will only be used by tables in that database.<br>
 * You cannot add them to {@link Table}'s individually. Use a {@link ObjectCodec} if you want to do that. 
 */
public interface TypeHandler {
    TypeSerializer getSerializer();

    TypeDeserializer getDeserializer();

    void addAdditionalClass(Class<?>... classes);

    Class<?> getMainClass();

    Set<Class<?>> getAdditionalClasses();

    String getMysqlType();

    boolean matches(Class<?> clazz);
}
