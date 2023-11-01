package com.stardevllc.stardata.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This allows you to specify an order of the table columns. <br>
 * Java does not order fields by the declaration order, so it is random when they are processed by the library. <br>
 * This annotation allows for custom ordering. The lower the number, the higher the order priority
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Order {
    /**
     * This value MUST be less than 1000 as the unordered columns start at index 1000
     * @return The order index. Smaller the number, the higher the order
     */
    int value();
}
