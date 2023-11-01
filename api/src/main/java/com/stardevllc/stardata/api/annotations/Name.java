package com.stardevllc.stardata.api.annotations;

import java.lang.annotation.*;

/**
 * This annotation allows customization of the name of columns and tables in the database. This annotation has the highest priority for naming columns and tables.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Name {
    String value();
}
