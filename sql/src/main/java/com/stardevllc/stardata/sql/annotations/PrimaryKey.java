package com.stardevllc.stardata.sql.annotations;

import com.stardevllc.stardata.api.interfaces.sql.Table;

import java.lang.annotation.*;

/**
 * This sets the field as the Primary Key<br>
 * Please see the {@link Table} class for more information on how to setup a table<br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PrimaryKey {
    
}
