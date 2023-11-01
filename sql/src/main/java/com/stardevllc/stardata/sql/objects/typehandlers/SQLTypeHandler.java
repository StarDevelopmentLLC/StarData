package com.stardevllc.stardata.sql.objects.typehandlers;

import com.stardevllc.stardata.api.interfaces.sql.TypeDeserializer;
import com.stardevllc.stardata.api.interfaces.sql.TypeHandler;
import com.stardevllc.stardata.api.interfaces.sql.TypeSerializer;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SQLTypeHandler implements TypeHandler {
    protected final Class<?> mainClass;
    protected final Set<Class<?>> additionalClasses = new HashSet<>();
    protected final String mysqlType;
    
    protected final TypeSerializer serializer;
    protected final TypeDeserializer deserializer;
    
    public SQLTypeHandler(Class<?> mainClass, String mysqlType, TypeSerializer serializer, TypeDeserializer deserializer) {
        this.mainClass = mainClass;
        this.mysqlType = mysqlType;
        this.serializer = serializer;
        this.deserializer = deserializer;
    }
    
    @Override
    public TypeSerializer getSerializer() {
        return serializer;
    }
    
    @Override
    public TypeDeserializer getDeserializer() {
        return deserializer;
    }
    
    @Override
    public void addAdditionalClass(Class<?>... classes) {
        if (classes != null) {
            this.additionalClasses.addAll(List.of(classes));
        }
    }
    
    @Override
    public Class<?> getMainClass() {
        return mainClass;
    }
    
    @Override
    public Set<Class<?>> getAdditionalClasses() {
        return additionalClasses;
    }
    
    @Override
    public String getMysqlType() {
        return mysqlType;
    }
    
    @Override
    public boolean matches(Class<?> clazz) {
        if (this.mainClass.equals(clazz)) {
            return true;
        }
    
        for (Class<?> additionalClass : this.additionalClasses) {
            if (additionalClass.equals(clazz)) {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SQLTypeHandler that = (SQLTypeHandler) o;
        return Objects.equals(mainClass, that.mainClass) && Objects.equals(additionalClasses, that.additionalClasses);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(mainClass, additionalClasses);
    }
}
