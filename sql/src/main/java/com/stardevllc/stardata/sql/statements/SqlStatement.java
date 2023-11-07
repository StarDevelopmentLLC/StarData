package com.stardevllc.stardata.sql.statements;

@FunctionalInterface
public interface SqlStatement {
    String build();
}