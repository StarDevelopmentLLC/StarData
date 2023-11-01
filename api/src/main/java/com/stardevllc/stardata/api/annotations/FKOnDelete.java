package com.stardevllc.stardata.api.annotations;

import com.stardevllc.stardata.api.FKAction;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FKOnDelete {
    FKAction value();
}