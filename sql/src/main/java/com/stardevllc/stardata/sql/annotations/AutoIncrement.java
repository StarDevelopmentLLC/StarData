package com.stardevllc.stardata.sql.annotations;

import java.lang.annotation.*;

/**
 * This sets the field to be an auto-increment field<br>
 * Please see the {@link com.stardevllc.stardata.api.interfaces.sql.Table} class for more information on how to setup a table
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoIncrement {
    
}
