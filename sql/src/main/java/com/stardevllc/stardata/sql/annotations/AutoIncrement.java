package com.stardevllc.stardata.sql.annotations;

import com.stardevllc.stardata.sql.interfaces.Table;

import java.lang.annotation.*;

/**
 * This sets the field to be an auto-increment field<br>
 * Please see the {@link Table} class for more information on how to setup a table
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoIncrement {
    
}
