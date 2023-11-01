package com.stardevllc.stardata.api.annotations;

import com.stardevllc.stardata.api.FKAction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This allows creating links between tables. <br>
 * To use this, annotate a field in the child class with this one and if you want a reference in the parent class, use the {@link ForeignKeyStorage} annotation <br>
 * All classes that use this annotation must be registered AFTER the class that is considered the parent class/table
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ForeignKey {
    /**
     * The class that the foreign key references <br>
     * The library will grab the correct field for the primary key of the other class <br>
     * The reference class must be registered before the class with the foreign key
     */
    Class<?> value();
    
    FKAction onDelete() default FKAction.RESTRICT;
    FKAction onUpdate() default FKAction.RESTRICT;
}